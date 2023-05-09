package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.dto.NewUserDto;
import ru.practicum.user.dto.UserResponseDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.List;

@Validated
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
public class AdminUserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto create(@Valid @RequestBody NewUserDto newUserDto) {
        log.info("Добавление нового пользователя {}", newUserDto.toString());
        return UserMapper.toUserResponseDto(
                userService.create(UserMapper.toUser(newUserDto)));
    }

    @GetMapping
    public Collection<UserResponseDto> getUsersByParameters(
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(defaultValue = "0") @Min(0) int from,
            @RequestParam(defaultValue = "10") @Min(1) int size) {
        log.info("Получение списка пользователей с ids={}, from={}, size={}", ids, from, size);
        return UserMapper.toUserResponseDtoCollection(
                userService.getUsers(ids, from, size));
    }

    @DeleteMapping("{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable @Min(0) long userId) {
        log.info("Удаление пользователя с id={}", userId);
        userService.deleteById(userId);
    }

}
