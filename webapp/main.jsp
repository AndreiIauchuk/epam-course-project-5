<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Парсеры</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main-page.css" />
</head>
<body>
<div class="buttons-container">
    <a href="${pageContext.request.contextPath}/main?parser=dom">
        <button>DOM-парсер</button>
    </a>
    <a href="${pageContext.request.contextPath}/main?parser=sax">
        <button>SAX-парсер</button>
    </a>
    <a href="${pageContext.request.contextPath}/main?parser=stax">
        <button>StAX-парсер</button>
    </a>
</div>
</body>
</html>
