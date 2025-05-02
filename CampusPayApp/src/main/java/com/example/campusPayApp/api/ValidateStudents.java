package com.example.campusPayApp.api;

import com.example.campusPayApp.utils.LocalStorageManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;


public class ValidateStudents {

    public String checkStudentDetails(String matricNo, String password) throws Exception {
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

        String responseCode = String.valueOf(conn.getResponseCode());

        if (!responseCode.equals("200")) {
            throw new RuntimeException("HttpResponseCode" + responseCode);
        } else {
//            Profile profiles = new Profile();
//            profiles.get();
//            String data = profiles.get();
//            System.out.println(data);
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
                int hashPassword = password.hashCode();
                Profile profile = new Profile();
                if (!student.isJsonNull()) {
                    String studentJsonString = "{" +
                            "\"isStudent\": " + true + ", " +
                            "\"firstname\": " + "\"" + student.get("FirstName").getAsString() + "\"" + ", " +
                            "\"middlename\": " + "\"" + student.get("MiddleName").getAsString() + "\"" + ", " +
                            "\"lastname\": " + "\"" + student.get("LastName").getAsString() + "\"" + ", " +
                            "\"email\": " + "\"" + student.get("AlternateEmail").getAsString() + "\"" + ", " +
                            "\"password\": " + "\"" + hashPassword + "\"" +
//                            ", " +
//                            "\"bio\": " + null + ", " +
//                            "\"github_link\": " + null + ", " +
//                            "\"resume_link\": " + null + ", " +
//                            "\"portfolio_link\": " + null + ", " +
//                            "\"business_name\": " + null + ", " +
//                            "\"business_category\": " + null + ", " +
//                        "\"user_id\": " + "\"" + "cb0cdc1e-e9ae-4b89-9191-4555824a6b73" + "\"" +
                            "}";
//                    System.out.println(studentJsonString);
                    //                    System.out.println(rspData);
                    return profile.post(studentJsonString);
                }
            }
            return responseCode;
        }
    }
}