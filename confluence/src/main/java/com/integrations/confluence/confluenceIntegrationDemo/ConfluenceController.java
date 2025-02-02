package com.integrations.confluence.confluenceIntegrationDemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
public class ConfluenceController {

    private final ConfluenceClient confluenceClient;

    public ConfluenceController(ConfluenceClient confluenceClient) {
        this.confluenceClient = confluenceClient;
    }

    @GetMapping("/spaces")
    public String getSpaces() throws IOException {
        confluenceClient.getSpaces();
        return "Fetched spaces, check console for output";
    }

    @PostMapping("/createPage/{spaceKey}/{title}")
    public String createPage(@PathVariable String spaceKey,
                             @PathVariable String title) throws IOException {
        // In a real app, you'd probably pass content via request body or a param
        confluenceClient.createPage(spaceKey, title, "<p>Hello from Spring Boot!</p>");
        return "Page created in space " + spaceKey;
    }
}
