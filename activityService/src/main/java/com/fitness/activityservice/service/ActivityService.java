package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityResponse trackActivity(ActivityRequest request) {
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getActivityType())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);
        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity){
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setType(activity.getType());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMetrics((activity.getAdditionalMetrics()));
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response;
    }

    // Метод, который взаимодействует с базой данных и возвращает список активностей
    public List<ActivityResponse> getUserActivities(String userId) {
        // Так как в MongoDB к id обратиться проблематично,
        // делаем фильт по полю userId
        List<Activity> activities = activityRepository.findByUserId(userId);
        // Нужно взять список, создать из него поток и замапить
        // каждый элемент списка в ActivityResponse
        return activities.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ActivityResponse getActivityById(String activityId) {
        return activityRepository.findById(activityId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Activity with id: " + activityId + " not found!"));
    }
}
