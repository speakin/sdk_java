package mobi.speakin.sdk.okhttp3;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * Created by panleiming on 17-7-21.
 */
public class RawRequestBody extends RequestBody {
    private static final MediaType CONTENT_TYPE = MediaType.parse("application/bson");

    private byte[] data;

    public RawRequestBody(byte[] data) {
        this.data = data;
    }

    private RawRequestBody() {

    }

    @Nullable
    public MediaType contentType() {
        return CONTENT_TYPE;
    }

    public void writeTo(BufferedSink bufferedSink) throws IOException {
        bufferedSink.buffer().write(this.data);
    }

    @Override
    public long contentLength() throws IOException {
        return this.data.length;
    }
}
