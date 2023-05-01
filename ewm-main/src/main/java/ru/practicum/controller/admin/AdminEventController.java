package ru.practicum.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.UpdateEventAdminRequest;
import ru.practicum.event.service.EventService;
import ru.practicum.util.State;
import ru.practicum.event.mapper.EventMapper;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Validated
@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
public class AdminEventController {

    private final EventService eventService;

    @PatchMapping("{eventId}")
    public EventFullDto update(@PathVariable @Min(0) long eventId,
                               @Valid @RequestBody UpdateEventAdminRequest request) {
        return EventMapper.toEventFullDto(eventService.updateByAdmin(eventId,
                EventMapper.toEvent(request)));
    }

    @GetMapping
    public Collection<EventFullDto> getAllByParameters(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<State> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) Timestamp rangeStart,
            @RequestParam(required = false) Timestamp rangeEnd,
            @RequestParam(defaultValue = "0", required = false) @Min(0) final int from,
            @RequestParam(defaultValue = "10", required = false) @Min(1) final int size) {

        return EventMapper.toEventFullDtoCollection(eventService.getAllByParameters(users, states, categories,
                rangeStart, rangeEnd, from, size));
    }
}
