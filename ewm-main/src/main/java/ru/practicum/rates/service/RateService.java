package ru.practicum.rates.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.rates.model.Rate;
import ru.practicum.rates.repository.RateRepository;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RateRepository repository;

    public Rate setRate(long userId, long eventId, boolean like, boolean dislike) {

        return repository.save(Rate.builder().
                userId(userId).
                eventId(eventId).
                likes(like).
                dislikes(dislike).build());
    }

    @Transactional
    public void deleteRate(long userId, long eventId) {
        repository.deleteByEventIdAndUserId(eventId, userId);
    }
}
