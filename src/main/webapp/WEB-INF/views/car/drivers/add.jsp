<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adding Drivers to Cars</title>
</head>
<body>
<h1>Please provide information about your Driver and Car</h1>
<form method="post" action="${pageContext.request.contextPath}/cars/drivers/add">
    Please enter car id: <input type="number" name="carId">
    Please enter driver id: <input type="number" name="driverId">
    <button type="submit">Add</button>
</form>
</body>
</html>
