package ru.practicum.controller.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryResponseDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.service.CategoryService;

import javax.validation.constraints.Min;
import java.util.Collection;

@Validated
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class PublicCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Collection<CategoryResponseDto> getAll(
            @RequestParam(defaultValue = "0") @Min(0) int from,
            @RequestParam(defaultValue = "10") @Min(1) int size) {
        return CategoryMapper.toCategoryResponseDtoCollection(
                categoryService.findCategories(from, size));
    }

    @GetMapping("{catId}")
    public CategoryResponseDto getById(@PathVariable @Min(0) long catId) {
        return CategoryMapper.toCategoryResponseDto(
                categoryService.findById(catId));
    }
}
