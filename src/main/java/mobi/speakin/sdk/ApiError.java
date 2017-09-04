package mobi.speakin.sdk;

import mobi.speakin.sdk.bson.BsonProperty;

/**
 * Created by panleiming on 17-5-15.
 */

public class ApiError extends Exception {
    @BsonProperty("id")
    public String errorId;

    @BsonProperty("desc")
    public String errorDesc;

    @Override
    public String toString() {
        return "ApiError{" +
                "errorId='" + errorId + '\'' +
                ", errorDesc='" + errorDesc + '\'' +
                '}';
    }
}
