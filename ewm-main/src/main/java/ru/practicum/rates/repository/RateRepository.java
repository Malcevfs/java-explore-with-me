package ru.practicum.rates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.rates.model.Rate;

public interface RateRepository extends JpaRepository<Rate, Long> {

    public void deleteByEventIdAndUserId(long eventId, long userId);
}
