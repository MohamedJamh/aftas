package com.aftas.exception.custom;

import com.aftas.utils.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ValidationException extends Exception{
    private final ErrorMessage errorMessage;
}
