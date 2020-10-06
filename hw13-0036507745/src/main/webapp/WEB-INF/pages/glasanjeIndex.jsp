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

	<h1>Glasanje za omiljeni bend:</h1>

	<p>Koji Vam je bend najdraži od navedenih bendova? Kliknite na link
		kako biste glasali!</p>

	<ol>

		<c:forEach items="${bands}" var="data">

			<li><a href="glasanje-glasaj?id=${data.getId()}">${data.getName()}</a> </li>


		</c:forEach>


	</ol>




</body>


</html>