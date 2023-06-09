package ru.practicum.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserResponseDto {
    @NotBlank
    @Email
    private String email;
    private Long id;
    @NotBlank
    private String name;
    private Float rate;
}
