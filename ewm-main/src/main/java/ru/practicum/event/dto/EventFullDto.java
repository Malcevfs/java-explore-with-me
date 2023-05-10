package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.dto.CategoryResponseDto;
import ru.practicum.location.dto.LocationResponseDto;
import ru.practicum.user.dto.UserShortDto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static ru.practicum.util.Constants.DATE_TIME;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    public Long id;
    LocationResponseDto location;
    private String annotation;
    private CategoryResponseDto category;
    private Integer confirmedRequests;
    @JsonFormat(pattern = DATE_TIME)
    private LocalDateTime createdOn;
    private String description;
    @JsonFormat(pattern = DATE_TIME)
    private Timestamp eventDate;
    private UserShortDto initiator;
    private Boolean paid;
    private Integer participantLimit;
    @JsonFormat(pattern = DATE_TIME)
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private String state;
    private String title;
    private Integer views;
    private Integer likes;
    private Integer dislikes;
}
