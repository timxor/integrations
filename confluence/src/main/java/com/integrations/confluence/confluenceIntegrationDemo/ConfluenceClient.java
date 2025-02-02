package com.integrations.confluence.confluenceIntegrationDemo;

import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

@Service
public class ConfluenceClient {

    private final OkHttpClient httpClient = new OkHttpClient();

    // defined in /resources/application.properties
    @Value("${confluence.user}")
    private String username;
    // private final String username = "siwulactim@gmail.com";

    // defined in /resources/application.properties
    // but set in ~/.zshrc to not commit api key to github ;)
    @Value("${confluence.api-token}")
    private String apiToken;
    // private final String apiToken = System.getenv("CONFLUENCE_API_TOKEN");

    // defined in /resources/application.properties
    @Value("${confluence.url}")
    private String confluenceUrl;
    // private final String confluenceUrl = "https://siwulactim.atlassian.net/wiki";

    private final String authHeader;

    public ConfluenceClient() {
        String credentials = username + ":" + apiToken;
        this.authHeader = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }

    public void getSpaces() throws IOException {
        String url = confluenceUrl + "/rest/api/space?limit=5";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", authHeader)
                .header("Accept", "application/json")
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to get spaces, code: " + response.code());
            }
            if (response.body() != null) {
                System.out.println("Spaces: " + response.body().string());
            }
        }
    }

    public void createPage(String spaceKey, String title, String content) throws IOException {
        String url = confluenceUrl + "/rest/api/content";

        JSONObject bodyJson = new JSONObject();
        bodyJson.put("type", "page");

        JSONObject spaceObj = new JSONObject();
        spaceObj.put("key", spaceKey);
        bodyJson.put("space", spaceObj);

        bodyJson.put("title", title);

        JSONObject storageObj = new JSONObject();
        storageObj.put("value", content);
        storageObj.put("representation", "storage");

        JSONObject bodyObj = new JSONObject();
        bodyObj.put("storage", storageObj);

        bodyJson.put("body", bodyObj);

        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json"),
                bodyJson.toString()
        );

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", authHeader)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .post(requestBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to create page, code: " + response.code()
                        + ", message: " + (response.body() != null ? response.body().string() : ""));
            }
            if (response.body() != null) {
                System.out.println("Created page: " + response.body().string());
            }
        }
    }
}
