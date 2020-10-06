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

	<h1>Dostupne ankete:</h1>


	<ol>

		<c:forEach items="${polls}" var="data">

			<li><a href="glasanje?pollID=${data.getId()}">${data.getTitle()}</a> </li>


		</c:forEach>


	</ol>




</body>


</html>