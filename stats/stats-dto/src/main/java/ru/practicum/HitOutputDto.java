package ru.practicum;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static ru.practicum.util.Constants.DATE_TIME;


@Builder(toBuilder = true)
@Getter
public class HitOutputDto {
    private final Long id;
    private final String app;
    private final String uri;
    private final String ip;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME)
    @JsonProperty("timestamp")
    private final LocalDateTime timeStamp;

}
