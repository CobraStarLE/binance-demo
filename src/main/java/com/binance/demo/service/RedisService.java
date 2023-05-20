package com.binance.demo.service;

import com.binance.demo.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.SerializationUtils;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;


    public String getSetStr(final String key, final String value) {
        return stringRedisTemplate.opsForValue().getAndSet(key, value);
    }


    public boolean setNXStr(final String key, final String value, final long timeout, final TimeUnit timeUnit) {
        return this.redisTemplate.boundValueOps(key).setIfAbsent(value,timeout,timeUnit);
//        return stringRedisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit);
    }


    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        if (value instanceof String) {
            this.stringRedisTemplate.opsForValue().set(key, String.valueOf(value), timeout, timeUnit);
        } else {
            this.redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        }
    }

    public String getString(String key) {
        return this.stringRedisTemplate.opsForValue().get(key);
    }

    public long incrBy(String key, long value) {
        return this.stringRedisTemplate.opsForValue().increment(key, value);
    }


    public Object getObject(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }

    public <T> T getBean(String key, Class<T> clazz) {
        String str = getString(key);
        if (str == null) {
            return null;
        }
        return JsonUtil.json2Pojo(str, clazz);
    }

    public <T> T getBean(String key, TypeReference<T> typeReference) {
        String str = getString(key);
        if (str == null) {
            return null;
        }
        return JsonUtil.json2Obj(str, typeReference);
    }

    public void del(String key) {
        this.stringRedisTemplate.delete(key);
    }

    public boolean exist(String key) {
        return this.redisTemplate.hasKey(key);
    }

    public void addToList(String key, Object value) {
        ListOperations listOperations = this.redisTemplate.opsForList();
        listOperations.leftPush(key, value);
    }

    public List getList(String key) {
        ListOperations listOperations = this.redisTemplate.opsForList();
        return listOperations.range(key, 0, -1);
    }

    public RedisAtomicLong getRedisAtomicLong(String key) {
        RedisAtomicLong counter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        return counter;
    }

    public synchronized boolean setBit(String key, Integer offset, boolean value, long timeout, TimeUnit timeUnit) {
        this.redisTemplate.opsForValue().setBit(key, offset, value);
        return expire(key, timeout, timeUnit);
    }

    public synchronized boolean setMBit(String key, List<Integer> offsets, boolean value, long timeout, TimeUnit timeUnit) {
        this.redisTemplate.execute((RedisCallback) connection -> {
            offsets.stream().forEach(V -> connection.setBit(rawKey(key), V, value));
            return true;
        });
        return expire(key, timeout, timeUnit);
    }

    public Long bitCount(String cacheKey) {
        return (Long) redisTemplate.execute((RedisCallback<Long>) connection ->
                connection.bitCount(cacheKey.getBytes()));
    }

    public Map<String, Long> bitMCount(String... keys) {
        Map<String, Long> keysCount = new HashMap<>();
        this.redisTemplate.execute((RedisCallback) connection -> {
            for (String key : keys) {
                keysCount.put(key, connection.bitCount(rawKey(key)));
            }
            return true;
        });
        return keysCount;
    }

    public synchronized boolean ZAdd(String key, Object field, double score, long timeout, TimeUnit timeUnit) {
        this.redisTemplate.opsForZSet().add(key, field, score);
        return expire(key, timeout, timeUnit);
    }

    public Long ZCard(String key) {
        return this.redisTemplate.opsForZSet().zCard(key);
    }

    public Double ZScore(String key, Object field) {
        return this.redisTemplate.opsForZSet().score(key, field);
    }

    public Double ZINCRBY(String key, Object field, double score) {
        return this.redisTemplate.opsForZSet().incrementScore(key, field, score);
    }

    public Long ZCount(String key, double min, double max) {
        return this.redisTemplate.opsForZSet().count(key, min, max);
    }

    ThreadLocal<Double> benchmarkLocal = new ThreadLocal<>();

    public <T> Set<T> ZRangeProximate(String key, double benchmark, double min, double max, int count, Object... exceptFields) {
        return (Set<T>) ZRangeProximateWithScore(key, benchmark, min, max, count, exceptFields).stream()
                .map(DefaultTypedTuple::getValue).collect(Collectors.toSet());
    }

    public Set<DefaultTypedTuple> ZRangeProximateWithScore(String key, double benchmark, double min, double max, int count, Object... exceptFields) {
        benchmarkLocal.set(benchmark);
        Map<String, Integer> exceptFieldRaw = new HashMap<>();
        if (exceptFields != null && exceptFields.length > 0) {
            Arrays.stream(exceptFields).forEach(V -> exceptFieldRaw.put(MD5Encoding(rawValue(V)), 1));
        }
        final int dataSize = exceptFieldRaw.size() + count;
        Set<DefaultTypedTuple> collect = new HashSet<>();
        //向上取exceptFields
        collect.addAll(Optional.ofNullable(
                        this.redisTemplate.opsForZSet().rangeByScoreWithScores(key, benchmark, max, 0, dataSize))
                .orElse(new HashSet()));
        //向下取
        collect.addAll(Optional.ofNullable(
                        this.redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, benchmark, 0, dataSize))
                .orElse(new HashSet()));
        //处理排序
        List<DefaultTypedTuple> collect1 = collect.stream()
                .filter(V -> !exceptFieldRaw.containsKey(MD5Encoding(rawValue(V.getValue()))))
                .sorted(Comparator.comparingDouble(this::absBenchmarkScore))
                .collect(Collectors.toList());
        //计数器
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        //截取指定数量
        return collect1.stream().filter(V -> atomicInteger.addAndGet(1) <= count).collect(Collectors.toSet());
    }

    public <T> Set<T> ZRange(String key, long start, long end) {
        return this.redisTemplate.opsForZSet().range(key, start, end);
    }

    public <T> Set<T> ZRangeFromMin(String key, double min, int count) {
        Set<byte[]> result = (Set<byte[]>) redisTemplate.execute((RedisCallback<Set<byte[]>>) connection
                -> connection.zRangeByScore(
                rawKey(key),
                RedisZSetCommands.Range.range().gt(min),
                RedisZSetCommands.Limit.limit().count(count).offset(0)));
        return SerializationUtils.deserialize(result, redisTemplate.getValueSerializer());
    }

    public <T> Set<T> ZRangeBYLEX(String key, RedisZSetCommands.Range range) {
        return this.redisTemplate.opsForZSet().rangeByLex(key, range);
    }

    public <T> Set<T> ZRangeBYLEX(String key, RedisZSetCommands.Range range, RedisZSetCommands.Limit limit) {
        return this.redisTemplate.opsForZSet().rangeByLex(key, range, limit);
    }

    public <T> Set<T> ZRangeBYScore(String key, double min, double max) {
        return this.redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    public <T> Set<T> ZRangeBYScore(String key, double min, double max, long offset, long count) {
        return this.redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
    }

    public Long ZRem(String key, Object... fields) {
        return this.redisTemplate.opsForZSet().remove(key, fields);
    }

    public <T> Set<T> ZReverseRange(String key, long start, long end) {
        return this.redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    public <T> Set<T> ZReverseRangeByScore(String key, double min, double max) {
        return this.redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
    }

    public <T> Set<T> ZReverseRangeByScore(String key, double min, double max, long offset, long count) {
        return this.redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, offset, count);
    }

    public <T> Set<T> ZReverseRangeFromMax(String key, double max, int count) {
        Set<byte[]> result = (Set<byte[]>) redisTemplate.execute((RedisCallback<Set<byte[]>>) connection
                -> connection.zRevRangeByScore(
                rawKey(key),
                RedisZSetCommands.Range.range().lt(max),
                RedisZSetCommands.Limit.limit().count(count).offset(0)));
        return SerializationUtils.deserialize(result, redisTemplate.getValueSerializer());
    }


    public synchronized void HSet(String key, String field, String value, long timeout, TimeUnit timeUnit) {
        this.redisTemplate.opsForHash().put(key, field, value);
        expire(key, timeout, timeUnit);
    }

    public String HGet(String key, String field) {
        return (String) this.redisTemplate.opsForHash().get(key, field);
    }

    public Long HDel(String key, String... field) {
        return this.redisTemplate.opsForHash().delete(key, field);
    }

    public synchronized void HMSet(String key, Map<String, String> kvMap, long timeout, TimeUnit timeUnit) {
        this.redisTemplate.opsForHash().putAll(key, kvMap);
        expire(key, timeout, timeUnit);
    }

    public List<String> HMGet(String key, List<String> fields) {
        return this.redisTemplate.opsForHash().multiGet(key, fields);
    }

    public Boolean HExists(String key, String field) {
        return this.redisTemplate.opsForHash().hasKey(key, field);
    }

    public Long HLen(String key) {
        return this.redisTemplate.opsForHash().size(key);
    }

    public <K> Boolean expire(K key, final long timeout, final TimeUnit unit) {
        return this.redisTemplate.expire(key, timeout, unit);
    }

    public <K> long getExpire(K key) {
        return this.redisTemplate.getExpire(key);
    }

    public <K> long getExpire(K key, final TimeUnit timeUnit) {
        return this.redisTemplate.getExpire(key, timeUnit);
    }

    public <K, V> Long hyperAdd(long timeout, TimeUnit timeUnit, K key, V... values) {
        long addResult = this.redisTemplate.opsForHyperLogLog().add(key, values);
        this.expire(key, timeout, timeUnit);
        return addResult;
    }

    public <K> Long hyperSize(K key) {
        return this.redisTemplate.opsForHyperLogLog().size(key);
    }

    public <K> void hyperDelete(K key) {
        this.redisTemplate.opsForHyperLogLog().delete(key);
    }

    private byte[] rawKey(Object key) {
        Assert.notNull(key, "[RedisServiceImpl|rawKey] non null key required");
        if (this.redisTemplate.getKeySerializer() == null && key instanceof byte[]) {
            return (byte[]) key;
        }
        return this.redisTemplate.getKeySerializer().serialize(key);
    }

    private byte[] rawValue(Object key) {
        Assert.notNull(key, "[RedisServiceImpl|rawKey] non null key required");
        if (this.redisTemplate.getValueSerializer() == null && key instanceof byte[]) {
            return (byte[]) key;
        }
        return this.redisTemplate.getValueSerializer().serialize(key);
    }

    /**
     * 私有方法
     *
     * @param tuple
     * @return
     */
    private double absBenchmarkScore(DefaultTypedTuple tuple) {
        final double v = Optional.ofNullable(benchmarkLocal.get()).orElse(Double.MAX_VALUE) - Optional.ofNullable(tuple.getScore()).orElse(0d);
        return Math.abs(v);
    }

    private String MD5Encoding(byte[] value) {
        return Md5Crypt.md5Crypt(value);
    }

}
