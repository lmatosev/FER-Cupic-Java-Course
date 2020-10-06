package hr.fer.zemris.java.hw16.images;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * Servlet used to draw the image to request output stream.
 * 
 * @author Lovro Matošević
 *
 */

@WebServlet("/draw")
public class DrawImageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4714320095673697618L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		String strPath = req.getParameter("path");
		
		Path path = Paths.get(strPath);
				
		FileInputStream in = new FileInputStream(path.toFile());

		OutputStream out = resp.getOutputStream();

		byte[] b = new byte[2048];

		int br = 0;
		while ((br = in.read(b)) >= 0) {
			out.write(b, 0, br);
		}
		in.close();
		out.close();
	}
}
