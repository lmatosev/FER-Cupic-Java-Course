<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>


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


	<p> <font
		color=<%String[] colors = { "red", "green", "blue", "orange", "black", "yellow" };
			int chosenColor = (int) (Math.random() * 6);
			out.print(colors[chosenColor]);%>>In
		a hotel a engineer, a physicist and a mathematician are sleeping when
		a fire breaks out. The engineer wakes up, notices the fire, grabs the
		next fire extinguisher and starts spraying.... After what seems hours
		of heroic fighting the fire is gone and he goes to sleep again. But
		the fire breaks out again. The physicist wakes up, notices the fire,
		grabs the fire extinguisher .... stares at the fire for some minutes,
		does some calculations in his head - air flow, humidity, thermodynamic
		whatever - and then - with one blow from the extinguisher at the right
		point the fire is out and he goes to sleep again. But the fire breaks
		out again. The mathematician wakes up, notices the fire, sees the
		extinguisher - aaaah, the problem is solvable ... and goes to sleep
		again.</font></p>




</body>


</html>