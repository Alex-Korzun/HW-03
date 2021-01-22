<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Manufacturer Creation</title>
</head>
<body>
<h1>Please provide information about your Manufacturer</h1>
<form method="post" action="${pageContext.request.contextPath}/manufacturers/create">
    Please enter Name of the Manufacturer: <input type="text" name="name" required>
    Please enter Country of the Manufacturer: <input type="text" name="country" required>
    <button type="submit">Create</button>
</form>
</body>
</html>
