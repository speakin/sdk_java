package mobi.speakin.sdk;

import mobi.speakin.sdk.value.UserApiObj;

/**
 * Created by panleiming on 17-5-24.
 */
public class UserApi extends BaseApi {
    UserApi(String appId, String appSecret, String baseUrl) {
        super(appId, RequestWarp.ID_TYPE.APP_ID, appSecret, baseUrl);
    }

    public UserApiObj.SetAppUserResponse setAppUser(UserApiObj.SetAppUserRequest reqData) throws ApiError, Exception {
        return setAppUser(reqData, "");
    }

    public UserApiObj.SetAppUserResponse setAppUser(UserApiObj.SetAppUserRequest reqData, String traceId) throws ApiError, Exception {
        String url = String.format("%s/v1/user/set?traceId=%s", this.baseUrl, traceId);
        return this.callApi(url, reqData, UserApiObj.SetAppUserResponse.class);
    }

    public UserApiObj.GetAppUserResponse getAppUser(UserApiObj.GetAppUserRequest reqData) throws ApiError, Exception {
        return getAppUser(reqData, "");
    }

    public UserApiObj.GetAppUserResponse getAppUser(UserApiObj.GetAppUserRequest reqData, String traceId) throws ApiError, Exception {
        String url = String.format("%s/v1/user/get?traceId=%s", this.baseUrl, traceId);
        return this.callApi(url, reqData, UserApiObj.GetAppUserResponse.class);
    }

    public UserApiObj.AddChildAppUserResponse addChildAppUser(UserApiObj.AddChildAppUserRequest reqData) throws ApiError, Exception {
        return addChildAppUser(reqData, "");
    }

    public UserApiObj.AddChildAppUserResponse addChildAppUser(UserApiObj.AddChildAppUserRequest reqData, String traceId) throws ApiError, Exception {
        String url = String.format("%s/v1/user/add_child?traceId=%s", this.baseUrl, traceId);
        return this.callApi(url, reqData, UserApiObj.AddChildAppUserResponse.class);
    }

    public UserApiObj.RemoveChildAppUserResponse removeChildAppUser(UserApiObj.RemoveChildAppUserRequest reqData) throws ApiError, Exception {
        return removeChildAppUser(reqData, "");
    }

    public UserApiObj.RemoveChildAppUserResponse removeChildAppUser(UserApiObj.RemoveChildAppUserRequest reqData, String traceId) throws ApiError, Exception {
        String url = String.format("%s/v1/user/remove_child?traceId=%s", this.baseUrl, traceId);
        return this.callApi(url, reqData, UserApiObj.RemoveChildAppUserResponse.class);
    }

    public UserApiObj.GetChildAppUserCountResponse getChildAppUserCount(UserApiObj.GetChildAppUserCountRequest reqData) throws ApiError, Exception {
        return getChildAppUserCount(reqData, "");
    }

    public UserApiObj.GetChildAppUserCountResponse getChildAppUserCount(UserApiObj.GetChildAppUserCountRequest reqData, String traceId) throws ApiError, Exception {
        String url = String.format("%s/v1/user/get_child_count?traceId=%s", this.baseUrl, traceId);
        return this.callApi(url, reqData, UserApiObj.GetChildAppUserCountResponse.class);
    }

    public UserApiObj.ListChildAppUserResponse listChildAppUser(UserApiObj.ListChildAppUserRequest reqData) throws ApiError, Exception {
        return listChildAppUser(reqData, "");
    }

    public UserApiObj.ListChildAppUserResponse listChildAppUser(UserApiObj.ListChildAppUserRequest reqData, String traceId) throws ApiError, Exception {
        String url = String.format("%s/v1/user/list_child?traceId=%s", this.baseUrl, traceId);
        return this.callApi(url, reqData, UserApiObj.ListChildAppUserResponse.class);
    }

    public UserApiObj.ContainChildAppUserResponse containChildAppUser(UserApiObj.ContainChildAppUserRequest reqData) throws ApiError, Exception {
        return containChildAppUser(reqData, "");
    }

    public UserApiObj.ContainChildAppUserResponse containChildAppUser(UserApiObj.ContainChildAppUserRequest reqData, String traceId) throws ApiError, Exception {
        String url = String.format("%s/v1/user/contain_child?traceId=%s", this.baseUrl, traceId);
        return this.callApi(url, reqData, UserApiObj.ContainChildAppUserResponse.class);
    }
}
