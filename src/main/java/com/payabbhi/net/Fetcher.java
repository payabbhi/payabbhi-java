package com.payabbhi.net;

import com.payabbhi.exception.PayabbhiException;
import com.payabbhi.net.APIResource.Method;

/** Represents the interface of a REST api client */
public interface Fetcher {

  APIResponse fetch(Method method, String url, String body) throws PayabbhiException;
}
