package mobi.speakin.sdk;

import mobi.speakin.sdk.value.ApiObj;

/**
 * Created by panleiming on 17-5-15.
 */
public class Api extends BaseApi {

    public Api(String appId, String appSecret, String baseUrl) {
        super(appId, RequestWarp.ID_TYPE.APP_ID, appSecret, baseUrl);
    }

    public UserApi getUserApi() {
        return new UserApi(this.id, this.secret, this.baseUrl);
    }

    public ApiObj.ListModuleRespone listModule(ApiObj.ListModuleRequest reqData) throws ApiError, Exception {
        return listModule(reqData, "");
    }

    public ApiObj.ListModuleRespone listModule(ApiObj.ListModuleRequest reqData, String traceId) throws ApiError, Exception {
        String url = String.format("%s/v1/list_module?traceId=%s", this.baseUrl, traceId);
        return this.callApi(url, reqData, ApiObj.ListModuleRespone.class);
    }

    public ApiObj.StartSessionResponse startSession(ApiObj.StartSessionRequest reqData) throws ApiError, Exception {
        return startSession(reqData, "");
    }

    public ApiObj.StartSessionResponse startSession(ApiObj.StartSessionRequest reqData, String traceId) throws ApiError, Exception {
        String url = String.format("%s/v1/start_session?traceId=%s", this.baseUrl, traceId);
        return this.callApi(url, reqData, ApiObj.StartSessionResponse.class);
    }

    public ApiObj.QueryTraceResponse queryTrace(ApiObj.QueryTraceRequest reqData,String traceId) throws ApiError, Exception {
        String url = String.format("%s/v1/query_trace?traceId=%s", this.baseUrl,traceId);
        return this.callApi(url, reqData, ApiObj.QueryTraceResponse.class);
    }

}
