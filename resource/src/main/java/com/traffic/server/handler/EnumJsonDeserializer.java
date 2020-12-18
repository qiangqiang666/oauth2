package com.traffic.server.handler;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.traffic.server.enums.CodeBaseEnum;
import org.springframework.beans.BeanUtils;

import java.io.IOException;

public class EnumJsonDeserializer extends JsonDeserializer<CodeBaseEnum> {
    public EnumJsonDeserializer() {
    }

    public CodeBaseEnum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = (JsonNode)jp.getCodec().readTree(jp);
        String currentName = jp.currentName();
        Object currentValue = jp.getCurrentValue();
        Class findPropertyType = BeanUtils.findPropertyType(currentName, new Class[]{currentValue.getClass()});
        CodeBaseEnum valueOf = null;
        if (node.has("code")) {
            valueOf = (CodeBaseEnum)CodeBaseEnum.codeOf(findPropertyType, node.get("code").asInt());
        } else {
            valueOf = (CodeBaseEnum)CodeBaseEnum.codeOf(findPropertyType, node.textValue());
        }

        return valueOf;
    }
}
