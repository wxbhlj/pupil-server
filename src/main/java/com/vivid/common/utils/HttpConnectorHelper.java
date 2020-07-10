package com.vivid.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpConnectorHelper {
	private static int connectionTimeout = 3000;
	private static int dataReadTimeout = 5000;
	private final static int maxTotalConnections = 200;
	private final static int maxPerRoute = 20;
	private final static PoolingHttpClientConnectionManager cm;
	private final static RequestConfig requestConfig = RequestConfig.custom()//
	        .setSocketTimeout(dataReadTimeout)//
	        .setConnectTimeout(connectionTimeout).build();

	static {
		cm = new PoolingHttpClientConnectionManager();
		// Increase max total connection to 200
		cm.setMaxTotal(maxTotalConnections);
		// Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(maxPerRoute);
		// Increase max connections for localhost:80 to 50
		//final HttpHost localhost = new HttpHost("locahost", 80);
		//cm.setMaxPerRoute(new HttpRoute(localhost), 50);
	}

	private static ConnectionKeepAliveStrategy keepAliveStrat = new DefaultConnectionKeepAliveStrategy() {
		@Override
		public long getKeepAliveDuration(final HttpResponse response, final HttpContext context) {
			long keepAlive = super.getKeepAliveDuration(response, context);
			if (keepAlive == -1) {
				keepAlive = 5000;
			}
			return keepAlive;
		}

	};

	public static String httpGet(final String url) {
		return httpGet(url, null, null);
	}

	public static String httpGet(final String url, final Map<String, List<Object>> params) {
		return httpGet(url, params, null);
	}

	public static String httpGet(final String url, final Map<String, List<Object>> params, final Map<String, String> headers) {
		final CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).setKeepAliveStrategy(keepAliveStrat).build();
		CloseableHttpResponse response = null;
		try {
			final URIBuilder builder = new URIBuilder(url);
			if ((params != null) && !params.isEmpty()) {
				for (final String key : params.keySet()) {
					if (StringUtils.isEmpty(key)) {
						continue;
					}
					final List<Object> vals = params.get(key);
					if (vals == null) {
						continue;
					}
					for (final Object val : vals) {
						builder.addParameter(key, String.valueOf(val));
					}
				}
			}
			final HttpGet httpGet = new HttpGet(builder.build());
			httpGet.setConfig(requestConfig);
			httpGet.addHeader("accept", "application/json");
			httpGet.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);

			if (headers != null) {
				for (final String key : headers.keySet()) {
					if ((key == null) || (key.trim().length() == 0) || (headers.get(key) == null)) {
						continue;
					}
					httpGet.addHeader(key, headers.get(key));
				}
			}
			response = httpClient.execute(httpGet);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
		finally {
			if (response != null) {
				try {
					response.close();
				}
				catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String httpPost(final String url) {
		return httpPost(url, null, null);
	}

	public static String httpPost(final String url, final String data) {
		return httpPost(url, data, null);
	}

	public static String httpPost(final String url, final String data, final Map<String, String> headers) {
		final CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).setKeepAliveStrategy(keepAliveStrat).build();
		CloseableHttpResponse response = null;
		try {
			final HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			httpPost.addHeader("accept", "application/json");
			httpPost.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);

			if (headers != null) {
				for (final String key : headers.keySet()) {
					if ((key == null) || (key.trim().length() == 0) || (headers.get(key) == null)) {
						continue;
					}
					httpPost.addHeader(key, headers.get(key));
				}
			}
			if (StringUtils.isNotEmpty(data)) {
				final StringEntity postBody = new StringEntity(data, "UTF-8");
				postBody.setContentType("application/json");
				httpPost.setEntity(postBody);
			}
			response = httpClient.execute(httpPost);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		}
		catch (final ClientProtocolException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		finally {
			if (response != null) {
				try {
					response.close();
				}
				catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}
	public static String httpPostByFrom(final String url, final Map<String,String> data, final Map<String, String> headers) {
		final CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).setKeepAliveStrategy(keepAliveStrat).build();
		CloseableHttpResponse response = null;
		try {
			final HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			httpPost.addHeader("accept", "application/json");
			httpPost.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);

			if (headers != null) {
				for (final String key : headers.keySet()) {
					if ((key == null) || (key.trim().length() == 0) || (headers.get(key) == null)) {
						continue;
					}
					httpPost.addHeader(key, headers.get(key));
				}
			}
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
			if(data != null){
				for (String key : data.keySet()){
					nameValuePairs.add(new BasicNameValuePair(key,data.get(key)));
				}
			}
			final UrlEncodedFormEntity postBody = new UrlEncodedFormEntity(nameValuePairs,"UTF-8");
				postBody.setContentType(MediaType.APPLICATION_FORM_URLENCODED.toString());
				httpPost.setEntity(postBody);

			response = httpClient.execute(httpPost);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		}
		catch (final ClientProtocolException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		finally {
			if (response != null) {
				try {
					response.close();
				}
				catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}
	public static String httpDeletet(final String url, final String data) {
		final CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).setKeepAliveStrategy(keepAliveStrat).build();
		CloseableHttpResponse response = null;
		try {
			final HttpDelete httpPost = new HttpDelete(url);
			httpPost.setConfig(requestConfig);
			httpPost.addHeader("accept", "application/json");
			httpPost.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);

			response = httpClient.execute(httpPost);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		}
		catch (final ClientProtocolException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		finally {
			if (response != null) {
				try {
					response.close();
				}
				catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}
	
	public static String httpPut(final String url, final String data) {
		final CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).setKeepAliveStrategy(keepAliveStrat).build();
		CloseableHttpResponse response = null;
		try {
			final HttpPut httpPost = new HttpPut(url);
			httpPost.setConfig(requestConfig);
			httpPost.addHeader("accept", "application/json");
			httpPost.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);

			response = httpClient.execute(httpPost);
			return EntityUtils.toString(response.getEntity(), "UTF-8");
		}
		catch (final ClientProtocolException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		finally {
			if (response != null) {
				try {
					response.close();
				}
				catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
