package mobi.speakin.sdk;

import mobi.speakin.sdk.bson.BsonProperty;
import mobi.speakin.sdk.okhttp3.RawRequestBody;
import mobi.speakin.sdk.util.BsonUtil;
import mobi.speakin.sdk.util.CryptUtil;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

/**
 * Created by panleiming on 17-5-15.
 */
public class RequestWarp {
    public static enum ID_TYPE {
        APP_ID, SESSION_ID
    }

    @BsonProperty("id")
    public String id;

    @BsonProperty("id_type")
    public String idType;

    private String secret;

    @BsonProperty("data")
    public byte[] data;

    @BsonProperty("t")
    public long callTimeStamp;

    @BsonProperty("skip_crypt")
    public boolean skipCrypt;

    @BsonProperty("sign")
    public byte[] sign;

    public RequestWarp(String id, ID_TYPE idType, String secret,boolean skipCrypt) {
        this.id = id;
        this.idType = idType.name();
        this.secret = secret;
        this.skipCrypt = skipCrypt;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setDataToBson(Object obj) throws Exception {
        this.data = BsonUtil.getBytes(obj);
    }

    public RequestBody getRequestBody() throws Exception {
        this.callTimeStamp = System.currentTimeMillis();
        byte[] data = this.data;
        if(false == this.skipCrypt) {
            data = CryptUtil.AesCrypt(this.secret, this.data);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(this.id.getBytes());
        baos.write(this.idType.getBytes());
        baos.write(Long.toString(this.callTimeStamp).getBytes());
        baos.write(this.data);
        baos.write(this.secret.getBytes());
        this.sign = MessageDigest.getInstance("SHA-1").digest(baos.toByteArray());
        this.data = data;
        return new RawRequestBody(BsonUtil.getBytes(this));
    }
}
