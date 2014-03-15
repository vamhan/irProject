package controller;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class DBPediaRequest {

	static HttpClient client;
	
	public static JSONObject getResult(String keyword) {
		try {
			if (client == null) {
				client = new DefaultHttpClient();
			}
			String getURL = "http://lookup.dbpedia.org/api/search/KeywordSearch?QueryClass=place&QueryString="
					+ keyword;
			HttpGet get = new HttpGet(getURL);
			get.setHeader("Accept", "application/json");
			HttpResponse responseGet = client.execute(get);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					responseGet.getEntity().getContent()));
	
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
	
			JSONObject o = new JSONObject(result.toString());
			
			rd.close();
			get.abort();
			
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
