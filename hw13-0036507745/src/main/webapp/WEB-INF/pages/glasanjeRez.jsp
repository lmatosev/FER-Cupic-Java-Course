<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
</head>

<body
	bgcolor=<%String pickedBgCol = (String) session.getAttribute("pickedBgCol");
			if (pickedBgCol == null) {
				out.print("white");
			} else {
				out.print(pickedBgCol);
			}%>>


	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" class="rez">

		<thead>
			<tr>
				<th>Bend</th>
				<th>Broj glasova</th>
			</tr>
		</thead>

		<tbody>

			<c:forEach items="${bandResults}" var="data">

				<tr>
					<td>${data.getName()}</td>
					<td>${data.getVotes()}</td>
				</tr>

			</c:forEach>

		</tbody>

	</table>


	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />


	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>
	</p>


	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>

	<ul>

		<c:forEach items="${bandWinners}" var="data">

			<li><a href="${data.getLink()}" target="_blank">${data.getName()}</a></li>
			
		</c:forEach>

	</ul>

</body>


</html>