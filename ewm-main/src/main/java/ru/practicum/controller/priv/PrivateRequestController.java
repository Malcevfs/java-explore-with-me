package ru.practicum.controller.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.service.RequestService;

import javax.validation.constraints.Min;
import java.util.Collection;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
@Slf4j
public class PrivateRequestController {

    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(@PathVariable @Min(0) long userId,
                                                 @RequestParam @Min(0) long eventId) {
        log.info("Добавление запроса на участии в событии от пользователя userId={} для eventId={}", userId, eventId);
        return RequestMapper.toParticipationRequestDto(requestService.create(userId, eventId));
    }

    @GetMapping
    public Collection<ParticipationRequestDto> getUserRequests(@PathVariable @Min(0) long userId) {
        log.info("Заявки на участия в событиях от пользователя userId={}", userId);
        return RequestMapper.toParticipationRequestDtoCollection(requestService.getAll(userId));
    }

    @PatchMapping("{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable @Min(0) long userId,
                                                 @PathVariable @Min(0) long requestId) {
        log.info("Отмена запроса на участии в событии пользователем userId={} для requestId{}", userId, requestId);
        return RequestMapper.toParticipationRequestDto(requestService.cancelRequest(userId, requestId));
    }

}
