package com.aftas.exception.custom;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InValidRefreshTokenException extends Exception {
    private final String message;
}
