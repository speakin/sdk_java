package mobi.speakin.sdk;

import mobi.speakin.sdk.value.ApiObj;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by panleiming on 17-5-24.
 */
public class ApiTest {
    private final static String baseUrl = "http://api3.speakin.mobi";
    private final static String appId = "speakin_test";
    private final static String appSecret = "mr1imt1etu7ryets9eoergua87h0pa4n";
    private static Api api;

    @BeforeClass
    public static void init() throws Exception {
        api = new Api(appId, appSecret, baseUrl);
    }

    @Test
    public void testListModule() throws Exception {
        ApiObj.ListModuleRequest req = new ApiObj.ListModuleRequest();
        ApiObj.ListModuleRespone response = api.listModule(req);
        for(ApiObj.Module module:response.moduleList){
            System.out.println(module.voiceLang);
        }
        System.out.println(response.moduleList.length);
    }

    @Test
    public void testStartSession() throws Exception {
        ApiObj.StartSessionRequest req = new ApiObj.StartSessionRequest();
        req.userId = "xx";
        req.ttl = 10000;
        ApiObj.StartSessionResponse response = api.startSession(req);
        System.out.println(response.sessionId);
    }
}
