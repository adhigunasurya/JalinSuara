package com.jalinsuara.android.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.news.model.News;
import com.jalinsuara.android.projects.model.SubProject;
import com.jalinsuara.android.search.SearchResult;

public class NetworkUtils {

	/** The tag used to log to adb console. */
	private static final String TAG = NetworkUtils.class.getSimpleName();

	/** POST parameter name for the user's account name */
	public static final String PARAM_USERNAME = "username";
	/** POST parameter name for the user's password */
	public static final String PARAM_PASSWORD = "password";

	/** Timeout (in ms) we specify for each http request */
	public static final int HTTP_REQUEST_TIMEOUT_MS = 30 * 1000;

	/** Base URL */
	public static String BASE_URL = "http://jalinsuara.web.id/en";

	private static DefaultHttpClient mHttpClient;

	/**
	 * Get local ip address
	 * 
	 * @return
	 */
	public static String getLocalIpAddress(Context context) {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);

		// Get WiFi status
		WifiInfo info = wifi.getConnectionInfo();
		return Formatter.formatIpAddress(info.getIpAddress());
	}

	public static boolean isNetworkDown(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		// getNetworkInfo(MOBILE) returns null in xoom, so check null first
		NetworkInfo mobile_info = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi_info = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		return !((mobile_info != null && mobile_info.getState() == NetworkInfo.State.CONNECTED) || (wifi_info != null && wifi_info
				.getState() == NetworkInfo.State.CONNECTED));
	}

	/**
	 * Configures the httpClient to connect to the URL provided.
	 */
	public static HttpClient getHttpClient() {
		if (mHttpClient == null) {
			mHttpClient = new DefaultHttpClient();
			final HttpParams params = mHttpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(params,
					HTTP_REQUEST_TIMEOUT_MS);
			HttpConnectionParams.setSoTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
			ConnManagerParams.setTimeout(params, HTTP_REQUEST_TIMEOUT_MS);
		}
		return mHttpClient;
	}

	/**
	 * Connects to the SampleSync test server, authenticates the provided
	 * username and password.
	 * 
	 * @param username
	 *            The server account username
	 * @param password
	 *            The server account password
	 * @return String The authentication token returned by the server (or null)
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static ArrayList<News> getPosts() {
		final HttpResponse resp;
		String uri = BASE_URL + "/posts.json";

		Log.i(TAG, "Request: " + uri);
		final HttpGet request = new HttpGet(uri);
		try {
			resp = getHttpClient().execute(request);

			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream istream = (resp.getEntity() != null) ? resp
						.getEntity().getContent() : null;
				if (istream != null) {
					BufferedReader ireader = new BufferedReader(
							new InputStreamReader(istream));
					String line = ireader.readLine();
					StringBuilder sb = new StringBuilder();
					while (line != null) {
						sb.append(line);
						line = ireader.readLine();
					}
					Log.i(TAG, "Response: " + sb.toString());
					ireader.close();
					String response = sb.toString();
					if (response.length() > 0) {
						try {
							Gson gson = JalinSuaraSingleton.getInstance()
									.getGson();
							Type collectionType = new TypeToken<ArrayList<News>>() {
							}.getType();
							ArrayList<News> retval = gson.fromJson(response,
									collectionType);
							return retval;

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						return null;
					}
				}

			} else {
				Log.e(TAG, "Error: " + resp.getStatusLine());
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Search
	 * 
	 * @param query
	 * @return
	 */
	public static ArrayList<SearchResult> getSearch(String query) {
		final HttpResponse resp;
		String uri = BASE_URL + "/home/search.json";

		Log.i(TAG, "Request: " + uri);
		final HttpPost request = new HttpPost(uri);

		try {
			// add query
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("query", query));
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			resp = getHttpClient().execute(request);

			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream istream = (resp.getEntity() != null) ? resp
						.getEntity().getContent() : null;
				if (istream != null) {
					BufferedReader ireader = new BufferedReader(
							new InputStreamReader(istream));
					String line = ireader.readLine();
					StringBuilder sb = new StringBuilder();
					while (line != null) {
						sb.append(line);
						line = ireader.readLine();
					}
					Log.i(TAG, "Response: " + sb.toString());
					ireader.close();
					String response = sb.toString();
					if (response.length() > 0) {
						try {
							ArrayList<SearchResult> retval = new ArrayList<SearchResult>();
							JsonParser parser = new JsonParser();
							JsonElement resElmt = parser.parse(response);
							if (resElmt.isJsonArray()) {
								JsonArray resArr = resElmt.getAsJsonArray();
								for (int i = 0; i < resArr.size(); i++) {
									JsonElement elmt = resArr.get(i);
									if (elmt.isJsonObject()) {
										JsonObject obj = elmt.getAsJsonObject();
										String objString = elmt.toString();
										JsonElement blmAmountElmt = obj
												.get("blm_amount");
										SearchResult retvalElmt = new SearchResult();
										if (blmAmountElmt != null) {
											retvalElmt.setNews(false);

											SubProject project = JalinSuaraSingleton
													.getInstance()
													.getGson()
													.fromJson(objString,
															SubProject.class);
											retvalElmt.setProjects(project);

										} else {
											retvalElmt.setNews(true);
											News news = JalinSuaraSingleton
													.getInstance()
													.getGson()
													.fromJson(objString,
															News.class);
											retvalElmt.setNews(news);
										}
										retval.add(retvalElmt);
									}
								}
							}

							return retval;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						return null;
					}
				}

			} else {
				Log.e(TAG, "Error: " + resp.getStatusLine());
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public static ArrayList<SubProject> getSubProject() {
		final HttpResponse resp;
		String uri = BASE_URL + "/activities.json";

		Log.i(TAG, "Request: " + uri);
		final HttpGet request = new HttpGet(uri);
		try {
			resp = getHttpClient().execute(request);

			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream istream = (resp.getEntity() != null) ? resp
						.getEntity().getContent() : null;
				if (istream != null) {
					BufferedReader ireader = new BufferedReader(
							new InputStreamReader(istream));
					String line = ireader.readLine();
					StringBuilder sb = new StringBuilder();
					while (line != null) {
						sb.append(line);
						line = ireader.readLine();
					}
					Log.i(TAG, "Response: " + sb.toString());
					ireader.close();
					String response = sb.toString();
					if (response.length() > 0) {
						try {
							Gson gson = JalinSuaraSingleton.getInstance()
									.getGson();
							Type collectionType = new TypeToken<ArrayList<SubProject>>() {
							}.getType();
							ArrayList<SubProject> retval = gson.fromJson(
									response, collectionType);
							return retval;

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						return null;
					}
				}

			} else {
				Log.e(TAG, "Error: " + resp.getStatusLine());
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;

	}

	public static class AuthResponse {
		public String token;
	}
}
