package controller;
import java.io.IOException;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Search extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {


			JSONObject o = DBPediaRequest.getResult(request.getParameter("keyword"));

			request.setAttribute("resultList", o);
			try {
				getServletConfig().getServletContext()
						.getRequestDispatcher("/searchResult.jsp")
						.forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//DBConnector connector = new DBConnector();
			//connector.connect();
			
		
	}
}
