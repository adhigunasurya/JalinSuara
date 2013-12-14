package com.jalinsuara.android.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.webkit.MimeTypeMap;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jalinsuara.android.JalinSuaraSingleton;
import com.jalinsuara.android.R;
import com.jalinsuara.android.news.model.Comment;
import com.jalinsuara.android.news.model.News;
import com.jalinsuara.android.news.model.ShareNewsParams;
import com.jalinsuara.android.projects.model.District;
import com.jalinsuara.android.projects.model.Province;
import com.jalinsuara.android.projects.model.SubDistrict;
import com.jalinsuara.android.projects.model.SubProject;
import com.jalinsuara.android.search.SearchResult;

/**
 * Helper for accessing web service API
 * 
 * @author tonoman3g
 * @author gabriellewp
 * 
 */
public class NetworkUtils {

	/**
	 * Logger
	 */
	private static Logger log = LoggerFactory
			.getLogger(JalinSuaraSingleton.class.getSimpleName());

	/**
	 * Gson for deserialize json response
	 */
	private static Gson mGson;

	/** POST parameter name for the user's account name */
	public static final String PARAM_USERNAME = "username";

	/** POST parameter name for the user's password */
	public static final String PARAM_PASSWORD = "password";

	/** Timeout (in ms) we specify for each http request */
	public static final int HTTP_REQUEST_TIMEOUT_MS = 2 * 60 * 1000;

	/**
	 * without this params, it still returns about 30 item in one page
	 */
	public static final String PARAM_PER_PAGE = "per_page";
	/**
	 * Default value for {@link NetworkUtils#PARAM_PER_PAGE}
	 */
	public static final int DEFAULT_PER_PAGE = 30;

	/** Base URL */
	public static String BASE_URL = "http://jalinsuara.web.id/api/v1";

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

