package com.traffic.authentication.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.traffic.authentication.exception.ApiException;
import com.traffic.authentication.handler.EnumJsonDeserializer;

import java.util.Objects;

@JsonDeserialize(
    using = EnumJsonDeserializer.class
)
public interface CodeBaseEnum {
    int code();

    String msg();

    static <E extends Enum<?> & CodeBaseEnum> E codeOf(Class<E> enumClass, int code) {
        E[] enumConstants = (E[]) enumClass.getEnumConstants();
        Enum[] var3 = enumConstants;
        int var4 = enumConstants.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            E e = (E) var3[var5];
            if (((CodeBaseEnum)e).code() == code) {
                return e;
            }
        }

        throw new ApiException(500, "enum.code.not.exsit");
    }

    static <E extends Enum<?> & CodeBaseEnum> E codeOf(Class<E> enumClass, String msg) {
        E[] enumConstants = (E[]) enumClass.getEnumConstants();
        Enum[] var3 = enumConstants;
        int var4 = enumConstants.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            E e = (E) var3[var5];
            if (Objects.equals(e.name(), msg)) {
                return e;
            }
        }

        throw new ApiException(500, "enum.code.not.exsit");
    }
}
