package com.finance.LoanAdvisor.customer.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CustomerEligiblityVO {
  private Integer LoanAmount;
  private Double roi;
}
