package ru.practicum.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@ToString
@NonNull
@Getter
@Setter
@AllArgsConstructor
@Builder
public class NewUserDto {
    @NotBlank
    public String name;
    @NotBlank
    @Email
    private String email;
}
