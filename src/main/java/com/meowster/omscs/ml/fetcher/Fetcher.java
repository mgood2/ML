package com.meowster.omscs.ml.fetcher;

import com.google.common.base.MoreObjects;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.meowster.omscs.ml.fetcher.Utils.print;

/**
 * Object to fetch data from a web service.
 */
public class Fetcher {

    private static final String HTTP_ = "http://";

    private final String url;
    private final StringBuilder content = new StringBuilder();

    Fetcher(String url) {
        this.url = HTTP_ + url;
    }

    public void fetch(HttpClient httpClient) {
        HttpGet request = new HttpGet(this.url);

        try {
            HttpResponse response = httpClient.execute(request);
            checkResponse(response);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            response.getEntity().getContent()
                    )
            );

            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InputStream inputStream() {
        return new ByteArrayInputStream(content.toString().getBytes());
    }

    private void checkResponse(HttpResponse response) {
        int status = response.getStatusLine().getStatusCode();
        if (status != HttpStatus.SC_OK) {
            print("BAD HTTP Response: %d", status);
            throw new RuntimeException("HTTP Failed: " + status);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("url", url)
                .toString();
    }
}