	/**
	 * Check whether network is down
	 * 
	 * @param context
	 * @return
	 */
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
	 * Get post from server
	 * 
	 * @param page
	 * @return null if error
	 */
	public static ArrayList<News> getPosts(int page) {
		final HttpResponse resp;
		String uri = BASE_URL + "/posts.json";

		if (page <= 0) {
			uri = BASE_URL + "/posts?page=1&" + PARAM_PER_PAGE + "="
					+ DEFAULT_PER_PAGE;
		} else {
			uri = BASE_URL + "/posts?page=" + page + "&" + PARAM_PER_PAGE + "="
					+ DEFAULT_PER_PAGE;
		}

		log.info("Request: " + uri);
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

					if (sb.length() <= 30) {
						log.info("Response: " + sb.toString());
					} else {
						log.info("Response: " + sb.toString().substring(0, 29));
					}
					ireader.close();

					String response = sb.toString();

					if (response.length() > 0) {
						try {
							Type collectionType = new TypeToken<ArrayList<News>>() {
							}.getType();
							ArrayList<News> retval = getGson().fromJson(
									response, collectionType);
							return retval;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						return null;
					}
				}
			} else {
				log.error("Error: " + resp.getStatusLine());
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	
	/**
	 * Get post from server
	 * 
	 * @param page
	 * @return null if error
	 */
	public static News getPosts(long id) {
		final HttpResponse resp;
		String uri = BASE_URL + "/posts/"+id;		

		log.info("Request: " + uri);
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

					if (sb.length() <= 30) {
						log.info("Response: " + sb.toString());
					} else {
						log.info("Response: " + sb.toString().substring(0, 29));
					}
					ireader.close();

					String response = sb.toString();

					if (response.length() > 0) {
						try {							
							News retval = getGson().fromJson(
									response, News.class);
							return retval;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						return null;
					}
				}
			} else {
				log.error("Error: " + resp.getStatusLine());
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Get related news / post from a certain subproject / activity
	 * 
	 * @param subprojectId
	 * @param page
	 * @return
	 */
	public static ArrayList<News> getPosts(long subprojectId, int page) {
		final HttpResponse resp;
		String uri = null;
		if (page <= 0) {
			uri = BASE_URL + "/activities/" + subprojectId + "/posts";
		} else {
			uri = BASE_URL + "/activities/" + subprojectId + "/posts?page="
					+ page + "&" + PARAM_PER_PAGE + "=" + DEFAULT_PER_PAGE;
		}

		log.info("Request: " + uri);
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

					log.info("Response retrieved");
					ireader.close();

					String response = sb.toString();

					if (response.length() > 0) {
						try {
							Gson gson = getGson();
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
				log.error("Error: " + resp.getStatusLine());
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Search for news or sub projects by a certain query
	 * 
	 * @param query
	 * @param page
	 * @return null if error
	 */
	public static ArrayList<SearchResult> getSearch(String query, int page) {
		final HttpResponse resp;

		// String uri = BASE_URL + "/home/search.json";

		String uri = "http://jalinsuara.web.id/en" + "/home/search.json";

		if (page <= 0) {

		} else {

		}

		log.info("Request: " + uri);
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

					log.info("Response received");
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

											SubProject project = getGson()
													.fromJson(objString,
															SubProject.class);
											retvalElmt.setProjects(project);

										} else {
											retvalElmt.setNews(true);
											News news = getGson().fromJson(
													objString, News.class);
											retvalElmt.setNews(news);
										}
										retval.add(retvalElmt);										
									}
								}
							}
							log.info("Result : "+retval.size());
							return retval;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						return null;
					}
				}

			} else {
				log.error("Error: " + resp.getStatusLine());
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Get sub project from server
	 * 
	 * @param page
	 *            if page == -1 no parameter
	 * @return null if error
	 */
	public static ArrayList<SubProject> getSubProjects(int page) {
		final HttpResponse resp;
		String uri = null;
		if (page <= 0) {
			uri = BASE_URL + "/activities?page=1&" + PARAM_PER_PAGE + "="
					+ DEFAULT_PER_PAGE;
		} else {
			uri = BASE_URL + "/activities?page=" + page + "&" + PARAM_PER_PAGE
					+ "=" + DEFAULT_PER_PAGE;
		}

		log.info("Request: " + uri);
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

					log.info("Response retrieved");
					ireader.close();

					String response = sb.toString();

					if (response.length() > 0) {
						try {
							Gson gson = getGson();
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
				log.error("Error: " + resp.getStatusLine());
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Get sub project from server
	 * 
	 * 
	 * @param subdistrictId
	 * @param page
	 *            if page == -1 no parameter
	 * @return null if error
	 */
	public static ArrayList<SubProject> getSubProjects(long subdistrictId,
			int page) {
		final HttpResponse resp;
		String uri = null;
		if (page <= 0) {
			uri = BASE_URL + "/subdistricts/" + subdistrictId + "/activities";
		} else {
			uri = BASE_URL + "/subdistricts/" + subdistrictId
					+ "/activities?page=" + page + "&" + PARAM_PER_PAGE + "="
					+ DEFAULT_PER_PAGE;
		}

		log.info("Request: " + uri);
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

					log.info("Response retrieved");
					ireader.close();

					String response = sb.toString();

					if (response.length() > 0) {
						try {
							Gson gson = getGson();
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
				log.error("Error: " + resp.getStatusLine());
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Retrieve all provinces
	 * 
	 * @return null if error
	 */
	public static ArrayList<Province> getProvinces() {
		final HttpResponse resp;
		String uri = null;

		// assumming this will return all provinces in Indonesia
		uri = BASE_URL + "/provinces?page=1&per_page=100";

		log.info("Request: " + uri);
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
					log.info("Response retrieved");
					ireader.close();

					String response = sb.toString();

					if (response.length() > 0) {
						try {
							Gson gson = getGson();
							Type collectionType = new TypeToken<ArrayList<Province>>() {
							}.getType();
							ArrayList<Province> retval = gson.fromJson(
									response, collectionType);
							return retval;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						return null;
					}
				}
			} else {
				log.error("Error: " + resp.getStatusLine());
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Get all districts
	 * 
	 * @param page
	 * @return null if error
	 */
	public static ArrayList<District> getDistricts(long page) {
		final HttpResponse resp;
		String uri = null;

		if (page <= 0) {
			uri = BASE_URL + "/districts?page=1&" + PARAM_PER_PAGE + "="
					+ DEFAULT_PER_PAGE;
		} else {
			uri = BASE_URL + "/districts?page=" + page + "&" + PARAM_PER_PAGE
					+ "=" + DEFAULT_PER_PAGE;
		}

		log.info("Request: " + uri);
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

					log.info("Response retrieved");
					ireader.close();

					String response = sb.toString();

					if (response.length() > 0) {
						try {
							Gson gson = getGson();
							Type collectionType = new TypeToken<ArrayList<District>>() {
							}.getType();
							ArrayList<District> retval = gson.fromJson(
									response, collectionType);
							return retval;
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						return null;
					}
				}

			} else {
				log.error("Error: " + resp.getStatusLine());
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Get districts by province_id
	 * 
	 * @param provinceId
	 * @param page
	 * @return null if error
	 */
	public static ArrayList<District> getDistricts(long provinceId, int page) {
		final HttpResponse resp;
		String uri = null;

		if (provinceId <= 0) {
			return null;
		} else {
			if (page <= 0) {
				uri = BASE_URL + "/provinces/" + provinceId + "/districts";
			} else {
				uri = BASE_URL + "/provinces/" + provinceId + "/districts"
						+ "?page=" + page + "&" + PARAM_PER_PAGE + "="
						+ DEFAULT_PER_PAGE;
			}
		}

		log.info("Request: " + uri);
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

					log.info("Response retrieved");
					ireader.close();

					String response = sb.toString();

					if (response.length() > 0) {
						try {
							Gson gson = getGson();
							Type collectionType = new TypeToken<ArrayList<District>>() {
							}.getType();
							ArrayList<District> retval = gson.fromJson(
									response, collectionType);
							return retval;

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						return null;
					}
				}

			} else {
				log.error("Error: " + resp.getStatusLine());
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Get Subdistrict
	 * 
	 * @param page
	 * @return null if error
	 */
	public static ArrayList<SubDistrict> getSubdistricts(long page) {
		final HttpResponse resp;
		String uri = null;

		if (page <= 0) {
			uri = BASE_URL + "/subdistricts";
		} else {
			uri = BASE_URL + "/subdistricts?page=" + page + "&"
					+ PARAM_PER_PAGE + "=" + DEFAULT_PER_PAGE;
		}

		log.info("Request: " + uri);
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
					log.info("Response retrieved");
					ireader.close();
					String response = sb.toString();
					if (response.length() > 0) {
						try {
							Gson gson = getGson();
							Type collectionType = new TypeToken<ArrayList<SubDistrict>>() {
							}.getType();
							ArrayList<SubDistrict> retval = gson.fromJson(
									response, collectionType);
							return retval;

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						return null;
					}
				}

			} else {
				log.error("Error: " + resp.getStatusLine());
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;

	}

	/**
	 * Get subdistricts by districtId
	 * 
	 * @param districtId
	 * @param page
	 * @return null if error
	 */
	public static ArrayList<SubDistrict> getSubdistricts(long districtId,
			int page) {
		final HttpResponse resp;
		String uri = null;
		if (districtId <= 0) {
			return null;
		} else {
			if (page <= 0) {
				uri = BASE_URL + "/districts/" + districtId + "/subdistricts";
			} else {
				uri = BASE_URL + "/districts/" + districtId + "/subdistricts"
						+ "?page=" + page + "&" + PARAM_PER_PAGE + "="
						+ DEFAULT_PER_PAGE;
			}

		}

		log.info("Request: " + uri);
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

					log.info("Response retrieved");
					ireader.close();
					String response = sb.toString();

					if (response.length() > 0) {
						try {
							Gson gson = getGson();
							Type collectionType = new TypeToken<ArrayList<SubDistrict>>() {
							}.getType();
							ArrayList<SubDistrict> retval = gson.fromJson(
									response, collectionType);
							return retval;

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						return null;
					}
				}

			} else {
				log.error("Error: " + resp.getStatusLine());
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;

	}

	/**
	 * Get comment from server
	 * 
	 * @param postId
	 * @param page
	 * @return null if error
	 */
	public static ArrayList<Comment> getComment(long postId, int page) {
		final HttpResponse resp;

		String uri = null;
		if (page <= 0) {
			uri = BASE_URL + "/posts/" + postId + "/comments?page=1&"
					+ PARAM_PER_PAGE + "=" + DEFAULT_PER_PAGE;
		} else {
			uri = BASE_URL + "/posts/" + postId + "/comments?page=" + page
					+ "&" + PARAM_PER_PAGE + "=" + DEFAULT_PER_PAGE;
		}

		log.info("Request: " + uri + ", page: " + page);
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

					log.info("Response: " + sb.toString());
					ireader.close();

					String response = sb.toString();

					if (response.length() > 0) {
						try {
							Gson gson = getGson();
							Type collectionType = new TypeToken<ArrayList<Comment>>() {
							}.getType();
							ArrayList<Comment> retval = gson.fromJson(response,
									collectionType);
							return retval;

						} catch (Exception ex) {
							ex.printStackTrace();
						}
						return null;
					}
				}
			} else {
				log.error("Error: " + resp.getStatusLine());
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Register new user
	 * 
	 * @param name
	 * @param email
	 * @param emailPassword
	 * 
	 * @return null if error <br/>
	 * 
	 *         json result
	 *         {"created_at":"2013-11-19T16:34:21Z","email":"student1@gmail.com"
	 *         ,"id":10,"picture_content_type":null,"picture_file_name":null,
	 *         "picture_file_size":null,"picture_updated_at":null,"updated_at":
	 *         "2013-11-19T16:34:21Z","username":"student1"}
	 */
	public static String registerNewUser(String name, String email,
			String emailPassword) {
		final HttpResponse resp;
		String uri = null;
		if (name != null && email != null && emailPassword != null) {
			uri = BASE_URL + "/users";

		}
		final HttpPost request = new HttpPost(uri);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("user[email]", email));
			nameValuePairs.add(new BasicNameValuePair("user[username]", name));
			nameValuePairs.add(new BasicNameValuePair("user[password]",
					emailPassword));
			nameValuePairs.add(new BasicNameValuePair(
					"user[password_confirmation]", emailPassword));
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			log.info("Request: " + uri);
			resp = getHttpClient().execute(request);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
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
					ireader.close();
					String response = sb.toString();

					log.info("Response :" + response);
					if (response.length() > 0) {
						try {
							JsonParser parser = new JsonParser();
							JsonElement resElmt = parser.parse(response);
							if (resElmt.isJsonObject()) {
								JsonObject obj = resElmt.getAsJsonObject();
								String email_response = obj.get("email")
										.getAsString();

								if (email_response.equals(email)) {
									String created_at = obj.get("created_at")
											.getAsString();
									String id = obj.get("id").getAsString();
									String picture_content_type = obj.get(
											"picture_content_type")
											.getAsString();
									String picture_file_name = obj.get(
											"picture_file_name").getAsString();
									String picture_file_size = obj.get(
											"picture_file_size").getAsString();
									String updated_at = obj.get("updated_at")
											.getAsString();
									String picture_update_at = obj.get(
											"picture_update_at").getAsString();
									String username = obj.get("username")
											.getAsString();

									return username;
								} else {
									return null;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			} else {
				// failed
				log.error("Error " + resp.getStatusLine().toString());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	/**
	 * Sign in to JalinSuara <br/>
	 * success response: {"success":true,"auth_token":"RwVBVHsFtF1VezDEVtDq"
	 * ,"email":"tonoman3g@gmail.com"}
	 * 
	 * <br/>
	 * error response: <br/>
	 * {"success":false,"message":"Error with your login or password"}
	 * 
	 * @param email
	 * @param email_password
	 * @return null if error
	 */
	public static String signIn(String email, String email_password) {
		final HttpResponse resp;

		String uri = BASE_URL + "/users/sign_in";
		if (email != null && email_password != null) {

		}
		final HttpPost request = new HttpPost(uri);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("user_login[email]",
					email));
			nameValuePairs.add(new BasicNameValuePair("user_login[password]",
					email_password));
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			log.info("Request :" + request.getRequestLine());
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
					ireader.close();
					String response = sb.toString();

					log.info("Response :" + response);
					if (response.length() > 0) {
						try {
							JsonParser parser = new JsonParser();
							JsonElement resElmt = parser.parse(response);
							if (resElmt.isJsonObject()) {
								JsonObject obj = resElmt.getAsJsonObject();
								boolean success = obj.get("success")
										.getAsBoolean();
								if (success) {
									String token = obj.get("auth_token")
											.getAsString();
									log.info("token:" + token);
									return token;
								} else {
									return null;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				// failed
				log.error("Error :" + resp.getStatusLine().getStatusCode()
						+ ", body: " + processResponseStream(resp));

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// return null because something error
		return null;
	}

	/**
	 * Share news
	 * 
	 * @param title
	 * @param description
	 * @param postable_type
	 * @param postable_id
	 * @param user_id
	 * @param auth_token
	 * @return null if there is error
	 */
	public static String postShareNews(ShareNewsParams param) {
		final HttpResponse resp;
		String uri = null;

		uri = BASE_URL + "/posts";

		final HttpPost request = new HttpPost(uri);
		try {
			MultipartEntity entity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);

			// For File parameters
			if (param.getFile() != null) {
				log.info("file " + param.getFile());
				String extension = FileUtils.getExtension(param.getFile());
				String mimeType = MimeTypeMap.getSingleton()
						.getMimeTypeFromExtension(extension);
				log.info("mimetype: " + mimeType);
				if (mimeType != null) {
					File file = new File(param.getFile());
					entity.addPart("post[picture]", new FileBody(((File) file),
							mimeType));
				}
			}

			// For usual String parameters
			entity.addPart("post[title]", new StringBody(param.getTitle()));
			entity.addPart("post[description]",
					new StringBody(param.getDescription()));
			entity.addPart("post[postable_type]",
					new StringBody(param.getPostable_type()));
			entity.addPart("post[postable_id]",
					new StringBody(param.getPostable_id()));
			entity.addPart("post[budget]", new StringBody(param.getBudget()));
			entity.addPart("post[beneficiary]",
					new StringBody(param.getBeneficiary()));
			entity.addPart("post[dimensions]",
					new StringBody(param.getDimensions()));
			entity.addPart("auth_token", new StringBody(param.getAuth_token()));

			request.setEntity(entity);

			log.info("Request: " + request.getRequestLine().toString());
			resp = getHttpClient().execute(request);

			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
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
					ireader.close();
					String response = sb.toString();

					log.info("Response :" + response);
					if (response.length() > 0) {
						try {
							News news = getGson()
									.fromJson(response, News.class);
							if (news.getId() != 0) {
								return "Success";
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			} else {
				log.error("Error: " + resp.getStatusLine());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Sign out from JalinSuara
	 * 
	 * @param email
	 * @return null if error
	 */
	public static boolean signOut(String email) {
		final HttpResponse resp;
		String uri = BASE_URL + "/users/sign_out";

		if (email != null) {

		}
		final HttpDeleteWithBody request = new HttpDeleteWithBody(uri);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("user_login[email]",
					email));

			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			log.info("request to " + request.getRequestLine());
			resp = getHttpClient().execute(request);
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String response = NetworkUtils.processResponseStream(resp);
				log.info("Response: " + response);

				if (response != null) {
					JsonParser parser = new JsonParser();
					JsonElement resElmt = parser.parse(response);
					if (resElmt.isJsonObject()) {
						JsonObject obj = resElmt.getAsJsonObject();
						boolean success = obj.get("success").getAsBoolean();
						return success;
					}
					return false;

				} else {
					return false;
				}

			} else {
				// failed
				log.error(resp.getStatusLine().getStatusCode() + "");
				return false;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * Get gson
	 * 
	 * @return
	 */
	public static Gson getGson() {
		if (mGson == null) {
			GsonBuilder builder = new GsonBuilder();
			builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
			mGson = builder.create();
		}
		return mGson;
	}

	/**
	 * parse httpresponse to string
	 * 
	 * @param resp
	 * @return null if no resposne
	 * @throws IOException
	 */
	public static String processResponseStream(HttpResponse resp)
			throws IOException {
		InputStream istream = (resp.getEntity() != null) ? resp.getEntity()
				.getContent() : null;
		if (istream != null) {
			BufferedReader ireader = new BufferedReader(new InputStreamReader(
					istream));

			String line = ireader.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line);
				line = ireader.readLine();
			}
			ireader.close();
			return sb.toString();
		}
		return null;
	}

	/**
	 * Post a comment to a news / post
	 * 
	 * 
	 * {"body":"Coba-coba","commentable_id":16,"commentable_type":"Post",
	 * "created_at"
	 * :"2013-11-21T01:49:19Z","guest_email":"hartono.sulaiman@gmail.com"
	 * ,"guest_name"
	 * :"Tono","id":12,"owner_id":4,"updated_at":"2013-11-21T01:49:19Z"}
	 * 
	 * @param postId
	 * @param username
	 * @param email
	 * @param comment
	 * @param token
	 * @param isAuthenticated
	 * @return null if error
	 */
	public static String postNewComment(long postId, String username,
			String email, String comment, String token, boolean isAuthenticated) {
		final HttpResponse resp;
		String uri = null;

		uri = BASE_URL + "/posts/" + postId + "/comments";
		final HttpPost request = new HttpPost(uri);

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			if (isAuthenticated) {
				nameValuePairs.add(new BasicNameValuePair("auth_token", token));
				nameValuePairs.add(new BasicNameValuePair("comment[body]",
						comment));
				nameValuePairs.add(new BasicNameValuePair(
						"comment[commentable_type]",
						Comment.COMMENTABLE_TYPE_POST));
				nameValuePairs.add(new BasicNameValuePair(
						"comment[commentable_id]", String.valueOf(postId)));
				request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			} else {
				nameValuePairs.add(new BasicNameValuePair(
						"comment[guest_name]", username));
				nameValuePairs.add(new BasicNameValuePair(
						"comment[guest_email]", email));
				nameValuePairs.add(new BasicNameValuePair("comment[body]",
						comment));
				nameValuePairs.add(new BasicNameValuePair(
						"comment[commentable_type]",
						Comment.COMMENTABLE_TYPE_POST));
				nameValuePairs.add(new BasicNameValuePair(
						"comment[commentable_id]", String.valueOf(postId)));
				request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			}

			if (request.getEntity() != null) {
				log.info("Request: "+request.getRequestLine().toString());
				log.info("entity: "+nameValuePairs.toString());
				resp = getHttpClient().execute(request);
				log.info("Response: "+resp.getStatusLine().toString());
				if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
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
						ireader.close();
						String response = sb.toString();

						log.info("Response :" + response);
						if (response.length() > 0) {
							try {
								// JsonParser parser = new JsonParser();
								// JsonElement resElmt = parser.parse(response);
								// if (resElmt.isJsonObject()) {
								// JsonObject obj = resElmt.getAsJsonObject();
								// boolean success = obj.get("success")
								// .getAsBoolean();
								//
								// if (success) {
								// String message = obj.get("message")
								// .getAsString();
								// return message;
								// } else {
								// String message = obj.get("message")
								// .getAsString();
								// return message;
								// }
								// }
								return "success";
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}

				} else {
					log.error("Failed to create");
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
