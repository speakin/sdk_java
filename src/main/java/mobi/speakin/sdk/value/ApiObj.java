package mobi.speakin.sdk.value;


import mobi.speakin.sdk.bson.BsonProperty;

/**
 * Created by panleiming on 17-5-15.
 */
public class ApiObj {

    public static class StartSessionRequest {
        @BsonProperty("user_id")
        public String userId;

        @BsonProperty("can_register")
        public boolean canRegister = true;

        @BsonProperty("can_verify")
        public boolean canVerify = true;

        @BsonProperty("can_identity")
        public boolean canIdentity = true;

        @BsonProperty("ttl")
        public int ttl;
    }

    public static class StartSessionResponse {
        @BsonProperty("session_id")
        public String sessionId;

        @BsonProperty("expire_time_stamp")
        public long expireTimeStamp;

        @BsonProperty("session_secret")
        public String sessionSecret;
    }

    public static class ListModuleRequest {

    }

    public static class Module {
        @BsonProperty("voice_bit_count")
        public int voiceBitCount;

        @BsonProperty("voice_rate")
        public int voiceRate;

        @BsonProperty("voice_lang")
        public String voiceLang;
    }

    public static class ListModuleRespone {
        @BsonProperty("module_list")
        public Module[] moduleList;
    }


    public static class QueryTraceRequest {
        @BsonProperty("offset")
        public int offset;
        @BsonProperty("limit")
        public int limit;
    }

    public static class QueryTraceResponse {
        @BsonProperty("total_count")
        public int totalCount;

        @BsonProperty("log_content")
        public String logContent;
    }


}
