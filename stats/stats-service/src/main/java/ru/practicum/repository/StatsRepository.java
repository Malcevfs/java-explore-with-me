package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.Stat;
import ru.practicum.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<Hit, Long> {

    @Query(name = "GetNotUniqueIpStat", nativeQuery = true)
    List<Stat> getStatForNotUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(name = "GetUniqueIpStat", nativeQuery = true)
    List<Stat> getStatForUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query(name = "GetNotUniqueIpStatNoUri", nativeQuery = true)
    List<Stat> getStatForNotUniqueIpWhithoutUri(LocalDateTime start, LocalDateTime end);

    @Query(name = "GetNotUniqueIpStatNoUri", nativeQuery = true)
    List<Stat> getStatForUniqueIpWhithoutUri(LocalDateTime start, LocalDateTime end);
}
