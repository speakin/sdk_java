package mobi.speakin.sdk;

import mobi.speakin.sdk.bson.BsonProperty;
import mobi.speakin.sdk.util.BsonUtil;
import mobi.speakin.sdk.util.CryptUtil;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

/**
 * Created by panleiming on 17-5-15.
 */

public class ResponseWarp {
    private String secret;

    @BsonProperty("has_error")
    public boolean hasError;

    @BsonProperty("error")
    public ApiError error = new ApiError();

    @BsonProperty("data")
    public byte[] data = new byte[]{};

    @BsonProperty("t")
    public long excueteTime;

    @BsonProperty("sign")
    public byte[] sign;

    public static ResponseWarp newResponseWarp(String secret, byte[] content) throws Exception{
        ResponseWarp ret = new ResponseWarp();
        try {
            BsonUtil.setBytes(ret,content);
        } catch (Exception e) {
            ret.hasError = true;
            ret.error = new ApiError();
            ret.error.errorId = "common.unkwon";
            ret.error.errorDesc = "internal error";
            return ret;
        }
        ret.secret = secret;
        ret.checkSign();
        return ret;
    }

    private void checkSign() throws Exception{
        if (this.hasError && this.error.errorId.startsWith("common.")) {
            return;
        }
        String hasErrorStr = "true";
        if (false == this.hasError) {
            hasErrorStr = "false";
        }
        byte[] data = this.decryptData();
        if (this.hasError && this.error.errorId.startsWith("common.")) {
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(Long.toString(this.excueteTime).getBytes());
        baos.write(hasErrorStr.getBytes());
        baos.write(this.error.errorId.getBytes());
        baos.write(this.error.errorDesc.getBytes());
        baos.write(data);
        baos.write(this.secret.getBytes());
        try {
            byte[] sign = MessageDigest.getInstance("SHA-1").digest(baos.toByteArray());
            boolean match = false;
            if(this.sign.length == sign.length){
                for(int i=0;i<this.sign.length;i++){
                    if(this.sign[i] != sign[i]){
                        break;
                    }
                }
                match = true;
            }
            if (false == match) {
                this.hasError = true;
                this.error = new ApiError();
                this.error.errorId = "common.wrong_sign";
                this.error.errorDesc = "wrong sign";
            }
        } catch (Exception e) {
            this.hasError = true;
            this.error = new ApiError();
            this.error.errorId = "common.unkwon";
            this.error.errorDesc = "internal error";
        }
    }

    public byte[] decryptData() {
        if (this.hasError && this.error.errorId.startsWith("common.")) {
            return this.data;
        }
        try {
            return CryptUtil.AesDecrypt(this.secret, this.data);
        } catch (Exception e) {
            this.hasError = true;
            this.error = new ApiError();
            this.error.errorId = "common.unkwon";
            this.error.errorDesc = "internal error";
            return this.data;
        }
    }
}
