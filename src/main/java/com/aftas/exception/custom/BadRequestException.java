package com.aftas.exception.custom;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class BadRequestException extends Exception {
    private final String message;
}
