package mobi.speakin.sdk;

import mobi.speakin.sdk.value.ApiObj;
import mobi.speakin.sdk.value.DATA_FORMAT;
import mobi.speakin.sdk.value.SessionApiObj;
import mobi.speakin.sdk.value.TARGET_ACTION;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;

/**
 * Created by panleiming on 17-5-24.
 */
public class SessionApiTest {
    private final static String baseUrl = "http://api3.speakin.mobi";
    private final static String appId = "speakin_test";
    private final static String appSecret = "mr1imt1etu7ryets9eoergua87h0pa4n";
    private static SessionApi sessionApi;

    @BeforeClass
    public static void init() throws Exception {
        Api api = new Api(appId, appSecret, baseUrl);
        ApiObj.StartSessionRequest req = new ApiObj.StartSessionRequest();
        req.userId = "xx";
        req.ttl = 100000;
        ApiObj.StartSessionResponse res = api.startSession(req);
        sessionApi = new SessionApi(res.sessionId, res.sessionSecret, baseUrl);
    }

    @Test
    public void test() throws Exception {
        String recordId = upload(TARGET_ACTION.REGISTER);
        SessionApiObj.RegisterRequest reqReq = new SessionApiObj.RegisterRequest();
        reqReq.recordIdList = new String[]{recordId};
        sessionApi.register(reqReq);
        System.out.println("done register");

        recordId = upload(TARGET_ACTION.VERIFY);
        SessionApiObj.VerifyRequest verifyReq = new SessionApiObj.VerifyRequest();
        verifyReq.recordId = recordId;
        SessionApiObj.VerifyResponse verifyRes = sessionApi.verify(verifyReq);
        System.out.println("done verify "+verifyRes.score+" "+verifyRes.thresholdScore);

        recordId = upload(TARGET_ACTION.IDENTITY);
        SessionApiObj.IdentityRequest identityReq = new SessionApiObj.IdentityRequest();
        identityReq.recordId = recordId;
        SessionApiObj.IdentityResponse identityRes = sessionApi.identity(identityReq);
        System.out.println("done identity "+identityRes.userIdList.length);
    }

    String upload(TARGET_ACTION action) throws Exception{
        SessionApiObj.StartRecordRequest startReq = new SessionApiObj.StartRecordRequest();
        startReq.targetAction = action.name();
        startReq.voiceBitCount = 16;
        startReq.voiceRate = 16000;
        startReq.voiceLang = "common-short";
        startReq.dataFormat = DATA_FORMAT.WAV.name();
        SessionApiObj.StartRecordResponse recordInfo = sessionApi.startRecord(startReq);
        RecordStream rs = sessionApi.openUploadRecordStream(recordInfo.recordId);
        InputStream ins = this.getClass().getClassLoader().getResourceAsStream("SI_device1_409_HM_digital_01.wav");
        while (true) {
            byte[] bs = new byte[40960];
            int readed = ins.read(bs);
            if (-1 == readed) {
                break;
            }
            if (readed < 40960) {
                byte[] bs2 = new byte[readed];
                System.arraycopy(bs, 0, bs2, 0, readed);
                bs = bs2;
            }
            rs.write(bs);
        }
        ins.close();
        rs.done();
        return recordInfo.recordId;
    }
}
