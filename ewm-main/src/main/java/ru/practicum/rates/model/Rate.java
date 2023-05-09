package ru.practicum.rates.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rate")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rate {
    @Id
    @Column(name = "event_id")
    private long eventId;
    @Column(name = "user_id")
    private long userId;
    private boolean likes;
    private boolean dislikes;
}
