package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.user.model.User;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.repository.UserRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public Collection<User> getUsers(List<Long> ids, int from, int size) {
        return userRepository.findByIdIn(ids, PageRequest.of(from, size));
    }

    public User getById(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id=" + userId));
    }

    @Transactional
    public void deleteById(long userId) {
        getById(userId);
        userRepository.deleteById(userId);
    }
}
