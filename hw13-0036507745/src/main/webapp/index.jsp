<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>

<html>
<head>
<title>Home</title>
</head>
<body
	bgcolor=<%String pickedBgCol = (String) session.getAttribute("pickedBgCol");
			if (pickedBgCol == null) {
				out.print("white");
			} else {
				out.print(pickedBgCol);
			}%>>


	<a href="/webapp2/colors.jsp">Background color chooser</a>

	<p></p>
	<form action="trigonometric" method="GET">
		Početni kut:<br> <input type="number" name="a" min="0" max="360"
			step="1" value="0"><br> Završni kut:<br> <input
			type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"><input type="reset"
			value="Reset">
	</form>

	<p></p>

	<p>
		<a href="powers?a=1&b=100&n=3">Powers</a>
	</p>

	<p></p>

	<p>
		<a href="appinfo.jsp">Application info</a>
	</p>

	<p>
		<a href="stories/funny.jsp">Funny story</a>
	</p>


</body>


</html>
