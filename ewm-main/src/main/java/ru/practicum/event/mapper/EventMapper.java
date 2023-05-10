package ru.practicum.event.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.event.dto.*;
import ru.practicum.event.model.Event;
import ru.practicum.location.model.Location;
import ru.practicum.util.State;
import ru.practicum.location.mapper.LocationMapper;
import ru.practicum.user.mapper.UserMapper;

import java.util.Collection;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {

    public static Event toEvent(NewEventDto dto) {
        return Event.builder()
                .annotation(dto.getAnnotation())
                .category(Category.builder().id(dto.getCategory()).build())
                .description(dto.getDescription())
                .eventDate(dto.getEventDate())
                .location(Location.builder().lat(dto.getLocation().getLat()).lon(dto.getLocation().getLon()).build())
                .paid(dto.isPaid())
                .participantLimit(dto.getParticipantLimit())
                .requestModeration(dto.isRequestModeration())
                .title(dto.getTitle())
                .build();
    }

    public static Event toEvent(UpdateEventUserRequest updateUserDto) {
        Event event = new Event();
        if (updateUserDto.getAnnotation() != null) event.setLocation(
                LocationMapper.toLocation(updateUserDto.getLocation()));
        if (updateUserDto.getCategory() != null) event.setCategory(
                Category.builder().id(updateUserDto.getCategory()).build());
        if (updateUserDto.getDescription() != null) event.setDescription(
                updateUserDto.getDescription());
        if (updateUserDto.getEventDate() != null) event.setEventDate(
                updateUserDto.getEventDate());
        if (updateUserDto.getLocation() != null) event.setLocation(LocationMapper.toLocation(
                updateUserDto.getLocation()));
        if (updateUserDto.getPaid() != null) event.setPaid(
                updateUserDto.getPaid());
        if (updateUserDto.getParticipantLimit() != null) event.setParticipantLimit(
                updateUserDto.getParticipantLimit());
        if (updateUserDto.getRequestModeration() != null) event.setRequestModeration(
                updateUserDto.getRequestModeration());
        if (updateUserDto.getStateAction() != null) event.setState(findState(
                updateUserDto.getStateAction()));
        if (updateUserDto.getTitle() != null) event.setTitle(
                updateUserDto.getTitle());
        return event;
    }

    public static Event toEvent(UpdateEventAdminRequest updateAdminDto) {
        Event event = new Event();
        if (updateAdminDto.getAnnotation() != null) event.setAnnotation(
                updateAdminDto.getAnnotation());
        if (updateAdminDto.getCategory() != null) event.setCategory(
                Category.builder().id(updateAdminDto.getCategory()).build());
        if (updateAdminDto.getDescription() != null) event.setDescription(
                updateAdminDto.getDescription());
        if (updateAdminDto.getEventDate() != null) event.setEventDate(
                updateAdminDto.getEventDate());
        if (updateAdminDto.getLocation() != null) event.setLocation(
                LocationMapper.toLocation(updateAdminDto.getLocation()));
        if (updateAdminDto.getPaid() != null) event.setPaid(
                updateAdminDto.getPaid());
        if (updateAdminDto.getParticipantLimit() != null) event.setParticipantLimit(
                updateAdminDto.getParticipantLimit());
        if (updateAdminDto.getRequestModeration() != null) event.setRequestModeration(
                updateAdminDto.getRequestModeration());
        if (updateAdminDto.getStateAction() != null) event.setState(findState(
                updateAdminDto.getStateAction()));
        if (updateAdminDto.getTitle() != null) event.setTitle(
                updateAdminDto.getTitle());
        return event;
    }

    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryResponseDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn().toLocalDateTime())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(LocationMapper.toLocationResponseDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn().toLocalDateTime())
                .requestModeration(event.getRequestModeration())
                .state(event.getState().toString())
                .title(event.getTitle())
                .views(event.getViews().intValue())
                .likes(event.getLikes().intValue())
                .dislikes(event.getDislikes().intValue())
                .build();
    }

    public static Event updateEvent(Event oldEvent, Event newEvent) {
        if (oldEvent.getAnnotation() != null) newEvent.setAnnotation(oldEvent.getAnnotation());
        if (oldEvent.getCategory() != null) newEvent.setCategory(oldEvent.getCategory());
        if (oldEvent.getDescription() != null) newEvent.setDescription(oldEvent.getDescription());
        if (oldEvent.getEventDate() != null) newEvent.setEventDate(oldEvent.getEventDate());
        if (oldEvent.getLocation() != null) newEvent.setLocation(oldEvent.getLocation());
        if (oldEvent.getPaid() != null) newEvent.setPaid(oldEvent.getPaid());
        if (oldEvent.getParticipantLimit() != null) newEvent.setParticipantLimit(oldEvent.getParticipantLimit());
        if (oldEvent.getRequestModeration() != null) newEvent.setRequestModeration(oldEvent.getRequestModeration());
        if (oldEvent.getState() != null) newEvent.setState(oldEvent.getState());
        if (oldEvent.getTitle() != null) newEvent.setTitle(oldEvent.getTitle());
        return newEvent;
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryResponseDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static Collection<EventShortDto> toEventShortDtoCollection(Collection<Event> events) {
        return events.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());

    }

    public static Collection<EventFullDto> toEventFullDtoCollection(Collection<Event> events) {
        return events.stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    private static State findState(String str) {
        if (str == null) return null;
        if (str.equals("CANCEL_REVIEW")) return State.CANCELED;
        if (str.equals("PUBLISH_EVENT")) return State.PUBLISHED;
        if (str.equals("REJECT_EVENT")) return State.CANCELED;
        if (str.equals("SEND_TO_REVIEW")) return State.PENDING;
        return null;
    }
}
