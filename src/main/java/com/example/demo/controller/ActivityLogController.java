package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.UserActivityLog;
import com.example.demo.repository.UserActivityLogRepository;

@RestController
@RequestMapping("/api/activity-logs")
public class ActivityLogController {

    @Autowired
    private UserActivityLogRepository repository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserActivityLog> getAllLogs() {
        return repository.findAll();
    }
}
