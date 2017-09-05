package mobi.speakin.sdk;

import mobi.speakin.sdk.value.SessionApiObj;

/**
 * Created by panleiming on 17-5-17.
 */
public class RecordStream {
    private SessionApi api;
    private String recordId;
    private int indexId;
    private String traceId;

    RecordStream(SessionApi api, String recordId,String traceId) {
        this.api = api;
        this.recordId = recordId;
        this.traceId = traceId;
    }

    public void write(byte[] data) throws ApiError, Exception {
        SessionApiObj.UploadRecordPartRequest reqData = new SessionApiObj.UploadRecordPartRequest();
        indexId += 1;
        reqData.recordId = this.recordId;
        reqData.indexId = this.indexId;
        reqData.data = data; //FIXME new String(Base64.encodeBase64(data));
        this.api.uploadRecordPart(reqData,traceId);
    }

    public void done() throws ApiError, Exception {
        SessionApiObj.UploadRecordDoneRequest reqData = new SessionApiObj.UploadRecordDoneRequest();
        reqData.recordId = this.recordId;
        this.api.uploadRecordDone(reqData,traceId);
    }

    public void cancel() throws ApiError, Exception {
        SessionApiObj.UploadRecordCancelRequest reqData = new SessionApiObj.UploadRecordCancelRequest();
        reqData.recordId = this.recordId;
        this.api.uploadRecordCancel(reqData,traceId);
    }

    public String getRecordId() {
        return this.recordId;
    }
}
