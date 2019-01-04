package com.payabbhi.net;

import com.payabbhi.Payabbhi;
import com.payabbhi.exception.ApiException;
import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.model.PayabbhiCollection;
import com.payabbhi.model.PayabbhiObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class APIResource extends PayabbhiObject {
  private static volatile Fetcher fetcher = new LiveFetcher();

  public static void setFetcher(Fetcher fetcher) {
    APIResource.fetcher = fetcher;
  }

  public enum Method {
    GET,
    POST,
    PUT,
    DELETE
  }

  protected static String urlFor(Class<?> clazz) {
    return String.format("%s/%s", Payabbhi.apiBase(), pluralize(clazz));
  }

  protected static String urlFor(Class<?> clazz, String id) {
    return String.format("%s/%s/%s", Payabbhi.apiBase(), pluralize(clazz), id);
  }

  protected static String urlFor(Class<?> clazz, String id, String action) {
    return String.format("%s/%s/%s/%s", Payabbhi.apiBase(), pluralize(clazz), id, action);
  }

  protected static String urlFor(Class<?> clazz, String id, Class<?> clazzChild) {
    return String.format(
        "%s/%s/%s/%s", Payabbhi.apiBase(), pluralize(clazz), id, pluralize(clazzChild));
  }

  protected static String urlFor(Class<?> clazz, String id, Class<?> clazzChild, String idChild) {
    return String.format(
        "%s/%s/%s/%s/%s", Payabbhi.apiBase(), pluralize(clazz), id, pluralize(clazzChild), idChild);
  }

  protected static String withParams(String url, Map<String, Object> params) {
    if (params == null) {
      return url;
    }
    StringBuilder sb = new StringBuilder();
    try {
      for (Map.Entry<?, ?> entry : params.entrySet()) {
        if (sb.length() > 0) {
          sb.append("&");
        }
        String key = URLEncoder.encode(safeString(entry.getKey()), "UTF-8");
        String value = URLEncoder.encode(safeString(entry.getValue()), "UTF-8");

        sb.append(String.format("%s=%s", key, value));
      }
    } catch (UnsupportedEncodingException e) {
      new ApiException("Illegal arguments to API", e);
    }
    return String.format("%s?%s", url, sb.toString());
  }

  private static String safeString(Object value) {
    if (value == null) {
      return "";
    }
    return value.toString();
  }

  protected static <T> T request(
      Method method, String url, Map<String, Object> bodyParams, Class<T> clazz)
      throws PayabbhiException {
    APIResponse response = APIResource.fetcher.fetch(method, url, toJSONString(bodyParams));
    return (T) unmarshalObject(sanityCheck(response).getBody(), clazz);
  }

  protected static <T> PayabbhiCollection<T> requestCollection(
      Method method, String url, Map<String, Object> bodyParams, Class<T> clazz)
      throws PayabbhiException {
    APIResponse response = APIResource.fetcher.fetch(method, url, toJSONString(bodyParams));

    return (PayabbhiCollection<T>) unmarshalCollection(sanityCheck(response).getBody(), clazz);
  }

  private static APIResponse sanityCheck(APIResponse response) throws PayabbhiException {
    if (response.getStatus() >= 200 && response.getStatus() < 300) {
      return response;
    } else {
      APIErrorResponse error = unmarshallError(response);
      throw error.toException();
    }
  }

  private static APIErrorResponse unmarshallError(APIResponse response) {
    JSONObject resp = new JSONObject(response.getBody());
    JSONObject er = resp.getJSONObject("error");
    return new APIErrorResponse(
        response.getStatus(), er.getString("field"), er.getString("message"), er.getString("type"));
  }

  protected static <T> T unmarshalObject(String body, Class<T> clazz) throws PayabbhiException {

    try {
      JSONObject jsonObject = new JSONObject(body);
      T obj = clazz.getConstructor(JSONObject.class).newInstance(jsonObject);
      return obj;
    } catch (Exception e) {
      throw new PayabbhiException("Invalid API response");
    }
  }

  protected static <T> PayabbhiCollection<T> unmarshalCollection(String body, Class<T> clazz)
      throws PayabbhiException {
    try {
      JSONObject jsonObject = new JSONObject(body);
      JSONArray jsonArray = jsonObject.getJSONArray("data");

      PayabbhiCollection<T> modelList =
          new PayabbhiCollection<T>(jsonObject, jsonObject.getInt("total_count"));
      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jsonObj = jsonArray.getJSONObject(i);
        T obj = clazz.getConstructor(JSONObject.class).newInstance(jsonObj);
        modelList.add(obj);
      }
      return modelList;
    } catch (Exception e) {
      throw new PayabbhiException("Invalid API response", e);
    }
  }

  private static String toJSONString(Map<String, Object> params) {
    JSONObject requestObject = new JSONObject(params);
    String requestContent = requestObject == null ? "" : requestObject.toString();
    return requestContent;
  }

  public APIResource(JSONObject jsonObject) {
    super(jsonObject);
  }
}
