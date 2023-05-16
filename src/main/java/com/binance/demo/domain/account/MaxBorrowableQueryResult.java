package com.binance.demo.domain.account;

import com.binance.api.client.domain.account.Withdraw;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Max Borrow Query Result
 *
 * @see Withdraw
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MaxBorrowableQueryResult {

  private String amount;

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "MaxBorrowQueryResult{" +
            "amount='" + amount + '\'' +
            '}';
  }
}
