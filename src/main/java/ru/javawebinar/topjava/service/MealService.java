package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public Meal update(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    // false if not found
    public boolean delete(int id, int userId) {
        return repository.delete(id, userId);
    }

    // null if not found
    public Meal get(int id, int userId) {
        return repository.get(id, userId);
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return repository.isBetween(startDate, endDate, userId);
    }

}