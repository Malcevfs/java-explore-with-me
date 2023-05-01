package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryResponseDto;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto create(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Создана категория - {}", categoryDto.toString());
        return CategoryMapper.toCategoryResponseDto(
                categoryService.create(CategoryMapper.toCategory(categoryDto)));
    }

    @DeleteMapping("{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @Min(0) long catId) {
        log.info("Удаление категории id={}", catId);
        categoryService.deleteById(catId);
    }

    @PatchMapping("{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDto update(@Valid @RequestBody CategoryDto categoryDto,
                                      @PathVariable @Min(0) long catId) {
        log.info("Обновление категории с id={}, на {}", catId, categoryDto.toString());
        return CategoryMapper.toCategoryResponseDto(
                categoryService.update(catId, CategoryMapper.toCategory(categoryDto)));
    }
}
