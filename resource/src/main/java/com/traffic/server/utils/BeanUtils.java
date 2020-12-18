package com.traffic.server.utils;

import java.beans.ConstructorProperties;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

import com.traffic.server.exception.ApiException;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.DataBinder;

public class BeanUtils {
    private static ConcurrentMap<BeanUtils.Key, BeanUtils.Holder<BeanCopier>> beanCopierMap = new ConcurrentHashMap();
    private static Converter converter = ConverterFactory.getConverter();
    private static Map<Class, Object> primitiveDefaultValues = new HashMap();

    public BeanUtils() {
    }

    public static <T> T copyProperties(Object source, T target) {
        return copyProperties(source, target, true);
    }

    public static <T> T copyProperties(Object source, Supplier<T> objectCreator) {
        return copyProperties(source, objectCreator, true);
    }

    public static <T> T copyProperties(Object source, Supplier<T> objectCreator, boolean convert) {
        try {
            return copyProperties(source, objectCreator.get(), convert);
        } catch (Exception var4) {
            throw new ApiException(500, var4.getMessage());
        }
    }

    public static <T> T copyProperties(Object source, T target, boolean convert) {
        if (source == null) {
            return target;
        } else {
            BeanUtils.Key beanKey = genKey(source.getClass(), target.getClass(), convert);
            BeanUtils.Holder<BeanCopier> holder = (BeanUtils.Holder)beanCopierMap.get(beanKey);
            if (holder == null) {
                holder = new BeanUtils.Holder();
                BeanUtils.Holder<BeanCopier> old = (BeanUtils.Holder)beanCopierMap.putIfAbsent(beanKey, holder);
                if (old != null) {
                    holder = old;
                }
            }

            if (holder.value == null) {
                synchronized(holder) {
                    if (holder.value == null) {
                        holder.value = BeanCopier.create(source.getClass(), target.getClass(), convert);
                    }
                }
            }

            ((BeanCopier)holder.value).copy(source, target, converter);
            return target;
        }
    }

    public static <T> T populate(Map<String, ? extends Object> source, T target) {
        DataBinder dataBinder = new DataBinder(target);
        dataBinder.bind(new MutablePropertyValues(source));
        return target;
    }

    public static <T> T populate(Map<String, ? extends Object> source, T target, Map<String, String> nameMap) {
        Map<String, Object> newProperties = new HashMap();
        Iterator var4 = source.entrySet().iterator();

        while(var4.hasNext()) {
            Entry<String, ? extends Object> entry = (Entry)var4.next();
            String name = (String)nameMap.get(entry.getKey());
            if (name == null) {
                name = (String)entry.getKey();
            }

            Object value = entry.getValue();
            newProperties.put(name, value);
        }

        return populate(newProperties, target);
    }

    public static <T> List<T> transform(List<?> sourceList, Class<T> targetClazz, boolean convert) {
        return (List)(sourceList == null ? new ArrayList() : transform(sourceList, () -> {
            try {
                return targetClazz.newInstance();
            } catch (Exception var2) {
                throw new ApiException(500, var2.getMessage());
            }
        }, convert));
    }

    public static <T> List<T> transform(List<?> sourceList, Class<T> targetClazz) {
        return transform(sourceList, targetClazz, true);
    }

    public static <T> List<T> transform(List<?> sourceList, Supplier<T> objectCreator) {
        return transform(sourceList, objectCreator, true);
    }

    public static <T> List<T> transform(List<?> sourceList, Supplier<T> objectCreator, boolean convert) {
        List<T> targetList = new ArrayList(sourceList.size());
        Iterator var4 = sourceList.iterator();

        while(var4.hasNext()) {
            Object source = var4.next();

            try {
                T target = objectCreator.get();
                targetList.add(copyProperties(source, target, convert));
            } catch (Exception var7) {
                throw new ApiException(500, var7.getMessage());
            }
        }

        return targetList;
    }

    public static <T> T deepCopy(T source) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(source);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            return (T)in.readObject();
        } catch (Exception var5) {
            throw new ApiException(500, var5.getMessage());
        }
    }

    private static BeanUtils.Key genKey(Class<?> source, Class<?> target, boolean convert) {
        return new BeanUtils.Key(source, target, convert);
    }

    static {
        primitiveDefaultValues.put(Integer.TYPE, 0);
        primitiveDefaultValues.put(Boolean.TYPE, false);
        primitiveDefaultValues.put(Character.TYPE, ' ');
        primitiveDefaultValues.put(Long.TYPE, 0L);
        primitiveDefaultValues.put(Short.TYPE, 0);
        primitiveDefaultValues.put(Double.TYPE, 0.0D);
        primitiveDefaultValues.put(Float.TYPE, 0.0F);
    }

    static class Key {
        private Class source;
        private Class target;
        private boolean convert;

        public Class getSource() {
            return this.source;
        }

        public Class getTarget() {
            return this.target;
        }

        public boolean isConvert() {
            return this.convert;
        }

        public void setSource(Class source) {
            this.source = source;
        }

        public void setTarget(Class target) {
            this.target = target;
        }

        public void setConvert(boolean convert) {
            this.convert = convert;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof BeanUtils.Key)) {
                return false;
            } else {
                BeanUtils.Key other = (BeanUtils.Key)o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    Object this$target = this.getSource();
                    Object other$target = other.getSource();
                    if (this$target == null) {
                        if (other$target != null) {
                            return false;
                        }
                    } else if (!this$target.equals(other$target)) {
                        return false;
                    }

                    this$target = this.getTarget();
                    other$target = other.getTarget();
                    if (this$target == null) {
                        if (other$target != null) {
                            return false;
                        }
                    } else if (!this$target.equals(other$target)) {
                        return false;
                    }

                    if (this.isConvert() != other.isConvert()) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }

        protected boolean canEqual(Object other) {
            return other instanceof BeanUtils.Key;
        }

        public int hashCode() {
            boolean PRIME = true;
            int result = 1;
            Object $source = this.getSource();
            result = result * 59 + ($source == null ? 43 : $source.hashCode());
            Object $target = this.getTarget();
            result = result * 59 + ($target == null ? 43 : $target.hashCode());
            result = result * 59 + (this.isConvert() ? 79 : 97);
            return result;
        }

        public String toString() {
            return "BeanUtils.Key(source=" + this.getSource() + ", target=" + this.getTarget() + ", convert=" + this.isConvert() + ")";
        }

        @ConstructorProperties({"source", "target", "convert"})
        public Key(Class source, Class target, boolean convert) {
            this.source = source;
            this.target = target;
            this.convert = convert;
        }
    }

    static class Holder<T> {
        private volatile T value;

        Holder() {
        }
    }
}
