package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repo.InMemoryMealRepository;
import ru.javawebinar.topjava.repo.MealRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import static ru.javawebinar.topjava.util.MealsUtil.filteredByCaloriesOnly;

public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private MealRepository repository;

    public MealServlet() {
        super();
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            log.info("getAll");
            request.setAttribute("meals", filteredByCaloriesOnly(repository.getAll(), 2000));
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("delete")) {
            int id = getId(request);
            repository.delete(id);
            log.info("Delete: " + id);
            response.sendRedirect("meals");
        } else if (action.equalsIgnoreCase("create")) {
            Meal meal = new Meal(LocalDateTime.now(), "", 1000);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("meal.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("edit")) {
            int id = getId(request);
            Meal meal = repository.get(id);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("meal.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal;
        if (id.isEmpty()) {
            meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));
        } else {
            meal = new Meal(Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));
        }
        repository.save(meal);
        response.sendRedirect("meals");
    }

    private int getId(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id"));
    }

}
