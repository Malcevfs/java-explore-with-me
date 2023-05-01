package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.model.Event;
import ru.practicum.event.service.EventService;
import ru.practicum.exception.NotFoundException;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.repository.CompilationRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventService eventService;

    public Collection<Compilation> getAll(boolean pinned, int from, int size) {
        return compilationRepository.findAllByPinned(pinned, PageRequest.of(from, size));
    }

    public Compilation create(Compilation compilation) {
        compilation.setEvents(new HashSet<>(eventService.getAll(
                compilation.getEvents().stream()
                        .map(Event::getId)
                        .collect(Collectors.toList())
        )));

        return compilationRepository.save(compilation);
    }


    public Compilation update(long compId, Compilation compilation) {
        Compilation recipient = getById(compId);
        return compilationRepository.save(CompilationMapper.update(recipient, compilation));
    }

    public void delete(long compId) {
        getById(compId);
        compilationRepository.deleteById(compId);
    }

    public Compilation getById(long compId) {
        return compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException("Не найдена коллекция с id=" + compId));
    }
}
