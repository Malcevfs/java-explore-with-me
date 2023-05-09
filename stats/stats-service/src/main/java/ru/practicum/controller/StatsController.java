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

import static ru.practicum.util.Constants.DATE_TIME;

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
                          @DateTimeFormat(pattern = DATE_TIME) LocalDateTime start,
                          @RequestParam(name = "end")
                          @DateTimeFormat(pattern = DATE_TIME) LocalDateTime end,
                          @RequestParam(name = "uris", defaultValue = "") List<String> uris,
                          @RequestParam(name = "unique", defaultValue = "false") boolean unique) {
        return statsService.getHits(start, end, uris, unique);
    }
}
