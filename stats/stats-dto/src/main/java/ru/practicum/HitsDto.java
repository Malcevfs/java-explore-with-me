package ru.practicum;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.Constants.DATE_TIME;


@Builder(toBuilder = true)
@Getter
public class HitsDto {
    @NotBlank
    private final String app;
    @NotEmpty
    private final List<String> uris;
    @NotBlank
    private final String ip;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME)
    @JsonProperty("timestamp")
    private final LocalDateTime timeStamp;
}
