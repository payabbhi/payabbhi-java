package com.payabbhi.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 * A generic collection of objects received from the payabbhi rest api
 *
 * @param <T> type of elements of the collections
 */
public class PayabbhiCollection<T> extends PayabbhiObject {
  private List<T> data;
  private Integer totalCount;

  public PayabbhiCollection(JSONObject jsonObject) {
    super(jsonObject);
    this.data = new ArrayList<T>();
  }

  public PayabbhiCollection(JSONObject jsonObject, Integer count) {
    super(jsonObject);
    this.totalCount = count;
    this.data = new ArrayList<T>();
  }

  /**
   * Returns a List object corresponding to a JSON list
   *
   * @return a list of elements of the collection
   */
  public List<T> getData() {
    return data;
  }

  /**
   * returns the count of objects in the collection
   *
   * @return number of elements of the collection
   */
  public Integer count() {
    return totalCount;
  }

  /**
   * Adds an object to the collection
   *
   * @param obj the object to be added to the collection
   */
  public void add(T obj) {
    this.data.add(obj);
  }
}
