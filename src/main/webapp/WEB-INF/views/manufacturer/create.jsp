<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manufacturer Creation</title>
</head>
<body>
<h1>Please provide information about your Manufacturer</h1>
<form method="post" action="${pageContext.request.contextPath}/manufacturers/create">
    Please enter name of the manufacturer: <input type="text" name="name">
    Please enter country of the manufacturer: <input type="text" name="country">
    <button type="submit">Create</button>
</form>
</body>
</html>
