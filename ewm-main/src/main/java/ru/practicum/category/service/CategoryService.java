package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exception.NotFoundException;

import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteById(long catId) {
        findById(catId);
        categoryRepository.deleteById(catId);
    }

    @Transactional
    public Category update(long catId, Category category) {
        category.setId(catId);
        return categoryRepository.save(category);
    }

    public Collection<Category> findCategories(int from, int size) {
        return categoryRepository.findAll(PageRequest.of(from, size))
                .toList();
    }

    public Category findById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Категория с id = {}, не найдена" + id)));
    }

}
