package ru.practicum.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hits")
@Setter
@Getter
@NamedNativeQueries({
        @NamedNativeQuery(name = "GetNotUniqueIpStat", resultSetMapping = "HitToDto",
                query = "select h.app, h.uri, count(h.ip) as hits " +
                        "from hits as h " +
                        "where (h.timeStamp between :start and :end) " +
                        "and (h.uri in :uris) " +
                        "group by h.uri, h.app order by hits desc "
        ),
        @NamedNativeQuery(name = "GetUniqueIpStat", resultSetMapping = "HitToDto",
                query = "select h.app, h.uri, count(distinct h.ip) as hits " +
                        "from hits as h " +
                        "where (h.timeStamp between :start and :end) " +
                        "and (h.uri in :uris) " +
                        "group by h.uri, h.app order by hits desc "
        ),
        @NamedNativeQuery(name = "GetNotUniqueIpStatNoUri", resultSetMapping = "HitToDto",
                query = "select h.app, h.uri, count(h.ip) as hits " +
                        "from hits as h " +
                        "where (h.timeStamp between :start and :end) " +
                        "group by h.uri, h.app order by hits desc "
        ),
        @NamedNativeQuery(name = "GetUniqueIpStatNoUri", resultSetMapping = "HitToDto",
                query = "select h.app, h.uri, count(distinct h.ip) as hits " +
                        "from hits as h " +
                        "where (h.timeStamp between :start and :end) " +
                        "group by h.uri, h.app order by hits desc "
        )
})
@SqlResultSetMapping(name = "HitToDto",
        classes = {
                @ConstructorResult(
                        targetClass = ru.practicum.Stat.class,
                        columns = {
                                @ColumnResult(name = "app", type = String.class),
                                @ColumnResult(name = "uri", type = String.class),
                                @ColumnResult(name = "hits", type = Integer.class)
                        }
                )}
)
public class Hit {
    @Id
    @Column(name = "hit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "app")
    private String app;
    @Column(name = "uri")
    private String uri;
    @Column(name = "ip")
    private String ip;
    @Column(name = "timestamp")
    private LocalDateTime timeStamp;
}
