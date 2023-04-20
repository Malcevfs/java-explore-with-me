package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.Stat;
import ru.practicum.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Hit, Long> {

    @Query("select new ru.practicum.Stat(h.app, h.uri, count(h.ip) as hits) " +
            "from Hit as h " +
            "where (h.timeStamp between :start and :end) " +
            "and (h.uri in :uris) " +
            "group by h.uri, h.app order by hits desc ")
    List<Stat> getStatForNotUniqueIp(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris") List<String> uris);

    @Query("select new ru.practicum.Stat(h.app, h.uri, count(distinct h.ip) as hits) " +
            "from Hit as h " +
            "where (h.timeStamp between :start and :end) " +
            "and (h.uri in :uris) " +
            "group by h.uri, h.app order by hits desc ")
    List<Stat> getStatForUniqueIp(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris") List<String> uris);

    @Query("select new ru.practicum.Stat(h.app, h.uri, count(h.ip) as hits) " +
            "from Hit as h " +
            "where (h.timeStamp between :start and :end) " +
            "group by h.uri, h.app order by hits desc ")
    List<Stat> getStatForNotUniqueIpWhithoutUri(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("select new ru.practicum.Stat(h.app, h.uri, count(distinct h.ip) as hits) " +
            "from Hit as h " +
            "where (h.timeStamp between :start and :end) " +
            "group by h.uri, h.app order by hits desc ")
    List<Stat> getStatForUniqueIpWhithoutUri(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
