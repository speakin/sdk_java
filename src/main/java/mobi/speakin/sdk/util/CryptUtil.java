package mobi.speakin.sdk.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;


/**
 * Created by panleiming on 17-5-15.
 */
public class CryptUtil {
    /*
     *
     */
    public static byte[] AesCrypt(String secret, byte[] content) throws Exception {
        SecretKeySpec key = new SecretKeySpec(secret.getBytes("utf8"), "AES");
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] bs = c.doFinal(content);
        byte[] result = new byte[bs.length + 16];
        System.arraycopy(c.getIV(), 0, result, 0, 16);
        System.arraycopy(bs, 0, result, 16, bs.length);
        return result;
    }

    public static byte[] AesDecrypt(String secret, byte[] content) throws Exception {
        SecretKeySpec key = new SecretKeySpec(secret.getBytes("utf8"), "AES");
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(Arrays.copyOfRange(content, 0, 16)));
        content = c.doFinal(Arrays.copyOfRange(content, 16, content.length));
        return content;
    }

}
