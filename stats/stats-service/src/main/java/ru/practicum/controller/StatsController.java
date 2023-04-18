package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.HitDto;
import ru.practicum.HitOutputDto;
import ru.practicum.Stat;
import ru.practicum.service.StatsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Validated
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(value = HttpStatus.CREATED)
    public HitOutputDto save(@Valid @RequestBody HitDto hitDto) {
        return statsService.saveHit(hitDto);
    }

    @GetMapping("/stats")
    public List<Stat> get(@RequestParam(name = "start")
                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                          @RequestParam(name = "end")
                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                          @RequestParam(name = "uris", defaultValue = "") List<String> uris,
                          @RequestParam(name = "unique", defaultValue = "false") boolean unique) {
        return statsService.getHits(start, end, uris, unique);
    }
}
