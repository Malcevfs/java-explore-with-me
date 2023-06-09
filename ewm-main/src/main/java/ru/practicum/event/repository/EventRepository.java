package ru.practicum.event.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.event.model.Event;
import ru.practicum.util.State;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Collection<Event> findAllByInitiatorId(long initiator, PageRequest pageRequest);

    Optional<Event> findByIdAndInitiatorId(long eventId, long userId);

    @Query("FROM Event e where " +
            "(e.eventDate between :rangeStart and :rangeEnd) and " +
            "((:initiators is null) or e.initiator.id in :initiators) and " +
            "((:categories is null) or e.category.id in :categories) and" +
            "((:states is null) or e.state in :states)")
    Collection<Event> findByParameters(List<Long> initiators,
                                       List<State> states,
                                       List<Long> categories,
                                       Timestamp rangeStart,
                                       Timestamp rangeEnd,
                                       PageRequest pageRequest);

    @Query("FROM Event e WHERE " +
            "(e.state = 'PUBLISHED') and " +
            "(e.eventDate between :rangeStart and :rangeEnd) and " +
            "(:text is null) or ((lower(e.annotation) like %:text% or lower(e.description) like %:text%)) and " +
            "((:categories is null) or e.category.id in :categories) and " +
            "((:paid is null) or e.paid = :paid) and " +
            "((:onlyAvailable is null) or e.participantLimit > e.confirmedRequests) " +
            "ORDER BY e.views ")
    Collection<Event> findByParametersForPublicSortViews(String text,
                                                         List<Long> categories,
                                                         Boolean paid,
                                                         Timestamp rangeStart,
                                                         Timestamp rangeEnd,
                                                         Boolean onlyAvailable,
                                                         PageRequest pageRequest);

    @Query("FROM Event e WHERE " +
            "(e.state = 'PUBLISHED') and " +
            "(e.eventDate between :rangeStart and :rangeEnd) and " +
            "(:text is null) or ((lower(e.annotation) like %:text% or lower(e.description) like %:text%)) and " +
            "((:categories is null) or e.category.id in :categories) and " +
            "((:paid is null) or e.paid = :paid) and " +
            "((:onlyAvailable is null) or e.participantLimit > e.confirmedRequests) " +
            "ORDER BY e.views, e.rate DESC")
    Collection<Event> findByParametersForPublicSortViewsAndRateDesc(String text,
                                                                    List<Long> categories,
                                                                    Boolean paid,
                                                                    Timestamp rangeStart,
                                                                    Timestamp rangeEnd,
                                                                    Boolean onlyAvailable,
                                                                    PageRequest pageRequest);

    @Query("FROM Event e WHERE " +
            "(e.state = 'PUBLISHED') and " +
            "(e.eventDate between :rangeStart and :rangeEnd) and " +
            "(:text is null) or ((lower(e.annotation) like %:text% or lower(e.description) like %:text%)) and " +
            "((:categories is null) or e.category.id in :categories) and " +
            "((:paid is null) or e.paid = :paid) and " +
            "((:onlyAvailable is null) or e.participantLimit > e.confirmedRequests) " +
            "ORDER BY e.views, e.rate asc ")
    Collection<Event> findByParametersForPublicSortViewsAndRateAsc(String text,
                                                                   List<Long> categories,
                                                                   Boolean paid,
                                                                   Timestamp rangeStart,
                                                                   Timestamp rangeEnd,
                                                                   Boolean onlyAvailable,
                                                                   PageRequest pageRequest);


    @Query("FROM Event e WHERE " +
            "(e.state = 'PUBLISHED') and " +
            "(e.eventDate between :rangeStart and :rangeEnd) and " +
            "(:text is null) or ((lower(e.annotation) like %:text% or lower(e.description) like %:text%)) and " +
            "((:categories is null) or e.category.id in :categories) and " +
            "((:paid is null) or e.paid = :paid) and " +
            "((:onlyAvailable is null) or e.participantLimit > e.confirmedRequests) " +
            "ORDER BY e.eventDate ")
    Collection<Event> findByParametersForPublicSortEventDate(String text,
                                                             List<Long> categories,
                                                             Boolean paid,
                                                             Timestamp rangeStart,
                                                             Timestamp rangeEnd,
                                                             Boolean onlyAvailable,
                                                             PageRequest pageRequest);

    @Query("FROM Event e WHERE " +
            "(e.state = 'PUBLISHED') and " +
            "(e.eventDate between :rangeStart and :rangeEnd) and " +
            "(:text is null) or ((lower(e.annotation) like %:text% or lower(e.description) like %:text%)) and " +
            "((:categories is null) or e.category.id in :categories) and " +
            "((:paid is null) or e.paid = :paid) and " +
            "((:onlyAvailable is null) or e.participantLimit > e.confirmedRequests) " +
            "ORDER BY e.eventDate, e.rate DESC ")
    Collection<Event> findByParametersForPublicSortEventDateRateDesc(String text,
                                                                     List<Long> categories,
                                                                     Boolean paid,
                                                                     Timestamp rangeStart,
                                                                     Timestamp rangeEnd,
                                                                     Boolean onlyAvailable,
                                                                     PageRequest pageRequest);

    @Query("FROM Event e WHERE " +
            "(e.state = 'PUBLISHED') and " +
            "(e.eventDate between :rangeStart and :rangeEnd) and " +
            "(:text is null) or ((lower(e.annotation) like %:text% or lower(e.description) like %:text%)) and " +
            "((:categories is null) or e.category.id in :categories) and " +
            "((:paid is null) or e.paid = :paid) and " +
            "((:onlyAvailable is null) or e.participantLimit > e.confirmedRequests) " +
            "ORDER BY e.eventDate, e.rate ASC ")
    Collection<Event> findByParametersForPublicSortEventDateRateAsc(String text,
                                                                    List<Long> categories,
                                                                    Boolean paid,
                                                                    Timestamp rangeStart,
                                                                    Timestamp rangeEnd,
                                                                    Boolean onlyAvailable,
                                                                    PageRequest pageRequest);

    @Modifying
    @Query("UPDATE Event e SET e.likes = e.likes + 1 WHERE e.id = :eventId")
    void incrementLikesById(Long eventId);

    @Modifying
    @Query("UPDATE Event e SET e.likes = e.likes - 1 WHERE e.id = :eventId")
    void removeLikesById(Long eventId);

    @Modifying
    @Query("UPDATE Event e SET e.dislikes = e.dislikes + 1 WHERE e.id = :eventId")
    void incrementDislikesById(Long eventId);

    @Modifying
    @Query("UPDATE Event e SET e.dislikes = e.dislikes - 1 WHERE e.id = :eventId")
    void removeDislikesById(Long eventId);

    Long countByInitiatorId(Long initiatorId);

    @Query("SELECT SUM(e.rate) FROM Event e WHERE e.initiator.id = :initiatorId")
    Float getSumOfRatesByInitiatorId(@Param("initiatorId") Long initiatorId);

}
