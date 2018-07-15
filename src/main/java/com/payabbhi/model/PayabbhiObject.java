package com.payabbhi.model;

import org.json.JSONException;
import org.json.JSONObject;

public class PayabbhiObject {

  protected static String pluralize(Class<?> clazz) {
    return clazz.getSimpleName().toLowerCase() + "s";
  }

  protected JSONObject getModelJson() {
    return modelJson;
  }

  protected void setModelJson(JSONObject modelJson) {
    this.modelJson = modelJson;
  }

  private JSONObject modelJson;

  public PayabbhiObject(JSONObject jsonObject) {
    this.modelJson = jsonObject;
  }

  @SuppressWarnings("unchecked")
  public <T> T get(String key) {
    try {
      Object value = modelJson.get(key);
      if (value == null || value == JSONObject.NULL) {
        return null;
      }
      return (T) value.getClass().cast(value);
    } catch (JSONException e) {
      return null;
    }
  }

  public String toString() {
    return modelJson.toString();
  }

  public boolean has(String key) {
    return modelJson.has(key);
  }
}
