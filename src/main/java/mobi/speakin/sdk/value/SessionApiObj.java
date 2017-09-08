package mobi.speakin.sdk.value;

import mobi.speakin.sdk.bson.BsonProperty;

import java.util.Arrays;

/**
 * Created by panleiming on 17-5-15.
 */
public class SessionApiObj {

    public static class StartRecordRequest {
        @BsonProperty("gen_text")
        public boolean genText;

        @BsonProperty("voice_bit_count")
        public int voiceBitCount;

        @BsonProperty("voice_rate")
        public int voiceRate;

        @BsonProperty("voice_lang")
        public String voiceLang;

        @BsonProperty("data_format")
        public String dataFormat;

        @BsonProperty("target_action")
        public String targetAction;
    }

    public static class StartRecordResponse {
        @BsonProperty("record_id")
        public String recordId;

        @BsonProperty("text")
        public String text;
    }

    public static class UploadRecordPartRequest {
        @BsonProperty("record_id")
        public String recordId;

        @BsonProperty("index_id")
        public int indexId;

        @BsonProperty("data")
        public byte[] data;
    }

    public static class UploadRecordPartResponse {

    }


    public static class UploadRecordDoneRequest {
        @BsonProperty("record_id")
        public String recordId;
    }


    public static class UploadRecordDoneResponse {

    }


    public static class UploadRecordCancelRequest {
        @BsonProperty("record_id")
        public String recordId;
    }

    public static class UploadRecordCancelResponse {

    }


    public static class RegisterRequest {
        @BsonProperty("record_id_list")
        public String[] recordIdList;
    }

    public static class RegisterResponse {

    }

    public static class VerifyRequest {
        @BsonProperty("record_id")
        public String recordId;
    }

    public static class VerifyResponse {
        @BsonProperty("result")
        public boolean result;

        @BsonProperty("score")
        public double score;

        @BsonProperty("threshold_score")
        public double thresholdScore;

        @BsonProperty("dyanmic_cmp_score")
        public double dynamicCmpScore;
    }


    public static class IdentityRequest {
        @BsonProperty("record_id")
        public String recordId;
    }


    public static class IdentityResponse {
        @BsonProperty("user_id_list")
        public String[] userIdList;
    }
}
