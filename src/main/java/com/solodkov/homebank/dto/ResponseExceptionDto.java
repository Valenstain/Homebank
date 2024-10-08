package com.solodkov.homebank.dto;

public record ResponseExceptionDto(
    int code,
    String message
) {

}
