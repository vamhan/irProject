package controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class Autocomplete extends HttpServlet {

	private static final long serialVersionUID = 1L;
	HttpClient client;
	HttpGet get;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (client == null) {
				client = new DefaultHttpClient();
			}
			if (get != null) {
				//get.abort();
			}
			String keyword = request.getParameter("keyword").replace(" ", "+");
			String getURL = "http://lookup.dbpedia.org/api/search.asmx/PrefixSearch?QueryClass=&MaxHits=5&QueryString=" + keyword;
			get = new HttpGet(getURL);
			get.setHeader("Accept", "application/json");
			HttpResponse responseGet = client.execute(get);
			BufferedReader rd = new BufferedReader(new InputStreamReader(responseGet.getEntity().getContent()));
	
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
	
			JSONObject o = new JSONObject(result.toString());
			System.out.println(o);
			
			rd.close();
			get.abort();
			
		    String json = o.toString();

		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(json);

		} catch (Exception e) {
			e.printStackTrace();
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    try {
				response.getWriter().write("");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}
}
