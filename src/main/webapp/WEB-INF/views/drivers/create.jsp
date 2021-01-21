<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Driver Creation</title>
</head>
<body>
<h1>Please provide information about your Driver</h1>
<form method="post" action="${pageContext.request.contextPath}/drivers/create">
    Please enter name of the driver: <input type="text" name="name">
    Please enter driving license number of the driver: <input type="number" name="licenseNumber">
    <button type="submit">Create</button>
</form>
</body>
</html>
