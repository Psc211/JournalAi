package com.example.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequestOpenAi {
    private String OpenAiAPIUrl = "https://api.openai.com/v1/chat/completions";
    //INSERT YOUR OWN API KEY FOR USE
    private String apiKey = "";
    public List<ActivitateSkill> chatGPT(String message) {
        String model = "gpt-3.5-turbo";
        List<ActivitateSkill>lista=new ArrayList<>();
        try {
            // Create the HTTP POST request
            URL obj = new URL(OpenAiAPIUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            // Build the request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.trim()+"\n");
            }
            br.close();
            sb.toString().replaceAll("/", "");
            String jsonResponse=sb.toString();
            try{
                JSONObject responseJson = new JSONObject(jsonResponse);
                JSONArray assistantMessageContent = new JSONObject(responseJson.getJSONArray("choices").getJSONObject(0)
                        .getJSONObject("message").getString("content")).getJSONArray("activities");
                for(int i=0;i<assistantMessageContent.length();i++){
                    JSONObject activitate=assistantMessageContent.getJSONObject(i);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        ActivitateSkill activitateSkill=new ActivitateSkill(LocalDate.now().toString(), activitate.getString("skill"), activitate.getString("hours"));
                        lista.add(activitateSkill);
                    }
                }
            }catch (JSONException e){

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            return lista;
        }
    }
    public String requestMessage(String message) {
        String model = "gpt-3.5-turbo";
        try {
            URL obj = new URL(OpenAiAPIUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine.trim()+"\n");
            }
            in.close();
            response.toString().replaceAll("\\n", "\n");
            return extractContentFromResponse(response.toString());



        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }


    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content")+11; // Marker for where the content starts.
        int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
        return response.substring(startMarker, endMarker); // Returns the substring containing only the response.
    }

}
