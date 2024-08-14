package com.example.todolist.Util;

import java.util.UUID;

public class UUIDUitls {

    public static String generateShortUUID() {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString().replace("-", ""); // UUID 문자열에서 '-' 제거
        return uuidString.substring(0, 8); // 첫 8자리만 반환
    }


}
