package com.binance.demo.mapper;

import com.binance.demo.entity.CandlestickStream;
import org.apache.ibatis.annotations.Mapper;

/**
* @author wangjinsheng
* @description 针对表【CandlestickEvent(K线图数据)】的数据库操作Mapper
* @createDate 2023-05-20 12:06:17
* @Entity com.binance.demo.entity.CandlestickStream
*/
@Mapper
public interface CandlestickStreamMapper {

    int deleteByPrimaryKey(Long id);

    int insert(CandlestickStream record);

    int insertSelective(CandlestickStream record);

    CandlestickStream selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CandlestickStream record);

    int updateByPrimaryKey(CandlestickStream record);

}
