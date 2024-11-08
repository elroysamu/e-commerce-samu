package com.E_Commerce.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApiException extends RuntimeException {
    private static final long serialVersionUId = 1L;

    public ApiException(String message) {
        super(message);
    }
}
