package mobi.speakin.sdk;

import mobi.speakin.sdk.value.USER_TYPE;
import mobi.speakin.sdk.value.UserApiObj;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by panleiming on 17-5-24.
 */
public class UserApiTest {
    private final static String baseUrl = "http://api3.speakin.mobi";
    private final static String appId = "speakin_test";
    private final static String appSecret = "mr1imt1etu7ryets9eoergua87h0pa4n";
    private static UserApi userApi;

    @BeforeClass
    public static void init() throws Exception {
        Api api = new Api(appId, appSecret, baseUrl);
        userApi = api.getUserApi();
    }

    @Test
    public void test() throws Exception {
        this.testSet();
        this.testGet();
        this.testAddChild();
        this.testContainChild();
        this.testGetChildCount();
        this.testListChild();
        this.testRemoveChild();
    }

    private void testContainChild() throws Exception {
        UserApiObj.ContainChildAppUserRequest req = new UserApiObj.ContainChildAppUserRequest();
        req.userId = "xx";
        req.childUserId = "yy";
        UserApiObj.ContainChildAppUserResponse res = userApi.containChildAppUser(req);
        System.out.println("contain result "+res.contain);
    }

    private void testRemoveChild() throws Exception {
        UserApiObj.RemoveChildAppUserRequest req = new UserApiObj.RemoveChildAppUserRequest();
        req.userId = "xx";
        req.childUserId = "yy";
        userApi.removeChildAppUser(req);
    }

    private void testListChild() throws Exception {
        UserApiObj.ListChildAppUserRequest req = new UserApiObj.ListChildAppUserRequest();
        req.userId = "xx";
        req.offset = 0;
        req.limit = 10;
        UserApiObj.ListChildAppUserResponse res = userApi.listChildAppUser(req);
        System.out.println("child list "+res.childUserIdList.length);
    }

    private void testGetChildCount() throws Exception {
        UserApiObj.GetChildAppUserCountRequest req = new UserApiObj.GetChildAppUserCountRequest();
        req.userId = "xx";
        UserApiObj.GetChildAppUserCountResponse res = userApi.getChildAppUserCount(req);
        System.out.println("count "+res.count);
    }

    private void testAddChild() throws Exception {
        UserApiObj.AddChildAppUserRequest req = new UserApiObj.AddChildAppUserRequest();
        req.userId = "xx";
        req.childUserId = "yy";
        userApi.addChildAppUser(req);
    }

    private void testGet() throws Exception {
        UserApiObj.GetAppUserRequest getUserReq = new UserApiObj.GetAppUserRequest();
        getUserReq.userId = "xx";
        userApi.getAppUser(getUserReq);
    }

    private void testSet() throws Exception {
        UserApiObj.SetAppUserRequest setUserReq = new UserApiObj.SetAppUserRequest();
        setUserReq.userId = "xx";
        setUserReq.userType = USER_TYPE.PEOPLE.name();
        setUserReq.valid = true;
        userApi.setAppUser(setUserReq);
        setUserReq.userId = "yy";
        userApi.setAppUser(setUserReq);
    }
}
