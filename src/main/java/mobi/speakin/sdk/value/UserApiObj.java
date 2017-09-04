package mobi.speakin.sdk.value;


import mobi.speakin.sdk.bson.BsonProperty;

/**
 * Created by panleiming on 17-5-24.
 */
public class UserApiObj {

    public static class SetAppUserRequest {
        /*
         * 用户ID,需要保证在单个APPID内唯一
         */
        @BsonProperty("user_id")
        public String userId = "";

        /*
         * 用户类型，目前值为DEV,PEOPLE,VIRTUAL
         */
        @BsonProperty("user_type")
        public String userType = "";

        /*
         * 是否有效
         */
        @BsonProperty("valid")
        public boolean valid = true;

        /*
         * 是否可以访问APP内所有用户
         */
        @BsonProperty("access_all_app_user")
        public boolean accessAllAppUser = false;
    }


    public static class SetAppUserResponse {

    }


    public static class GetAppUserRequest {
        /*
        * 用户ID,需要保证在单个APPID内唯一
        */
        @BsonProperty("user_id")
        public String userId = "";
    }

    public static class GetAppUserResponse {
        /*
        * 用户ID,需要保证在单个APPID内唯一
        */
        @BsonProperty("user_id")
        public String userId = "";

        /*
         * 用户类型，目前值为DEV,PEOPLE,VIRTUAL
         */
        @BsonProperty("user_type")
        public String userType = "";

        /*
         * 是否有效
         */
        @BsonProperty("valid")
        public boolean valid = true;

        /*
         * 是否可以访问APP内所有用户
         */
        @BsonProperty("access_all_app_user")
        public boolean accessAllAppUser = false;
    }


    public static class AddChildAppUserRequest {
        /*
         * 用户ID,需要保证在单个APPID内唯一
         */
        @BsonProperty("user_id")
        public String userId = "";

        @BsonProperty("child_user_id")
        public String childUserId = "";
    }

    public static class AddChildAppUserResponse {

    }


    public static class RemoveChildAppUserRequest {
        /*
         * 用户ID,需要保证在单个APPID内唯一
         */
        @BsonProperty("user_id")
        public String userId = "";

        @BsonProperty("child_user_id")
        public String childUserId = "";
    }

    public static class RemoveChildAppUserResponse {

    }

    public static class GetChildAppUserCountRequest {
        /*
         * 用户ID,需要保证在单个APPID内唯一
         */
        @BsonProperty("user_id")
        public String userId = "";
    }


    public static class GetChildAppUserCountResponse {
        @BsonProperty("count")
        public int count;
    }

    public static class ListChildAppUserRequest {
        /*
         * 用户ID,需要保证在单个APPID内唯一
         */
        @BsonProperty("user_id")
        public String userId = "";
        /*
         * 偏移
         */
        @BsonProperty("offset")
        public int offset = 0;
        /*
         * 数量限制
         */
        @BsonProperty("limit")
        public int limit = 10;
    }

    public static class ListChildAppUserResponse {
        /*
         * child用户Id列表
         */
        @BsonProperty("child_user_id_list")
        public String[] childUserIdList = new String[]{};
    }


    public static class ContainChildAppUserRequest {
        /*
         * 用户ID,需要保证在单个APPID内唯一
         */
        @BsonProperty("user_id")
        public String userId = "";

        @BsonProperty("child_user_id")
        public String childUserId = "";
    }


    public static class ContainChildAppUserResponse {
        @BsonProperty("contain")
        public boolean contain;

    }
}
