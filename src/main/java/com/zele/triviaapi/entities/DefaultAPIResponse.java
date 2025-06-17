package com.zele.triviaapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class DefaultAPIResponse<T> {
    private int status;
    private String message;
    private T data;


    @Override
    public String toString() {
        return String.format(
                """
                        "status": %s,
                        "message": %s,
                        "data": %s
                        """,
                status, message, data.toString()
        );
    }
}
