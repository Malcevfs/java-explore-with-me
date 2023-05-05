package ru.practicum.event.dto;

import lombok.Data;
import ru.practicum.util.Status;

import java.util.Set;

@Data
public class EventRequestStatusUpdateRequest {

   private Set<Long> requestIds;
   private Status status;
}
