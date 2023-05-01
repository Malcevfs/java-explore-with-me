package ru.practicum.compilation.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.compilation.model.Compilation;

import java.util.Collection;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    Collection<Compilation> findAllByPinned(boolean pinned, PageRequest pageRequest);
}
