package ru.practicum.controller.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.service.CompilationService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@Validated
@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor

public class PublicCompilationController {

    private final CompilationService compilationService;

    @GetMapping
    public Collection<CompilationDto> getAll(
            @RequestParam(required = false) boolean pinned,
            @PositiveOrZero @RequestParam(defaultValue = "0", required = false) @Min(0) int from,
            @Positive @RequestParam(defaultValue = "10", required = false) @Min(1) int size) {
        return CompilationMapper.toCompilationDtoCollection(
                compilationService.getAll(pinned, from, size));
    }

    @GetMapping("{compId}")
    public CompilationDto getById(@PathVariable @Min(0) long compId) {
        return CompilationMapper.toCompilationDto(
                compilationService.getById(compId));
    }
}
