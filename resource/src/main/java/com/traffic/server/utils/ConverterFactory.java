package com.traffic.server.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.sf.cglib.core.Converter;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.util.ClassUtils;

public class ConverterFactory {
    private static final Object NULL_OBJECT = new Object();
    private static boolean isApacheCommonsBeanUtilsAvailable = ClassUtils.isPresent("org.apache.commons.beanutils.BeanUtilsBean", (ClassLoader)null);
    private static final Converter CONVERTER = initConverter();

    public ConverterFactory() {
    }

    public static Converter getConverter() {
        return CONVERTER;
    }

    private static Converter initConverter() {
        return initCompositeConverterConverter(true);
    }

    private static Converter initEnumConverter() {
        Converter compositeConverter = initCompositeConverterConverter(false);
        return new ConverterFactory.EnumConverter(compositeConverter);
    }

    private static Converter initCompositeConverterConverter(boolean registerEnumConverter) {
        ConverterFactory.CompositeConverter compositeConverter = new ConverterFactory.CompositeConverter();
        if (registerEnumConverter) {
            compositeConverter.registerFirst(initEnumConverter());
        }

        if (isApacheCommonsBeanUtilsAvailable) {
            Converter beanUtilsConverter = ConverterFactory.CommonsBeanUtilsConverterFactory.getConverter();
            compositeConverter.register(beanUtilsConverter);
        }

        compositeConverter.register(new ConverterFactory.SpringConverter());
        return compositeConverter;
    }

    private static class CompositeConverter implements Converter {
        private List<Converter> converters;

        private CompositeConverter() {
            this.converters = new ArrayList();
        }

        public Object convert(Object value, Class target, Object context) {
            if (value == null) {
                return null;
            } else if (value.getClass() != target && !target.isAssignableFrom(value.getClass())) {
                Iterator var4 = this.converters.iterator();

                while(var4.hasNext()) {
                    Converter converter = (Converter)var4.next();
                    Object convertedValue = converter.convert(value, target, (Object)null);
                    if (convertedValue == ConverterFactory.NULL_OBJECT) {
                        return null;
                    }

                    if (convertedValue != null) {
                        return convertedValue;
                    }
                }

                return value;
            } else {
                return value;
            }
        }

        void register(Converter converter) {
            this.converters.add(converter);
        }

        void registerFirst(Converter converter) {
            this.converters.add(0, converter);
        }
    }

    static class SpringConverter implements Converter {
        private final ConversionService conversionService = new DefaultConversionService();

        SpringConverter() {
        }

        public Object convert(Object value, Class target, Object context) {
            return this.conversionService.canConvert(value.getClass(), target) ? this.conversionService.convert(value, target) : null;
        }
    }

    static class EnumConverter implements Converter {
        private Map<Class, Map<String, Enum>> globalStringToEnumMap = new ConcurrentHashMap();
        private Map<Class, Map<Enum, Object>> globalEnumToCodeMap = new ConcurrentHashMap();
        private Converter converter;

        public EnumConverter(Converter converter) {
            this.converter = converter;
        }

        public Object convert(Object value, Class target, Object context) {
            if (target.isEnum()) {
                Map<String, Enum> enumMap = (Map)this.globalStringToEnumMap.get(target);
                if (enumMap == null) {
                    this.loadEnumMap(target);
                    enumMap = (Map)this.globalStringToEnumMap.get(target);
                }

                if (enumMap.isEmpty()) {
                    return null;
                } else {
                    Object targetValue;
                    if (value == null) {
                        targetValue = enumMap.get((Object)null);
                    } else {
                        targetValue = enumMap.get(String.valueOf(value));
                    }

                    return targetValue == null ? ConverterFactory.NULL_OBJECT : targetValue;
                }
            } else if (value.getClass().isEnum()) {
                Class sourceClass = value.getClass();
                Map<Enum, Object> enumToObjectMap = (Map)this.globalEnumToCodeMap.get(sourceClass);
                if (enumToObjectMap == null) {
                    this.loadEnumMap(sourceClass);
                    enumToObjectMap = (Map)this.globalEnumToCodeMap.get(sourceClass);
                }

                if (enumToObjectMap.isEmpty()) {
                    return null;
                } else {
                    Object targetValue = enumToObjectMap.get(value);
                    if (targetValue != null) {
                        if (target.isAssignableFrom(targetValue.getClass())) {
                            return targetValue;
                        } else {
                            targetValue = this.converter.convert(targetValue, target, context);
                            return targetValue == null ? ConverterFactory.NULL_OBJECT : targetValue;
                        }
                    } else {
                        return null;
                    }
                }
            } else {
                return null;
            }
        }

        private void loadEnumMap(Class target) {
            Field field = EnumUtils.findCodeField(target, new Class[0]);
            Map<Enum, Object> enumToObjectMap = new HashMap();
            Map enumMap;
            if (field != null) {
                enumMap = EnumUtils.getCodeToEnumMap(field);
                Iterator var5 = enumMap.entrySet().iterator();

                while(var5.hasNext()) {
                    Entry<Object, Enum> enumEntry = (Entry)var5.next();
                    enumToObjectMap.put(enumEntry.getValue(), enumEntry.getKey());
                }
            }

            enumMap = EnumUtils.getStringCodeToEnumMap(field);
            this.globalStringToEnumMap.put(target, enumMap);
            this.globalEnumToCodeMap.put(target, enumToObjectMap);
        }
    }

    static class CommonsBeanUtilsConverterFactory {
        CommonsBeanUtilsConverterFactory() {
        }

        static Converter getConverter() {
            ConvertUtilsBean convertUtilsBean = BeanUtilsBean.getInstance().getConvertUtils();
            DateConverter converter = new DateConverter();
            converter.setPatterns(new String[]{"yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy-MM-dd", "yyyyMMdd"});
            converter.setUseLocaleFormat(true);
            convertUtilsBean.register(converter, Date.class);
            return (o, targetClass, o1) -> {
                org.apache.commons.beanutils.Converter converter1 = convertUtilsBean.lookup(o.getClass(), targetClass);
                return converter1 != null ? converter1.convert(targetClass, o) : null;
            };
        }
    }
}
