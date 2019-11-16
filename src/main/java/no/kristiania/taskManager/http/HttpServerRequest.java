package no.kristiania.taskManager.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpServerRequest extends HttpMessage {

    private String requestTarget;

    public HttpServerRequest(InputStream inputStream) throws IOException {
        super(inputStream);
        requestTarget = getStartLine().split(" ")[1];//OK
    }

    public Map<String, String> parseRequestParameters() {
        Map<String, String> requestParameters = new HashMap<>();

        int questionPos = requestTarget.indexOf('?');
        if (questionPos != -1) {
            String query = requestTarget.substring(questionPos + 1);
            requestParameters = parametersToMap(query);
        }
        return requestParameters;
    }


    public Map<String, String> parseRequestBody(String request){
        Map<String, String> dataInput = parametersToMap(replaceSpecialCharacters(request));
        return dataInput;
    }

    private String replaceSpecialCharacters(String request) {
        String replaceString;
        replaceString = request.replaceAll("\\+", " ");
        replaceString = replaceString.replaceAll("%C3%A6", "æ");
        replaceString = replaceString.replaceAll("%C3%98", "ø");
        replaceString = replaceString.replaceAll("%C3%A5", "å");

        return replaceString;
    }

    private Map<String, String> parametersToMap(String request) {
        Map<String, String> dataInput = new HashMap<>();;

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
