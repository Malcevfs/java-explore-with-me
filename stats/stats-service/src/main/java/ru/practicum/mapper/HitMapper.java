package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.HitDto;
import ru.practicum.HitOutputDto;
import ru.practicum.model.App;
import ru.practicum.model.Hit;

@UtilityClass
public class HitMapper {
    public static Hit toHit(HitDto dto, App app) {
        Hit hit = new Hit();

        hit.setApp(app);
        hit.setUri(dto.getUri());
        hit.setIp(dto.getIp());
        hit.setTimeStamp(dto.getTimeStamp());

        return hit;
    }

    public static HitOutputDto toOutputDto(Hit hit) {
        return HitOutputDto.builder()
                .id(hit.getId())
                .app(hit.getApp().getName())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .timeStamp(hit.getTimeStamp())
                .build();
    }
}
