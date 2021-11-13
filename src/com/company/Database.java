package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Database {

    /*
    readFromUserTable is used to read from the database for login and registration as we do not have access to a user id
    before the user has registered and therefore can't use the regular readFromTable method.
     */
    public static JSONObject readFromUserTable(String email, String password) {
        JSONObject userData = new JSONObject();

        try {
            URL url = new URL("http://slynch.ie:8000/user");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.connect();
            int responseCode = http.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }

                //Close the scanner
                scanner.close();
                JSONArray jsonData = new JSONArray(inline.toString());

                for(Object obj : jsonData) {
                    JSONObject obj2 = (JSONObject) obj;
                    if(password != null && Objects.equals(obj2.getString("email"), email) &&
                            Objects.equals(obj2.getString("password"), password)) {
                        userData.put("password", password);
                        userData.put("email", obj2.getString("email"));
                        userData.put("userID", obj2.getInt("user_id"));
                        userData.put("fullName", obj2.get("fullname"));
                        userData.put("userType", obj2.get("user_type"));
                    } else if(Objects.equals(obj2.getString("email"), email)) {
                        userData.put("email", obj2.getString("email"));
                        userData.put("userID", obj2.getInt("user_id"));
                        userData.put("fullName", obj2.get("fullname"));
                        userData.put("userType", obj2.get("user_type"));
                        userData.put("password", password);
                    }
                }
            }
            http.getResponseCode();
            http.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userData;
    }

    public static JSONObject readFromTable(String table, int userId, List<String> cols) {
        JSONObject userLoyalty = new JSONObject();
        HttpURLConnection http = null;
        try {
            URL url = new URL("http://slynch.ie:8000/" + table);
            System.out.println("url is " + url);
            http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.connect();
            int responseCode = http.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }

                //Close the scanner
                scanner.close();
                JSONArray jsonData = new JSONArray(inline.toString());

                for(Object obj : jsonData) {
                    JSONObject obj2 = (JSONObject) obj;
                        if(obj2.getInt("user_id") == userId) {
                            for(String col : cols) {
                                userLoyalty.put(col, obj2.get(col));
                            }
                        }

                }
            }
            http.getResponseCode();
        } catch (IOException e) {
            if (http != null) {
                http.disconnect();
            }
            e.printStackTrace();
        }
        if (http != null) {
            http.disconnect();
        }
        return userLoyalty;
    }

    public static boolean writeToDatabase(String table, JSONObject data) {
        URL url;
        HttpURLConnection http = null;
        try {
            url = new URL("http://slynch.ie:8000/" + table);
            http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Content-Type", "application/json");

            byte[] out = data.toString().getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http.getOutputStream();
            stream.write(out);

            http.getResponseCode();

        } catch (IOException e) {
            e.printStackTrace();
            if (http != null) {
                http.disconnect();
            }
            return false;
        }
        http.disconnect();
        return true;
    }

    public static boolean deleteFromTable(String table, String col, int id) {
        boolean success = false;
        HttpURLConnection http = null;
        try {
            URL url = new URL("http://slynch.ie:8000/" + table);
            http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("DELETE");
            http.setDoOutput(true);
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Content-Type", "application/json");

            JSONObject data = new JSONObject();
            data.put(col, id);

            System.out.println(data);
            byte[] out = data.toString().getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http.getOutputStream();
            stream.write(out);
            success = true;
            http.getResponseCode();
        } catch (IOException e) {
            if (http != null) {
                http.disconnect();
            }
            e.printStackTrace();
        }
        if (http != null) {
            http.disconnect();
        }
        return success;
    }
}
