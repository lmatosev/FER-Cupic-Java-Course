<%@page import="hr.fer.zemris.java.hw14.model.Poll"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
</head>

<body
	bgcolor=<%String pickedBgCol = (String) session.getAttribute("pickedBgCol");
			if (pickedBgCol == null) {
				out.print("white");
			} else {
				out.print(pickedBgCol);
			}%>>

	<h1>
		<%
			out.print(((Poll) request.getAttribute("poll")).getTitle());
		%>
	</h1>

	<p>
		<%
			out.print(((Poll) request.getAttribute("poll")).getMessage());
		%>
	</p>

	<ol>

		<c:forEach items="${options}" var="data">

			<li><a
				href="glasanje-glasaj?id=${data.getId()}&pollID=<%out.print(request.getAttribute("pollID"));%>">${data.getOptionTitle()}</a>
			</li>


		</c:forEach>


	</ol>



</body>


</html>