package com.traffic.authentication.util;

import com.traffic.authentication.enums.ErrorEnum;
import com.traffic.authentication.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;

/**
 * @author rihong.g
 * @date 2020-02-12 18:02
 */
@Slf4j
public class DecodePhone {

    public static final String KEY_NAME = "AES";

    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";

    public static String wxDecrypt(String encrypted, String session_key, String iv) {
        String json = null;
        byte[] encrypted64 = Base64.decodeBase64(encrypted);
        byte[] key64 = Base64.decodeBase64(session_key);
        byte[] iv64 = Base64.decodeBase64(iv);
        byte[] data;
        try {
            init();
            json = new String(decrypt(encrypted64, key64, generateIV(iv64)));
        } catch (Exception e) {
            log.error("解析微信加密数据异",e);
            ApiException.thr(ErrorEnum.FL3);
        }
        return json;
    }

    /**
     * 初始化密钥
     */
    public static void init() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        KeyGenerator.getInstance(KEY_NAME).init(128);
    }

    /**
     * 生成iv
     */
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception {
        AlgorithmParameters params = AlgorithmParameters.getInstance(KEY_NAME);
        params.init(new IvParameterSpec(iv));
        return params;
    }

    /**
     * 生成解密
     */
    public static byte[] decrypt(byte[] encryptedData, byte[] keyBytes, AlgorithmParameters iv) throws Exception {
        Key key = new SecretKeySpec(keyBytes, KEY_NAME);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(encryptedData);
    }
}
