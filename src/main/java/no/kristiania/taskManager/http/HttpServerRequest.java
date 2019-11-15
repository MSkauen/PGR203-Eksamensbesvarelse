package no.kristiania.taskManager.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpServerRequest extends HttpMessage {



    public HttpServerRequest(InputStream inputStream) throws IOException {
        super(inputStream);
    }

    public Map<String, String> parseRequestParameters(String requestTarget) {
        Map<String, String> requestParameters = new HashMap<>();

        int questionPos = requestTarget.indexOf('?');
        if (questionPos != -1) {

            String query = requestTarget.substring(questionPos + 1);

            for (String parameter : query.split("&")) {
                int equalsPos = parameter.indexOf('=');
                String parameterValue = parameter.substring(equalsPos + 1);
                String parameterName = parameter.substring(0, equalsPos);
                requestParameters.put(parameterName, parameterValue);
            }
        }
        return requestParameters;
    }


    public Map<String, String> parsePostRequestBody(String body){

        Map<String, String> dataInput = new HashMap<>();


        //EXTRACT THIS TO METHOD
        if(body.contains("&")){
            for(String parameter : body.split("&")){
                int equalsPos = parameter.indexOf('=');
                String parameterValue = parameter.substring(equalsPos + 1);
                String parameterName = parameter.substring(0, equalsPos);
                dataInput.put(parameterName, parameterValue);
            }
        } else {
            int equalsPos = body.indexOf('=');
            String parameterValue = body.substring(equalsPos + 1);
            String parameterName = body.substring(0, equalsPos);
            dataInput.put(parameterName, parameterValue);
        }

        return dataInput;
    }
}
