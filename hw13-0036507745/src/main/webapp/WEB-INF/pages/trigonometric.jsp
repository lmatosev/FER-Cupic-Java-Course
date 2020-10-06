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


	<table>
		<tr>

			<th>Angle</th>
			<th>Sine</th>
			<th>Cosine</th>

		</tr>

		<c:forEach items="${angleData}" var="data" >

			<tr>
				<td><c:out value="${data.getAngle()}"></c:out></td>
				<td><c:out value="${data.getSin()}"></c:out></td>
				<td><c:out value="${data.getCos()}"></c:out></td>

			</tr>


		</c:forEach>

	</table>



</body>


</html>