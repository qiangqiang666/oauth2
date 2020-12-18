package com.traffic.authentication.oauth2.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class OauthExceptionSerializer extends StdSerializer<OauthException> {

    protected OauthExceptionSerializer() {
        super(OauthException.class);
    }

    @Override
    public void serialize(OauthException e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("code", e.getHttpErrorCode());
        jsonGenerator.writeStringField("msg", e.getMessage());
        jsonGenerator.writeStringField("data",null);
        jsonGenerator.writeEndObject();
    }
}

