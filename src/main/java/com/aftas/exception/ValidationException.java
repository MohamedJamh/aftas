package com.aftas.exception;

import com.aftas.utils.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ValidationException extends Exception{
    private ErrorMessage errorMessage;
}
