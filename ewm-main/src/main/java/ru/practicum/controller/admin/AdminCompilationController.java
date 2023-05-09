package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.service.CompilationService;
import ru.practicum.validation.group.Create;
import ru.practicum.validation.group.Update;

import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Slf4j
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@Validated(Create.class) @RequestBody NewCompilationDto dto) {
        log.info("Создание подборки {}", dto.toString());
        return CompilationMapper.toCompilationDto(
                compilationService.create(CompilationMapper.toCompilation(dto)));
    }

    @DeleteMapping("{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable @Min(0) long compId) {
        log.info("Удаление подборки id={}", compId);
        compilationService.delete(compId);
    }

    @PatchMapping("{compId}")
    public CompilationDto update(@Validated(Update.class) @RequestBody NewCompilationDto dto,
                                 @PathVariable @Min(0) long compId) {
        log.info("Обновление подборки с id={}, на {}", compId, dto.toString());
        return CompilationMapper.toCompilationDto(
                compilationService.update(compId, CompilationMapper.toCompilation(dto)));
    }
}
