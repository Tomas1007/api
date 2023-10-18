package com.ejemplo.api.dto;
import java.util.List;

public record UserListDto(
        Integer id,
        String name,
        String lastname,
        String email,
        Long phoneNumber,
        List<String> nombreRol
) {
}
