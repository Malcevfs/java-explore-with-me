package ru.practicum.rates.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.rates.model.Rate;
import ru.practicum.rates.repository.RateRepository;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;
    private final EventRepository eventRepository;

    @Transactional
    public Rate setRate(long userId, long eventId, boolean like, boolean dislike) {
        Rate rate = rateRepository.findByUserIdAndEventId(userId, eventId);
        if (rate != null) {
            if (rate.getLikes() && like) {
                eventRepository.removeLikesById(eventId);
                rateRepository.deleteByEventIdAndUserId(eventId, userId);
            }
            if (rate.getDislikes() && dislike) {
                eventRepository.removeDislikesById(eventId);
                rateRepository.deleteByEventIdAndUserId(eventId, userId);
            }
            if (rate.getLikes() && dislike) {
                rate.setDislikes(true);
                rate.setLikes(false);
                eventRepository.removeLikesById(eventId);
                eventRepository.incrementDislikesById(eventId);
                rateRepository.save(rate);
            }
            if (rate.getDislikes() && like) {
                rate.setDislikes(false);
                rate.setLikes(true);
                eventRepository.removeDislikesById(eventId);
                eventRepository.incrementLikesById(eventId);
                rateRepository.save(rate);
            }
        } else {
            rate = rateRepository.save(Rate.builder()
                    .userId(userId)
                    .eventId(eventId)
                    .likes(like)
                    .dislikes(dislike).build());
            if (like) {
                eventRepository.incrementLikesById(eventId);
            }
            if (dislike) {
                eventRepository.incrementDislikesById(eventId);
            }
        }
        return rate;
    }

}
