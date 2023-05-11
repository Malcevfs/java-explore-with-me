package ru.practicum.controller.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.*;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.service.EventService;
import ru.practicum.rates.model.Rate;
import ru.practicum.rates.service.RateService;
import ru.practicum.request.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;

@Validated
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
public class PrivateEventController {

    private final EventService eventService;
    private final RequestService requestService;
    private final RateService rateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(
            @PathVariable @Min(0) long userId,
            @RequestBody @Valid NewEventDto dto) {
        log.info("Создание события {}", dto.toString());
        return EventMapper.toEventFullDto(
                eventService.create(userId, EventMapper.toEvent(dto)));
    }

    @PatchMapping("{eventId}")
    public EventFullDto updateEvent(
            @PathVariable @Min(0) long userId,
            @PathVariable @Min(0) long eventId,
            @Valid @RequestBody UpdateEventUserRequest dto) {
        log.info("Обновление события userId ={} и eventId={}, на {}", userId, eventId, dto.toString());
        return EventMapper.toEventFullDto(
                eventService.update(userId, eventId, EventMapper.toEvent(dto)));
    }

    @PatchMapping("{eventId}/requests")
    public EventRequestStatusUpdateResultDto updateEvent(
            @PathVariable @Min(0) long userId,
            @PathVariable @Min(0) long eventId,
            @Valid @RequestBody EventRequestStatusUpdateRequest requestStatusUpdate) {
        log.info("Обновление статуса события пользоватем userId ={}, eventId={}, for {}", userId, eventId, requestStatusUpdate.toString());
        return RequestMapper.toEventRequestStatusUpdateResultDto(
                requestService.updateStatus(userId, eventId, requestStatusUpdate));

    }

    @GetMapping
    public Collection<EventShortDto> getAll(
            @PathVariable @Min(0) Long userId,
            @RequestParam(defaultValue = "0") @Min(0) int from,
            @RequestParam(defaultValue = "10") @Min(1) int size) {
        log.info("Получение списка события пользователем userId={}, from={}, size={}", userId, from, size);
        return EventMapper.toEventShortDtoCollection(
                eventService.getAll(userId, from, size));
    }

    @GetMapping("{eventId}")
    public EventFullDto getUserEvent(
            @PathVariable @Min(0) long userId,
            @PathVariable @Min(0) long eventId) {
        log.info("Получение информации о событии пользователем userId={}, eventId={}", userId, eventId);
        return EventMapper.toEventFullDto(eventService.getUserEventById(eventId, userId));
    }

    @GetMapping("/{eventId}/requests")
    public Collection<ParticipationRequestDto> getUserEventRequests(
            @PathVariable @Min(0) long userId,
            @PathVariable @Min(0) long eventId) {
        return RequestMapper.toParticipationRequestDtoCollection(
                requestService.getEventRequests(userId, eventId));
    }

    @PostMapping("/{eventId}/rate")
    public Rate setRateOnEvent(@PathVariable @Min(0) long userId,
                               @PathVariable @Min(0) long eventId,
                               @RequestParam(defaultValue = "false") boolean like,
                               @RequestParam(defaultValue = "false") boolean dislike) {
        log.info("Оценка события пользователем userId={}, eventId={}", userId, eventId);
        return rateService.setRate(userId, eventId, like, dislike);
    }
}
