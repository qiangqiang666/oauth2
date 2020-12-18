package com.traffic.authentication.oauth2.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@JsonSerialize(using = OauthExceptionSerializer.class)
public class OauthException extends OAuth2Exception {
    public OauthException(String msg, Throwable t) {
        super(msg, t);
    }

    public OauthException(String msg) {
        super(msg);
    }

}

