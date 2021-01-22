<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Car Creation</title>
</head>
<body>
<h1>Please provide information about your Car</h1>
<form method="post" action="${pageContext.request.contextPath}/car/create">
    Please Enter Car Model: <input type="text" name="model" required>
    Please Enter Manufacturer ID: <input type="number" name="manufacturer" required>
    <button type="submit">Create</button>
</form>
</body>
</html>
