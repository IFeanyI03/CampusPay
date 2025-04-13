package com.example.campusPayApp.api;

import com.example.campusPayApp.utils.LocalStorageManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;


public class ValidateStudents {

    public Integer checkStudentDetails(String matricNo, String password) throws IOException {
        String body = String.format("{\"%s\": \"%s\", \"%s\": \"%s\"}", "MatricNo", matricNo, "password", password);;
        URL url = new URL("https://studentportalbeta.unilag.edu.ng/users/login");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
            dos.writeBytes(body);
        }

        int responseCode = conn.getResponseCode();

        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode" + responseCode);
        } else {
            StringBuilder responseString = new StringBuilder();
            try (BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = bf.readLine()) != null) {
                    responseString.append(line);
                }

//                System.out.println(responseString);
                Gson gson = new Gson();
                JsonObject responseObject = gson.fromJson(responseString.toString(), JsonObject.class);
//                studentObject = responseObject.get("Student").toString();
                JsonObject dataObject = gson.fromJson(responseObject.get("Data").toString(), JsonObject.class);


                JsonObject student = gson.fromJson(dataObject.get("Student").toString(), JsonObject.class);

                LocalStorageManager.saveObject("Student", student);


            }
            return responseCode;
        }
    }
}