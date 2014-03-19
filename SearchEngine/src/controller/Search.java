package controller;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Search extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		String keyword = request.getParameter("keyword").replace(" ", "+");
		JSONObject o = DBPediaRequest.getResult(keyword);

		JSONArray docs = LuceneSearch.search(keyword);

		try {
			request.setAttribute("resultList", o);
			request.setAttribute("localReports", docs);
			getServletConfig().getServletContext()
					.getRequestDispatcher("/searchResult.jsp")
					.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
