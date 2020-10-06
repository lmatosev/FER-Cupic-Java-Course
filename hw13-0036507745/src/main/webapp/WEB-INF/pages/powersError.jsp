<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  session="true" %>


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


	<p>Invalid arguments provided for selected action.</p>
	<p>Parameters "a" and "b" should be between -100 and 100 while parameter "n" should be between 1 and 5.</p>

	


</body>


</html>