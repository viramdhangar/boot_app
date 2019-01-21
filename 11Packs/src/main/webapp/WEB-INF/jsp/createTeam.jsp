<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="false"%>
<html>
<head>
<title>Create Team</title>
</head>
<body>
	<jsp:include page="menu.jsp" />
	      
	<h3 style="color: red;">Select Your Playing 11</h3>
	<ul>
		<c:forEach var="sq" items="${squad}">
			<li>${sq.id}:: Player: ${sq.playerName}:: Type: ${sq.playerType}:: Credit: ${sq.credit}</li>
		</c:forEach>
	</ul>
</body>
</html>