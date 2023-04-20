package ru.practicum;

import kong.unirest.GenericType;
import kong.unirest.Unirest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsClient {
    private final String localUri = "http://localhost:9090";

    public void save(HitDto hitDto) {
        Unirest.post(localUri + "/hit").body(hitDto);
        log.info("Cохранение запроса c ip = {}, url = {}", hitDto.getIp(), hitDto.getUri());
    }

    public List<Stat> get(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        log.info("Запрос статистики uris = {}", uris);

        return Unirest.get(localUri + "/stats")
                .queryString("start", urlTimeEncode(start))
                .queryString("end", urlTimeEncode(end))
                .queryString("uris", uris)
                .queryString("unique", unique)
                .asObject(new GenericType<List<Stat>>() {
                })
                .getBody();
    }

    private String urlTimeEncode(LocalDateTime time) {
        String timeString = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return URLEncoder.encode(timeString, StandardCharsets.UTF_8);
    }
}
