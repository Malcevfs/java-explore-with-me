package ru.practicum.rates.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.rates.model.Rate;
import ru.practicum.rates.repository.RateRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

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
        setRateForEventAndInitiator(eventId);
        return rate;
    }

    public void setRateForEventAndInitiator(long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow();
        float allLikes = event.getLikes();
        float allRates = event.getLikes() + event.getDislikes();
        float rating = allLikes / allRates * 100;
        float newValue = (float) (Math.round(rating * 10.0) / 10.0);
        event.setRate(newValue);
        eventRepository.save(event);

        User user = userRepository.findById(event.getInitiator().getId()).orElseThrow();
        float countUserEvents = eventRepository.countByInitiatorId(event.getInitiator().getId());
        float newUserRate = eventRepository.getSumOfRatesByInitiatorId(user.getId()) / countUserEvents;
        user.setRate(newUserRate);
        userRepository.save(user);

    }
}
