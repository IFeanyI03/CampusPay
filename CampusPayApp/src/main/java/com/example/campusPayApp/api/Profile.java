package com.example.campusPayApp.api;

import java.io.IOException;

import com.example.campusPayApp.AppConfig;
import okhttp3.*;

public class Profile {

    private static final String TABLE_NAME = "profiles";

    private final OkHttpClient client = new OkHttpClient();

    public String get(String applicant) throws IOException {
        String responseData;
        Request request = new Request.Builder()
                .url(AppConfig.get("SUPABASE_URL") + "/rest/v1/" + TABLE_NAME + "?select=*")
                .header("apikey", AppConfig.get("SUPABASE_API_KEY"))
//                .header("Authorization", "Bearer" + SUPABASE_JWT_TOKEN)// **ADD THE API KEY HEADER HERE**
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            responseData = response.body().string();
        }

//        System.out.println(responseData);
        return responseData;
    }

    public String post(String data) throws IOException {
        String responseData;
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(data, JSON);

        Request request = new Request.Builder()
                .url(AppConfig.get("SUPABASE_URL") + "/rest/v1/" + TABLE_NAME)
                .header("apikey", AppConfig.get("SUPABASE_API_KEY"))
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
//                throw new IOException("Unexpected code " + response);
                System.out.println("body" + response.body().string());
                return String.valueOf(response.code());
            }
            else {
                System.out.println("body" + response.body().string());
                return String.valueOf(response.code());
            }
        }
    }

    public String getProfileByEmail(String email) throws IOException {
        // 1. Construct the URL with the ID.  Supabase uses the ID in the URL path.
        String url = AppConfig.get("SUPABASE_URL")+ "/rest/v1/" + TABLE_NAME +  "?email=eq." + email;  //  /jobs?id=eq.1

        // 2. Build the Request.  Use GET for retrieving data.
        Request request = new Request.Builder()
                .url(url)
                .get() // Use the GET method
                .addHeader("apikey", AppConfig.get("SUPABASE_API_KEY"))
                .build();

        // 3. Execute the request and handle the response
        Response response = client.newCall(request).execute();
        try {
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String responseArray = responseBody.string();
//                    System.out.println("Responseody: " + responseString);
                    if (responseArray.equals("[]")){
                        return "404";
                    }
                    // Parse the JSON response.  It will be a JSON array, even if you only asked for one ID.
                    // You'll likely want to use Gson for this.  For example:
                    // Gson gson = new Gson();
                    // JobData[] jobDataArray = gson.fromJson(responseString, JobData[].class);
                    // if (jobDataArray.length > 0) {
                    //   JobData job = jobDataArray[0]; // Get the first (and only) job
                    //   System.out.println("Job Title: " + job.getTitle());
                    // }
                    else if (responseArray.startsWith("[") && responseArray.endsWith("]")) {
                        return responseArray.substring(1, responseArray.length() - 1);
                    }
                    else {
                        return responseArray;
                    }

                } else {
                    System.out.println("Successfully retrieved job, but response body is empty.");
                }
            } else {
                System.err.println("Failed to retrieve job. Response code: " + response.code());
                ResponseBody errorBody = response.body();
                if (errorBody != null) {
                    System.err.println("Error details: " + errorBody.string());
                }
            }
        } catch (IOException e) {
            System.err.println("Error making the request: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return response.body().toString();
    }

    public String getProfileById(String id) throws IOException {
        // 1. Construct the URL with the ID.  Supabase uses the ID in the URL path.
        String url = AppConfig.get("SUPABASE_URL")+ "/rest/v1/" + TABLE_NAME +  "?id=eq." + id;  //  /jobs?id=eq.1

        // 2. Build the Request.  Use GET for retrieving data.
        Request request = new Request.Builder()
                .url(url)
                .get() // Use the GET method
                .addHeader("apikey", AppConfig.get("SUPABASE_API_KEY"))
                .build();

        // 3. Execute the request and handle the response
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    String responseString = responseBody.string();
                    System.out.println("Successfully retrieved job: " + responseString);
                    // Parse the JSON response.  It will be a JSON array, even if you only asked for one ID.
                    // You'll likely want to use Gson for this.  For example:
                    // Gson gson = new Gson();
                    // JobData[] jobDataArray = gson.fromJson(responseString, JobData[].class);
                    // if (jobDataArray.length > 0) {
                    //   JobData job = jobDataArray[0]; // Get the first (and only) job
                    //   System.out.println("Job Title: " + job.getTitle());
                    // }
                    return responseString;

                } else {
                    System.out.println("Successfully retrieved job, but response body is empty.");
                    return "Successfully retrieved job, but response body is empty.";
                }
            } else {
                System.err.println("Failed to retrieve job. Response code: " + response.code());
                ResponseBody errorBody = response.body();
                if (errorBody != null) {
                    System.err.println("Error details: " + errorBody.string());
                }
            }
        } catch (IOException e) {
            System.err.println("Error making the request: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return "finally";
    }

}
