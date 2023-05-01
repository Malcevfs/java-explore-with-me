package ru.practicum.location.mapper;

import ru.practicum.location.dto.LocationRequestDto;
import ru.practicum.location.dto.LocationResponseDto;
import ru.practicum.location.model.Location;

public class LocationMapper {

    public static Location toLocation(LocationRequestDto dto) {
        if (dto == null) return null;
        return Location.builder()
                .lat(dto.getLat())
                .lon(dto.getLon())
                .build();
    }

    public static LocationResponseDto toLocationResponseDto(Location location) {
        return LocationResponseDto.builder()
                .lat(location.getLat())
                .lon(location.getLon())
                .build();
    }
}
