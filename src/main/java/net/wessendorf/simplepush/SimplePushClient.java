/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.wessendorf.simplepush;

import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * A SimplePush Java Client to send messages to a given SimplePush Server,
 * like the AeroGear SimplePush Server or Mozilla's Push Network.
 */
public class SimplePushClient {

    /**
     * Performs an HTTP-PUT request against the give URL, identifying a valid update endpoint of a SimplePush Server.
     *
     * @param url update endpoint
     * @param version the version string, like <pre>version=123</pre>
     */
    public int put(URL url, String version) throws IOException {

        if (url == null || version == null || version.isEmpty()) {
            throw new IllegalArgumentException("arguments cannot be null");
        }

        byte[] bytes = version.getBytes(StandardCharsets.UTF_8);

        int statusCode;

        final OkHttpClient client = new OkHttpClient();
        final HttpURLConnection conn =  client.open(url);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setFixedLengthStreamingMode(bytes.length);
        conn.setRequestProperty("Accept", "application/x-www-form-urlencoded");
        conn.setRequestMethod("PUT");
        OutputStream out = null;

        try {
            out = conn.getOutputStream();
            out.write(bytes);
            out.close();

            // stash the status code
            statusCode = conn.getResponseCode();

        } finally {
            // in case something blows up, while writing
            // the payload, we wanna close the stream:
            if (out != null) {
                out.close();
            }
        }

        return statusCode;
    }

}
