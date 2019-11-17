package no.kristiania.taskManager.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest extends HttpMessage {

    private String requestTarget;

    public HttpRequest(InputStream inputStream) throws IOException {
        super(inputStream);

        //Get the requestTarget from the HTTP request
        try {
            requestTarget = getStartLine().split(" ")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            //ADD LOGGER
        }
    }

    //ADD MORE explanations to this part
    public Map<String, String> parseRequestParameters() {
        Map<String, String> requestParameters = new HashMap<>();

        int questionPos = requestTarget.indexOf('?');
        if (questionPos != -1) {
            String query = requestTarget.substring(questionPos + 1);
            requestParameters = parametersToMap(query);
        }
        return requestParameters;
    }

    public Map<String, String> parseRequestBody(String request) {
        String decodedRequest = URLDecoder.decode(request, StandardCharsets.UTF_8);
        return parametersToMap(decodedRequest);
    }

    private Map<String, String> parametersToMap(String request) {
        for (String parameter : request.split("&")) {
            parseParameter('=', parameter);
        }
        return headers;
    }

    public String getRequestTarget() {
        return requestTarget;
    }
}
