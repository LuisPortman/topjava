package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class LoggedUser {

    private static int userId = 1;

    public static void setUserId(int userId) {
        LoggedUser.userId = userId;
    }
    
    public static int authUserId() {
        return userId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}