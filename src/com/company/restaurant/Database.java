package com.company.restaurant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
                    // If logging in, verify both user and password match
                    if(password != null && Objects.equals(obj2.getString("email"), email) &&
                            Objects.equals(obj2.getString("password"), password)) {
                        userData = obj2;
                        userData.put("correct_pass", true);
                        // if registering, check if email matches any in db
                    } else if(Objects.equals(obj2.getString("email"), email)) {
                        userData = obj2;
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

        public static JSONObject readFromTable(String table, int id, List<String> cols, String idCol) {
        JSONObject rowDetails = new JSONObject();
        HttpURLConnection http = null;
        try {
            URL url = new URL("http://slynch.ie:8000/" + table);
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
                    if(obj2.getInt(idCol) == id) {
                        for(String col : cols) {
                            rowDetails.put(col, obj2.get(col));
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
        return rowDetails;
    }

    public static JSONArray readAllFromTable(String table, int idNum, String col, String matchVal) {
        JSONArray tableData = null;

        try {
            URL url = new URL("http://slynch.ie:8000/" + table);
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
                tableData = new JSONArray(inline.toString());
            }


            http.getResponseCode();
            http.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if((idNum != -1 || !Objects.equals(matchVal, "")) && tableData != null){
            JSONArray matchedTableData = new JSONArray();
            for (Object obj : tableData){
                JSONObject obj2 = (JSONObject)obj;
                if((idNum != -1 && obj2.getInt(col) == (idNum)) || obj2.get(col).equals(matchVal)){
                    matchedTableData.put(obj2);
                }
            } 
            return matchedTableData;
        }
        return tableData;
    }

    public static int writeToTable(String table, JSONObject data) {
        URL url;
        HttpURLConnection http = null;
        int idNum = -1;

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

            InputStream stream2 = http.getInputStream();
            StringBuilder sb = new StringBuilder();
            for (int ch; (ch = stream2.read()) != -1; ) {
                sb.append((char) ch);
            }
            String[] splitSb = sb.toString().split(" ");
            if (splitSb.length > 2) {
                String num = splitSb[2].replace("\"", "");
                try {
                    idNum = Integer.parseInt(num);
                } catch (NumberFormatException e) {
                    idNum = -1;
                }
            }
            
            http.getResponseCode();

        } catch (IOException e) {
            e.printStackTrace();
            if (http != null) {
                http.disconnect();
            }
            return idNum;
        }
        http.disconnect();
        return idNum;
    }

    public static boolean updateTable(String table, JSONObject data) {
        allowMethods("PATCH");
        URL url;
        HttpURLConnection http = null;
        try {
            url = new URL("http://slynch.ie:8000/" + table);
            http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("PATCH");
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

    private static void allowMethods(String... methods) {
        try {
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

            methodsField.setAccessible(true);

            String[] oldMethods = (String[]) methodsField.get(null);
            Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
            methodsSet.addAll(Arrays.asList(methods));
            String[] newMethods = methodsSet.toArray(new String[0]);

            methodsField.set(null/*static field*/, newMethods);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
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
            // col is for the id of each table
            data.put(col, id);

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
