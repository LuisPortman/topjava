<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<table border="1">
    <thead>
        <tr>
            <th>Дата</th>
            <th>Описание</th>
            <th>Калории</th>
            <th colspan=2>Action</th>
        </tr>
        <c:forEach items="${meals}" var="item">
            <jsp:useBean id="item" scope="page" type="ru.javawebinar.topjava.model.MealTo" />
            <c:if test="${!item.excess}">
                <tr style="color:Green;">
                    <td>${item.formattedDateTime}</td>
                    <td>${item.description}</td>
                    <td>${item.calories}</td>
                    <td><a href="meals?action=edit&id=<c:out value="${item.id}"/>">Update</a></td>
                    <td><a href="meals?action=delete&id=<c:out value="${item.id}"/>">Delete</a></td>
                </tr>
            </c:if>
            <c:if test="${item.excess}">
                <tr style="color:Red;">
                    <td>${item.formattedDateTime}</td>
                    <td>${item.description}</td>
                    <td>${item.calories}</td>
                    <td><a href="meals?action=edit&id=<c:out value="${item.id}"/>">Update</a></td>
                    <td><a href="meals?action=delete&id=<c:out value="${item.id}"/>">Delete</a></td>
                </tr>
            </c:if>

        </c:forEach>
    </thead>
</table>
<p><a href="meals?action=create">Add Meal</a></p>
</body>
</html>
