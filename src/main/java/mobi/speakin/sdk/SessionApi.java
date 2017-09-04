package mobi.speakin.sdk;

import mobi.speakin.sdk.value.SessionApiObj;

/**
 * Created by panleiming on 17-5-15.
 */
public class SessionApi extends BaseApi {
    public SessionApi(String sessionId, String sessionSecret, String baseUrl) {
        super(sessionId, RequestWarp.ID_TYPE.SESSION_ID, sessionSecret, baseUrl);
    }

    public SessionApiObj.StartRecordResponse startRecord(SessionApiObj.StartRecordRequest reqData) throws ApiError, Exception {
        return startRecord(reqData, "");
    }

    public SessionApiObj.StartRecordResponse startRecord(SessionApiObj.StartRecordRequest reqData, String traceId) throws ApiError, Exception {
        String url = String.format("%s/v1/session/record/start?traceId=%s", this.baseUrl, traceId);
        return this.callApi(url, reqData, SessionApiObj.StartRecordResponse.class);
    }

    public RecordStream openUploadRecordStream(String recordId) {
        return new RecordStream(this, recordId, "");
    }

    public RecordStream openUploadRecordStream(String recordId, String traceId) {
        return new RecordStream(this, recordId, traceId);
    }

    SessionApiObj.UploadRecordPartResponse uploadRecordPart(SessionApiObj.UploadRecordPartRequest reqData, String traceId) throws ApiError, Exception {
        String url = String.format("%s/v1/session/record/upload_part?traceId=%s", this.baseUrl, traceId);
        return this.callApi(url, reqData, SessionApiObj.UploadRecordPartResponse.class);
    }

    SessionApiObj.UploadRecordDoneResponse uploadRecordDone(SessionApiObj.UploadRecordDoneRequest reqData, String traceId) throws ApiError, Exception {
        String url = String.format("%s/v1/session/record/done?traceId=%s", this.baseUrl, traceId);
        return this.callApi(url, reqData, SessionApiObj.UploadRecordDoneResponse.class);
    }


    SessionApiObj.UploadRecordCancelResponse uploadRecordCancel(SessionApiObj.UploadRecordCancelRequest reqData, String traceId) throws ApiError, Exception {
        String url = String.format("%s/v1/session/record/cancel?traceId=%s", this.baseUrl, traceId);
        return this.callApi(url, reqData, SessionApiObj.UploadRecordCancelResponse.class);
    }

    public SessionApiObj.RegisterResponse register(SessionApiObj.RegisterRequest reqData) throws ApiError, Exception {
        return register(reqData, "");
    }

    public SessionApiObj.RegisterResponse register(SessionApiObj.RegisterRequest reqData, String traceId) throws ApiError, Exception {
        String url = String.format("%s/v1/session/register?traceId=%s", this.baseUrl, traceId);
        return this.callApi(url, reqData, SessionApiObj.RegisterResponse.class);
    }

    public SessionApiObj.VerifyResponse verify(SessionApiObj.VerifyRequest reqData) throws ApiError, Exception {
        return verify(reqData, "");
    }

    public SessionApiObj.VerifyResponse verify(SessionApiObj.VerifyRequest reqData, String traceId) throws ApiError, Exception {
        String url = String.format("%s/v1/session/verify?traceId=%s", this.baseUrl, traceId);
        return this.callApi(url, reqData, SessionApiObj.VerifyResponse.class);
    }

    public SessionApiObj.IdentityResponse identity(SessionApiObj.IdentityRequest reqData) throws ApiError, Exception {
        return identity(reqData, "");
    }

    public SessionApiObj.IdentityResponse identity(SessionApiObj.IdentityRequest reqData, String traceId) throws ApiError, Exception {
        String url = String.format("%s/v1/session/identity?traceId=%s", this.baseUrl, traceId);
        return this.callApi(url, reqData, SessionApiObj.IdentityResponse.class);
    }
}
