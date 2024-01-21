package cc.newex.dax.perpetual.openapi.support.aop.interceptor;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.apache.commons.io.IOUtils.toByteArray;

/**
 * @author newex-team
 * @date 2018-10-31 16:16:36
 */
public class CustomServletRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;

    public CustomServletRequestWrapper(final HttpServletRequest request) throws IOException {
        super(request);
        this.body = toByteArray(super.getInputStream());
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new RequestBodyCachingInputStream(this.body);
    }

    private static class RequestBodyCachingInputStream extends ServletInputStream {
        private byte[] body;
        private int lastIndexRetrieved = -1;
        private ReadListener listener;

        public RequestBodyCachingInputStream(final byte[] body) {
            this.body = body;
        }

        @Override
        public int read() throws IOException {
            if (this.isFinished()) {
                return -1;
            }
            final int i = this.body[this.lastIndexRetrieved + 1];
            this.lastIndexRetrieved++;
            if (this.isFinished() && this.listener != null) {
                try {
                    this.listener.onAllDataRead();
                } catch (final IOException e) {
                    this.listener.onError(e);
                    throw e;
                }
            }
            return i;
        }

        @Override
        public boolean isFinished() {
            return this.lastIndexRetrieved == this.body.length - 1;
        }

        @Override
        public boolean isReady() {
            return this.isFinished();
        }

        @Override
        public void setReadListener(final ReadListener listener) {
            if (listener == null) {
                throw new IllegalArgumentException("listener cann not be null");
            }

            if (this.listener != null) {
                throw new IllegalArgumentException("listener has been set");
            }

            this.listener = listener;

            try {
                listener.onAllDataRead();
            } catch (final IOException e) {
                listener.onError(e);
            }

        }

        @Override
        public int available() throws IOException {
            return this.body.length - this.lastIndexRetrieved - 1;
        }

        @Override
        public void close() throws IOException {
            this.lastIndexRetrieved = this.body.length - 1;
            this.body = null;
        }
    }
}