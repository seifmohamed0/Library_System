package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.model.UserActivityLog;
import com.example.demo.repository.UserActivityLogRepository;

@Service
public class UserActivityLogService {

    private final UserActivityLogRepository logRepository;

    public UserActivityLogService(UserActivityLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public void log(User user, String action, String details) {
        UserActivityLog log = new UserActivityLog();
        log.setUser(user);
        log.setAction(action);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        logRepository.save(log);
    }
}

