package ru.practicum.controller.pub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.StatsClient;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.service.EventService;
import ru.practicum.util.SortEvent;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Validated
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class PublicEventController {

    private final EventService eventService;
    private final StatsClient statsClient;

    @GetMapping
    public Collection<EventShortDto> getFilteredEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) Timestamp rangeStart,
            @RequestParam(required = false) Timestamp rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) SortEvent sort,
            @RequestParam(defaultValue = "0") @Min(0) int from,
            @RequestParam(defaultValue = "10") @Min(1) int size, HttpServletRequest request,
            @RequestParam(required = false) String rate) {
        statsClient.createHit(request);
        return EventMapper.toEventShortDtoCollection(
                eventService.getAllByParametersPublic(text, categories, paid, rangeStart,
                        rangeEnd, onlyAvailable, sort, from, size, rate));
    }

    @GetMapping("{id}")
    public EventFullDto getById(
            @PathVariable @Min(0) long id, HttpServletRequest request) {
        statsClient.createHit(request);
        return EventMapper.toEventFullDto(
                eventService.getById(id));
    }
}
