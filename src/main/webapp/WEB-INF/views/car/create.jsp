<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Car Creation</title>
</head>
<body>
<h1>Please provide information about your Car</h1>
<form method="post" action="${pageContext.request.contextPath}/cars/create">
    Please enter car model: <input type="text" name="model">
    Please enter manufacturer ID: <input type="number" name="manufacturer">
    <button type="submit">Create</button>
</form>
</body>
</html>