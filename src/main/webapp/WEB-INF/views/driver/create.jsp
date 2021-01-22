<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Driver Creation</title>
</head>
<body>
<h1>Please provide information about your Driver</h1>
<h4 style="color:red">${errorMsg}</h4>
<form method="post" action="${pageContext.request.contextPath}/driver/create">
    Please enter Name of the Driver: <input type="text" name="name" required><br>
    Please enter driving License Number of the Driver: <input type="number" name="licenseNumber" required><br>
    Please enter your Login: <input type="text" name="login" required><br>
    Please enter your Password: <input type="password" name="password" required><br>
    Please confirm your Password: <input type="password" name="submitPassword" required><br>
    <button type="submit">Create</button>
</form>
</body>
</html>
