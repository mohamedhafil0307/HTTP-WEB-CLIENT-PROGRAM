import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;

public class AdvancedHttpClientExample {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static void main(String[] args) {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        // Perform GET request
        doGetRequest(client);

        // Perform POST request
        doPostRequest(client);
    }

    private static void doGetRequest(HttpClient client) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/posts/1"))
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(5))
                    .GET()
                    .build();

            System.out.println("Sending GET request...");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("GET Response Status Code: " + response.statusCode());
            System.out.println("GET Response Body:");
            System.out.println(response.body());
            System.out.println("-------------------------------------------------\n");

        } catch (HttpTimeoutException e) {
            System.err.println("GET request timed out");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doPostRequest(HttpClient client) {
        try {
            String jsonPayload = """
                    {
                        "title": "foo",
                        "body": "bar",
                        "userId": 1
                    }
                    """;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/posts"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(5))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            System.out.println("Sending POST request...");
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("POST Response Status Code: " + response.statusCode());
            System.out.println("POST Response Body:");
            System.out.println(response.body());
            System.out.println("-------------------------------------------------\n");

        } catch (HttpTimeoutException e) {
            System.err.println("POST request timed out");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

