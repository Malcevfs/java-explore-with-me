package ru.practicum.request.model;

import lombok.*;
import ru.practicum.util.Status;
import ru.practicum.user.model.User;
import ru.practicum.event.model.Event;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "requests")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Timestamp created;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @ToString.Exclude
    private Event event;
    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    @ToString.Exclude
    private User requester;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}
