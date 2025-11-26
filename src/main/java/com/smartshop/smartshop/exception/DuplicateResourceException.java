package com.smartshop.smartshop.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
public class DuplicateResourceException extends RuntimeException {
    private final List<String> errors;
    public DuplicateResourceException(List<String> errors) {
        super("Resource already exists:\n");
        this.errors = errors;
    }
}
