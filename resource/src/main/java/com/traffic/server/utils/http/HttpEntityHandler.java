/**
 * @Copyright@wantdo.com 2015
 */

package com.traffic.server.utils.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;

/**
 * @Auther: liwu
 * @Date: 2020/1/9 18:04
 * @Description:
 */
public abstract class HttpEntityHandler<T> implements ResponseHandler<T> {

    public T handleResponse(HttpResponse response) throws IOException {

        return handle(response.getEntity());
    }

    public abstract T handle(HttpEntity entity) throws IOException;

    public abstract String getName();

}
