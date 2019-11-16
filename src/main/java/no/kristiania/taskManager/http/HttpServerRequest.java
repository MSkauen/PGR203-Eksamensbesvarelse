package no.kristiania.taskManager.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpServerRequest extends HttpMessage {

    private String requestTarget;

    public HttpServerRequest(InputStream inputStream) throws IOException {
        super(inputStream);

        //Get the requestTarget from the HTTP request
        try{
            requestTarget = getStartLine().split(" ")[1];
        } catch (ArrayIndexOutOfBoundsException e){
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
        Map<String, String> dataInput = new HashMap<>();

        for(String parameter : request.split("&")){
            int equalsPos = parameter.indexOf('=');
            String parameterValue = parameter.substring(equalsPos + 1);
            String parameterName = parameter.substring(0, equalsPos);
            dataInput.put(parameterName, parameterValue);
        }
        return dataInput;
    }

    public String getRequestTarget() {
        return requestTarget;
    }
}
