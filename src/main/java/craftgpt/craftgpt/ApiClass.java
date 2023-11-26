package craftgpt.craftgpt;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class ApiClass {

    public static CompletableFuture<String> sendPostRequest(String jsonInputString) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL urlObj = new URL("http://localhost:3000/chat");
                HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                OutputStream os = connection.getOutputStream();
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);

                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }

                connection.disconnect();

                return content.toString();

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public static CompletableFuture<String> getDeathMessageReponse(String jsonInputString) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                URL urlObj = new URL("http://localhost:3000/deathreply");
                HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                OutputStream os = connection.getOutputStream();
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);

                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }

                connection.disconnect();

                return content.toString();

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }
}
