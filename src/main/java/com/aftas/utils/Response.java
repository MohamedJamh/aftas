package com.aftas.utils;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private String message;
    private T result;
    private List<ErrorMessage> errors;
}
