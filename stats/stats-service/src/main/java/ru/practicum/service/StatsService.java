package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.HitDto;
import ru.practicum.HitOutputDto;
import ru.practicum.Stat;
import ru.practicum.exception.PeriodException;
import ru.practicum.mapper.HitMapper;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
public class StatsService {
    private final StatsRepository statsRepository;

    public HitOutputDto saveHit(HitDto hitDto) {

        return HitMapper.toOutputDto(statsRepository.save(HitMapper.toHit(hitDto)));
    }

    public List<Stat> getHits(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        if (end.isBefore(start)) {
            throw new PeriodException("Дата окончания поиска не может быть раньше начала.");
        }

        if (uris.isEmpty()) {
            if (unique) {
                return statsRepository.getStatForUniqueIpWhithoutUri(start, end);
            } else {
                return statsRepository.getStatForNotUniqueIpWhithoutUri(start, end);
            }
        } else {
            if (unique) {
                return statsRepository.getStatForUniqueIp(start, end, uris);
            } else {
                return statsRepository.getStatForNotUniqueIp(start, end, uris);
            }
        }
    }

}
