package mobi.speakin.sdk;

import mobi.speakin.sdk.util.BsonUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by panleiming on 17-5-24.
 */
abstract class BaseApi {
    protected String id;
    protected RequestWarp.ID_TYPE idType;
    protected String secret;
    protected String baseUrl;
    protected boolean skipCrypt;
    private OkHttpClient client = new OkHttpClient();

    public BaseApi(String id, RequestWarp.ID_TYPE idType,String secret, String baseUrl) {
        this(id,idType,secret,baseUrl,false);
    }

    public BaseApi(String id, RequestWarp.ID_TYPE idType,String secret, String baseUrl,boolean skipCrypt) {
        this.id = id;
        this.idType = idType;
        this.secret = secret;
        this.baseUrl = baseUrl;
        this.skipCrypt = skipCrypt;
    }

    protected <REQ, RES> RES callApi(String url, REQ reqData, Class<RES> clazz) throws ApiError, Exception {
        RequestWarp reqWarp = new RequestWarp(this.id, this.idType, this.secret,this.skipCrypt);
        reqWarp.setDataToBson(reqData);
        Request req = new Request.Builder().url(url).post(reqWarp.getRequestBody()).build();
        Response res = null;
        try {
            res = client.newCall(req).execute();
            ResponseWarp responseWarp = ResponseWarp.newResponseWarp(this.secret, res.body().bytes());
            if (responseWarp.hasError) {
                throw responseWarp.error;
            }
            byte[] bsonData = responseWarp.decryptData();
            if (responseWarp.hasError) {
                throw responseWarp.error;
            }
            RES resp = clazz.newInstance();
            BsonUtil.setBytes(resp,bsonData);
            return resp;
        } finally {
            if(null != res) {
                res.close();
            }
        }
    }
}
