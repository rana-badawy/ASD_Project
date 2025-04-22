package edu.miu.cs.cs489.surgeries.exception;

import java.time.Instant;

public record ApiError(
        String message,
        String path,
        Integer httpStatusCode,
        Instant instant
) {
}
