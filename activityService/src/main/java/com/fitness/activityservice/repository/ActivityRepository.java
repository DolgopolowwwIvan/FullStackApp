package com.fitness.activityservice.repository;

import com.fitness.activityservice.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {
    // Spring Data использует процессор аннотаций
    // и создает proxy-класс во время выполнения
    // Создает запрос: { "userId" : userId }
    // Выполняет: db.activities.find({ "userId" : userId })
    // Возвращает список Activity
    // Это работает если правильрно указывать имя метода
    // findBy<Поле>
    List<Activity> findByUserId(String userId);
}
