package me.kix.uzi.api.connection.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import me.kix.uzi.api.connection.ConnectionStrategy;

/**
 * An attempt at creating an easier way of sending GET requests.
 * 
 * @author Jackson
 * @since April 2019
 */
public class GetRequestConnectionStrategy implements ConnectionStrategy {

    @Override
    public String connect(String link) {
        StringBuilder inputStreamBuilder = new StringBuilder();
        HttpURLConnection connection = null;
        InputStream connectionInputStream = null;
        try {
            URL url = new URL(link);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connectionInputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connectionInputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                inputStreamBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connectionInputStream != null) {
                    connectionInputStream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputStreamBuilder.toString();
    }
}