package ru.practicum.request.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.event.dto.EventRequestStatusUpdateResult;
import ru.practicum.request.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.model.Request;

import java.util.Collection;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestMapper {

    public static ParticipationRequestDto toParticipationRequestDto(Request request) {

        return ParticipationRequestDto.builder()
                .id(request.getId())
                .event(request.getEvent().getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .created(request.getCreated().toLocalDateTime())
                .build();
    }

    public static Collection<ParticipationRequestDto> toParticipationRequestDtoCollection(
            Collection<Request> requests) {
        return requests.stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    public static EventRequestStatusUpdateResultDto toEventRequestStatusUpdateResultDto(
            EventRequestStatusUpdateResult result) {
        return EventRequestStatusUpdateResultDto.builder()
                .confirmedRequests(result.getConfirmedRequests().stream()
                        .map(RequestMapper::toParticipationRequestDto)
                        .collect(Collectors.toList()))
                .rejectedRequests(result.getRejectedRequests().stream()
                        .map(RequestMapper::toParticipationRequestDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
