package com.company.ui;

import com.company.ui.UserInterface;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class UserLogin extends UserInterface {
    private boolean successfulLogin = false;
    private String email = "";

    public UserLogin() {
    }

    void setSuccessfulLogin(boolean success) {
        successfulLogin = success;
    }

    boolean isSuccessfulLogin() {
        return this.successfulLogin;
    }

    protected void displayLoginPrompt() {
        System.out.println("Enter Q on its own in the email field to shut down the system, or enter B to go back to the previous screen.");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Email address:");
        String email = scanner.nextLine();
        checkQ(email);
        if(inputB(email)) {
            return;
        }
        System.out.println("Enter Password:");
        String password = scanner.nextLine();
        boolean validCredentials = validEmailAndPassword(email, password);
        while(!validCredentials) {
            System.out.println("Please enter valid login details. You must register before you can login.");
            System.out.println("Enter Email address:");
            email = scanner.nextLine();
            if(inputB(email)) {
                this.setSuccessfulLogin(false);
                return;
            }
            System.out.println("Enter Password:");
            password = scanner.nextLine();
            validCredentials = validEmailAndPassword(email, password);
        }
        this.setSuccessfulLogin(true);
        this.email = email;
    }

    private boolean validEmailAndPassword(String email, String password) {
        try {
            /* Send to DB */
            /*URL url = new URL("http://slynch.ie:8000/user");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Content-Type", "application/json");

            String data = "{\n  \"username\": \"John Doe\",\n  \"password\": 12345,\n  \"user_type\": \"customer\"\n}";

            byte[] out = data.getBytes(StandardCharsets.UTF_8);

            OutputStream  stream = http.getOutputStream();
            stream.write(out);
            System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
            System.out.println("Hi, I'm here");
            http.disconnect();
            */
            /* Get From DB */
            URL url = new URL("http://slynch.ie:8000/user");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.connect();
            int responsecode = http.getResponseCode();
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                //Close the scanner
                scanner.close();
                //  System.out.println(inline);
                JSONArray datajson = new JSONArray(inline);

                //  JSONObject obj = (JSONObject) datajson.get(0);

                //Get the required data using its key
                //    System.out.println(obj);

                for(Object obj : datajson) {
                    JSONObject obj2 = (JSONObject) obj;
                    //change username to email when DB updated
                    if(Objects.equals(obj2.getString("username"), email) &&
                            Objects.equals(obj2.getString("password"), password)) {
                        // store user type here
                        return true;
                    }
                }
            }
            http.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
