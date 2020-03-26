package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.web.LoggedUser.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.LoggedUser.authUserId;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        log.info("create {}", meal.getId());
        return service.create(meal, authUserId());
    }

    public Meal update(Meal meal) {
        log.info("update {}", meal.getId());
        return service.update(meal, authUserId());
    }

    // false if not found
    public boolean delete(int id) {
        log.info("delete {}", id);
        return service.delete(id, authUserId());
    }

    // null if not found
    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, authUserId());
    }

    public Collection<MealTo> getAll() {
        log.info("get all");
        return MealsUtil.getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public List<MealTo> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        List<Meal> meals = service.getBetween(LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime), authUserId());
        return MealsUtil.getTos(meals, authUserCaloriesPerDay());
    }

}