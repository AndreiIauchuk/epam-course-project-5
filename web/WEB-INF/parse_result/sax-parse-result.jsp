<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>SAX-парсер</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/parse-result.css" />
</head>
<body>
<h2>SAX-парсер результат</h2>

<table style="width:100%">
    <tr>
        <th rowspan="2">ID</th>
        <th rowspan="2">Name</th>
        <th rowspan="2">Origin</th>
        <th rowspan="2">Price</th>
        <th rowspan="2">Currency</th>
        <th rowspan="2">Peripheral</th>
        <th rowspan="2">Energy consumption</th>
        <th rowspan="2">Battery-charged</th>
        <th rowspan="2">Cooler presence</th>
        <th rowspan="2">Component group</th>
        <th rowspan="2">Ports</th>
        <th rowspan="2">Bluetooth-connectivity</th>
        <th rowspan="2">Critical</th>
    </tr>
    <tr></tr>
    <c:forEach var="device" items="${devices}">
        <tr>
            <td><c:out value="${device.id}"/></td>
            <td><c:out value="${device.name}"/></td>
            <td><c:out value="${device.origin}"/></td>
            <td><c:out value="${device.price.value}"/></td>
            <td><c:out value="${device.price.currency}"/></td>
            <td><c:out value="${device.deviceType.peripheral}"/></td>
            <td><c:out value="${device.deviceType.energyConsumption}"/></td>
            <td><c:out value="${device.deviceType.batteryCharged}"/></td>
            <td><c:out value="${device.deviceType.coolerPresence}"/></td>
            <td><c:out value="${device.deviceType.componentGroup}"/></td>
            <td>
                <c:forEach var="port" items="${device.deviceType.ports}">
                    <c:out value="${port}"/>
                </c:forEach>
            </td>
            <td><c:out value="${device.deviceType.bluetoothConnectivity}"/></td>
            <td><c:out value="${device.critical}"/></td>
        </tr>
    </c:forEach>
</table>
<br/>
<a href="${pageContext.request.contextPath}/main.jsp">Go back to main page</a>

</body>
</html>
