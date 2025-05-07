package com.example.campusPayApp.api;

import com.example.campusPayApp.AppConfig;
import com.example.campusPayApp.HelloApplication;
import com.example.campusPayApp.utils.LocalStorageManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.IOException;

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
        Gson gson = new Gson();
        JsonObject user = gson.fromJson(data, JsonObject.class);

        LocalStorageManager.saveString("registeredUser", user.get("email").getAsString());

        Request request = new Request.Builder()
                .url(AppConfig.get("SUPABASE_URL") + "/rest/v1/" + TABLE_NAME)
                .header("apikey", AppConfig.get("SUPABASE_API_KEY"))
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
//                throw new IOException("Unexpected code " + response);
//                System.out.println("body" + response.body().string());
                return String.valueOf(response.code());
            }
            else {

//                System.out.println("body" + response.body().string());
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
//                    System.out.println("Successfully retrieved job, but response body is empty.");
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
//                    System.out.println("Successfully retrieved job: " + responseString);
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
//                    System.out.println("Successfully retrieved job, but response body is empty.");
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

    public String updateUser(String id, String data) throws IOException {
        // 1. Construct the URL to target the specific profile row using its ID.
        String url = AppConfig.get("SUPABASE_URL") + "/rest/v1/" + TABLE_NAME + "?id=eq." + id;

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        // 2. Create the request body from the JSON data string.
        RequestBody body = RequestBody.create(data, JSON);

        // 3. Build the PATCH request.
        Request request = new Request.Builder()
                .url(url)
                .patch(body) // Use PATCH method for partial updates
                .addHeader("apikey", AppConfig.get("SUPABASE_API_KEY"))
                // .addHeader("Authorization", "Bearer " + YOUR_JWT_TOKEN_IF_NEEDED) // Add if RLS requires authentication for updates
                .addHeader("Content-Type", "application/json") // Specify the content type of the payload
                .addHeader("Prefer", "return=representation") // Ask Supabase to return the updated row(s) in the response body
                .build();

        // 4. Execute the request and handle the response.
        try (Response response = client.newCall(request).execute()) {
            String responseBodyString = response.body() != null ? response.body().string() : "null"; // Safely read the body once

            if (response.isSuccessful()) {
                // Status code 200 OK is expected when using "Prefer: return=representation" and the update is successful.
                // Status code 204 No Content might be returned if "Prefer" header wasn't set or if return=minimal was used.
                String[] navData = LocalStorageManager.getObject("User") == null ? new String[]{"sign-in-view.fxml", "Sign In"} : new String[]{"home-view.fxml", "Welcome"};
                HelloApplication.changeScene(navData);
//                System.out.println("Profile update successful for ID: " + id + ". Code: " + response.code() + ". Updated data: " + responseBodyString);
                // You could potentially parse and return the updated object from responseBodyString if needed.
                return String.valueOf(response.code());
            } else {
                // Handle potential errors (e.g., 400 Bad Request, 401 Unauthorized, 404 Not Found)
                System.err.println("Failed to update profile for ID: " + id + ". Response code: " + response.code());
                System.err.println("Error details: " + responseBodyString);
                // If the response body indicates zero rows were updated (e.g., empty array "[]" when using representation)
                // it might imply the ID didn't exist, conceptually similar to a 404.
                if (response.code() == 200 && "[]".equals(responseBodyString.trim())) {
                    System.err.println("Note: Update request was successful (Code 200), but no rows matched the ID " + id + ".");
                    // You might want to treat this as a specific case, perhaps return "404" or similar logic.
                    return "404"; // Or return String.valueOf(response.code()) depending on desired behavior
                }
                return String.valueOf(response.code()); // Return the actual error code
            }
        } catch (IOException e) {
            System.err.println("Error making the updateUser request for ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw the exception
        }
    }

}
