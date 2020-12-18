package com.traffic.server.redis;

import java.io.*;

/**
 * 序列化工具
 * @Author 黄国刚【1058118328@qq.com】
 */
public class SerializeUtils {

    /**
     * 序列化
     * @param obj
     * @return
     * @throws IOException
     */
    public static byte[] serialize(Object obj) throws IOException {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            return baos.toByteArray();
        } finally {
            if(oos != null)
                try {
                    oos.close();
                } catch (IOException e) {}
        }
    }

    /**
     * 反序列化
     * @param bits
     * @return
     * @throws IOException
     */
    public static Object deserialize(byte[] bits) throws IOException {
        if(bits == null || bits.length == 0)
            return null;
        ObjectInputStream ois = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bits);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("serialize error,class not found");
        } finally {
            if(ois != null)
                try {
                    ois.close();
                } catch (IOException e) {}
        }
    }
}
