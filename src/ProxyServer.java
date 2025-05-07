// ProxyServer.java
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProxyServer {
    private static final int PORT = 8010;           // Port for the proxy server
    private static final int CACHE_SIZE = 5;        // LRU Cache size (number of pages to store)
    private final LRUCache<String, String> cache;   // Cache instance
    private final ExecutorService threadPool;       // Thread pool for handling multiple clients

    // Constructor
    public ProxyServer(int poolSize, int cacheSize) {
        this.cache = new LRUCache<>(cacheSize);
        this.threadPool = Executors.newFixedThreadPool(poolSize);
    }

    // Start the server
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Proxy Server is listening on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            threadPool.execute(() -> handleClient(clientSocket));  // Handle each client in a separate thread
        }
    }

    // Handle client requests
    private void handleClient(Socket clientSocket) {
        try (BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // Read client request (GET request)
            String requestLine = fromClient.readLine();
            if (requestLine == null || !requestLine.startsWith("GET")) {
                toClient.println("HTTP/1.1 400 Bad Request");
                return;
            }

            // Extract the requested URL from the GET request
            String url = requestLine.split(" ")[1].substring(1);  // Remove "GET /" part

            // Check if the page is in the cache
            String cachedPage = cache.get(url);
            if (cachedPage != null) {
                // Cache hit
                System.out.println("Cache HIT: " + url);
                toClient.println("HTTP/1.1 200 OK");
                toClient.println("Content-Type: text/html");
                toClient.println();
                toClient.println(cachedPage); // Send cached page to client
            } else {
                // Cache miss - Fetch from original server
                System.out.println("Cache MISS: " + url);
                String pageContent = fetchPageFromServer(url);
                if (pageContent != null) {
                    // Cache the page for future use
                    cache.put(url, pageContent);

                    toClient.println("HTTP/1.1 200 OK");
                    toClient.println("Content-Type: text/html");
                    toClient.println();
                    toClient.println(pageContent);  // Send fetched page to client
                } else {
                    toClient.println("HTTP/1.1 500 Internal Server Error");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fetch page content from the original server (HTTP GET request)
    private String fetchPageFromServer(String url) {
        StringBuilder content = new StringBuilder();
        try {
            // Connect to the original server
            URL targetUrl = new URL("http://" + url);
            HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            // Read the response from the original server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return content.toString();
    }

    // Main method to start the proxy server
    public static void main(String[] args) {
        ProxyServer server = new ProxyServer(10, CACHE_SIZE); // 10 threads, Cache size = 5
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
