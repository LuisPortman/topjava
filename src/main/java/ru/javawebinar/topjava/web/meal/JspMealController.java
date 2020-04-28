package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.AbstractMealController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

/*
1.3 Перенести функциональность MealServlet в JspMealController контроллер
(по аналогии с RootController). MealRestController у нас останется, с ним будем работать позже.
1.3.1 разнести запросы на update/delete/.. по разным методам (попробуйте вообще без action=).
Можно по аналогии с RootController#setUser принимать HttpServletRequest request
(аннотации на параметры и адаптеры для LocalDate/Time мы введем позже).
1.3.2 в одном контроллере нельзя использовать другой.
Чтобы не дублировать код, можно сделать наследование контроллеров от абстрактного класса.
1.3.3 добавить локализацию и jsp:include в mealForm.jsp / meals.jsp
 */
@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        super.delete(getId(request));
        return "redirect:/meals";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("meal", super.get(getId(request)));
        return "mealForm";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now(), "description", 0));
        return "mealForm";
    }

    @GetMapping("/filter")
    public String getBetween(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @PostMapping
    public String createOrUpdate(HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            super.create(meal);
        } else {
            super.update(meal, getId(request));
        }
        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = request.getParameter("id");
        return Integer.valueOf(paramId);
    }

}
