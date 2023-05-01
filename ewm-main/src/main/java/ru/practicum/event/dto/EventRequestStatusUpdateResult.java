package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.request.model.Request;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequestStatusUpdateResult {

    List<Request> confirmedRequests = new ArrayList<Request>();
    List<Request> rejectedRequests = new ArrayList<Request>();
}
