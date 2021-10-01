package com.finance.loanadvisorr.security.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse
{
    @NonNull
    private String jwt;
    private String refreshToken;
    @NonNull
    private List<String> roles;


}
