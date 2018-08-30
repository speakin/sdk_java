package mobi.speakin.sdk.voiceprint_cloud;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Request;

import mobi.speakin.sdk.voiceprint_cloud.gen.ApiClient;
import mobi.speakin.sdk.voiceprint_cloud.gen.ApiException;
import mobi.speakin.sdk.voiceprint_cloud.gen.Pair;
import mobi.speakin.sdk.voiceprint_cloud.gen.ProgressRequestBody;
import okio.Buffer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SecApiClient extends ApiClient {
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    private String appAccessKey;
    private String appSecretKey;
    private String bucketAccessKey;
    private String bucketSecretKey;

    public SecApiClient(String appAccessKey,String appSecretKey, String bucketAccessKey, String bucketSecretKey) {
         this.appAccessKey = appAccessKey;
         this.appSecretKey = appSecretKey;
         this.bucketAccessKey = bucketAccessKey;
         this.bucketSecretKey = bucketSecretKey;
    }

    @Override
    public Request buildRequest(String path, String method, List<Pair> queryParams, List<Pair> collectionQueryParams, Object body, Map<String, String> headerParams, Map<String, Object> formParams, String[] authNames, ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Request req =  super.buildRequest(path, method, queryParams, collectionQueryParams, body, headerParams, formParams, authNames, progressRequestListener);

        if(path.startsWith("/cloudapi/v1beta/storage/")) {
            HttpUrl url = HttpUrl.parse(req.url().toString());
            String encodedPath = url.encodedPath();
            String query = url.query();
            String data = String.format("%s?%s", encodedPath,query);
            try {
                byte[] sign = this.bucketSha1(data.getBytes());
                req = req.newBuilder().addHeader("Authorization", String.format("SpeakIn %s:%s",this.bucketAccessKey, new String(sign))).build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }else if (path.startsWith("/cloudapi/v1beta/voiceprint/")){
            Buffer reqBuf = new  Buffer();
            try {
                req.body().writeTo(reqBuf);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            byte[] bs = reqBuf.readByteArray();
            try {
                byte[] sign = this.apiSha1(bs);
                req = req.newBuilder().addHeader("Authorization", String.format("SpeakIn %s:%s",this.appAccessKey, new String(sign))).build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            System.out.println(new String(bs));

        }

        return req;
    }

    public void setServer(String host) {
        String basePath = this.getBasePath();
        basePath = basePath.replaceAll("__HOST__",host);
        this.setBasePath(basePath);
    }

    byte[] apiSha1(byte[] bs) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(this.appSecretKey.getBytes(), HMAC_SHA1_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);
        byte[] sign = mac.doFinal(bs);
        byte[] encodeSign = java.util.Base64.getUrlEncoder().encode(sign);
        return encodeSign;
    }

    byte[] bucketSha1(byte[] bs) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(this.bucketSecretKey.getBytes(), HMAC_SHA1_ALGORITHM);
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        mac.init(signingKey);
        byte[] sign = mac.doFinal(bs);
        byte[] encodeSign = java.util.Base64.getUrlEncoder().encode(sign);
        return encodeSign;
    }
}
