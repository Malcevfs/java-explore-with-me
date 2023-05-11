package ru.practicum.rates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.rates.model.Rate;

public interface RateRepository extends JpaRepository<Rate, Long> {

    Rate findByUserIdAndEventId(long userId, long eventId);

    void deleteByEventIdAndUserId(long eventId, long userId);
}
