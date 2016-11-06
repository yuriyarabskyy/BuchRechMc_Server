package com.fortum.nokid;

import com.fortum.nokid.entities.SlackMessage;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

public class Utils {

    public static void sendErrorTextToSlack(String errorText) {
        RestTemplate restTemplate = new RestTemplate();
        String webHookUrl = "https://hooks.slack.com/services/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";

        HttpEntity<SlackMessage> request = new HttpEntity<>(new SlackMessage("Server", ":skull:", errorText));
        restTemplate.postForObject(webHookUrl, request, String.class);
    }

}
