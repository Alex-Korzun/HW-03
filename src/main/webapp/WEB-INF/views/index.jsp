<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mate Academy</title>
</head>
<body>
<h1>Welcome!</h1>
<a href="${pageContext.request.contextPath}/drivers/create">Create New Driver</a><br>
<a href="${pageContext.request.contextPath}/drivers/">Display All Drivers</a><br>
<a href="${pageContext.request.contextPath}/manufacturers/create">Add New Manufacturer</a><br>
<a href="${pageContext.request.contextPath}/manufacturers/">Display All Manufacturers</a><br>
<a href="${pageContext.request.contextPath}/cars/create">Add New Car</a><br>
<a href="${pageContext.request.contextPath}/cars/">Display All Cars</a><br>
<a href="${pageContext.request.contextPath}/cars/drivers/add">Add Driver To Car</a><br>
</body>
</html>
