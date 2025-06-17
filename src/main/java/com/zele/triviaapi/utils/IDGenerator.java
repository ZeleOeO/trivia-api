package com.zele.triviaapi.utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class IDGenerator {
    private IDGenerator() {
    }

    public static String generateQuizID() {
        return "quiz" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    public static String generateQuestionID() {
        return "question" + UUID.randomUUID().toString().replace("-", "");
    }
}
