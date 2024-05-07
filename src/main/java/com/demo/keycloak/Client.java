package com.demo.keycloak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Client {

    public static StringBuilder getService(String data) {
        try {


            URL url = new URL("https://6613a6d153b0d5d80f67ee10.mockapi.io/api/v1/users/1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");




            // Check if the response code is 200 (OK)
            if (conn.getResponseCode() == 200) {
                // Get the input stream
                InputStream inputStream = conn.getInputStream();

                // Read the data from the input stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder responseBody = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    responseBody.append(line);
                }

                // Print the response body
                System.out.println("Response Body:");
                System.out.println(responseBody.toString());

                // Close the input stream
                reader.close();

                return(responseBody);
            } else {
                System.err.println("Error: Response code " + conn.getResponseCode());
            }

            // Don't forget to close the connection when you're done
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
