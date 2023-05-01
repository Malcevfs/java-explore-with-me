package ru.practicum.user.mapper;

import ru.practicum.user.dto.NewUserDto;
import ru.practicum.user.dto.UserResponseDto;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toUser(NewUserDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .build();
    }

    public static UserResponseDto toUserResponseDto(User user) {
        return UserResponseDto.builder()
                .email(user.getEmail())
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public static UserShortDto toUserShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public static Collection<UserResponseDto> toUserResponseDtoCollection(Collection<User> users) {
        return users.stream()
                .map(UserMapper::toUserResponseDto)
                .collect(Collectors.toList());
    }
}
