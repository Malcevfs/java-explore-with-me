package ru.practicum.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.HitDto;
import ru.practicum.model.App;

@UtilityClass
public class AppMapper {
    public static App toApp(HitDto dto) {
        App app = new App();

        app.setName(dto.getApp());

        return app;
    }
}
