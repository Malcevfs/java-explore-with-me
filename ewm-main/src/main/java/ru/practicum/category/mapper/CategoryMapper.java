package ru.practicum.category.mapper;

import ru.practicum.category.model.Category;
import ru.practicum.category.dto.CategoryResponseDto;
import ru.practicum.category.dto.CategoryDto;

import java.util.Collection;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static Category toCategory(CategoryDto dto) {
        return Category.builder()
                .name(dto.getName())
                .build();
    }

    public static CategoryResponseDto toCategoryResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Collection<CategoryResponseDto> toCategoryResponseDtoCollection(Collection<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::toCategoryResponseDto)
                .collect(Collectors.toList());
    }
}
