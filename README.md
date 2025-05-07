# ProxyServer-with-LRU-Cache


````
# 🔐 Java Multithreaded Proxy Server with LRU Cache

## 🧠 Project Overview

This project is a **Multithreaded Proxy Server** implemented in Java. It acts as an intermediary between clients (such as browsers or curl requests) and target web servers. The proxy server intercepts HTTP GET requests, checks if the requested page is present in a **Least Recently Used (LRU)** cache, and either serves it from the cache (cache hit) or fetches it from the origin server (cache miss).

This server supports concurrent requests using a **thread pool** and provides a simple demonstration of web server fundamentals, caching strategies, socket programming, and multi-threading.

---

## 🎯 Motivation

- Understand how proxy servers work internally.
- Learn how to implement a **basic web cache** using Java's `LinkedHashMap` for LRU eviction.
- Apply operating system concepts like **thread pools** and concurrency.
- Serve as a foundation for more complex features like:
  - Website blocking
  - Request logging
  - IP filtering
  - HTTPS support
  - Authentication and firewall rules

---

## 🏗️ How I Built It

The project was built in Java using the following components:

| Component           | Purpose |
|---------------------|---------|
| `ServerSocket`      | Listens for incoming connections |
| `ThreadPool (Executors)` | Handles multiple clients concurrently |
| `LinkedHashMap`     | Used to implement LRU cache |
| `HttpURLConnection` | Sends HTTP GET requests to actual web servers |

I started with a basic single-threaded proxy, then extended it using a fixed thread pool to support multiple clients. LRU cache logic was added with `LinkedHashMap` using access order.

---

## 🧪 Features

✅ Serves cached pages (HTML only)  
✅ Multi-threaded client handling (via ThreadPool)  
✅ LRU Cache (with eviction logging)  
✅ Logs cache hits and misses  
✅ Extensible codebase for new features  

---

## 🔍 Architecture & Flow Diagram


+-------------+        GET        +--------------+        GET        +-------------------+
|   Client    | ---------------> | Proxy Server | ------------->    |  Target Web Server|
|  (Browser)  |                  | (Port 8010)  |                   | (e.g., example.com)|
+-------------+                  +--------------+                   +-------------------+
        ^                                 |                                  |
        |                                 | Cache HIT/MISS                   |
        |        Cached Page (if HIT)     |                                  |
        | <------------------------------ |                                  |
````

---

## ⚙️ How to Set Up Locally

### Prerequisites

* Java JDK 8 or later
* Terminal or command prompt

### 🛠 Setup

1. Clone or download this repository:

   ```bash
   git clone https://github.com/yourusername/Java-Proxy-Server.git
   cd Java-Proxy-Server
   ```

2. Compile the source code:

   ```bash
   javac ProxyServer.java LRUCache.java
   ```

3. Run the server:

   ```bash
   java ProxyServer
   ```

4. Test it via browser or `curl`:

   ```bash
   curl http://localhost:8010/www.example.com
   ```

---

## 📦 Output

![Screenshot (83)](https://github.com/user-attachments/assets/e678a322-ffc5-4969-b707-39e42a48b7a6)
![Screenshot (82)](https://github.com/user-attachments/assets/663f5f09-504f-434f-ad22-5721eede222c)


```plaintext
Proxy Server is listening on port 8010
Cache MISS: www.example.com
Cache Updated - Added: www.example.com
Cache HIT: www.example.com
Cache MISS: www.geeksforgeeks.org
Cache Updated - Added: www.geeksforgeeks.org
Cache MISS - Evicting: www.example.com
```

### Client Output

HTML response (rendered or raw) of the requested site.

---

## 🚧 Challenges Faced

### 1. **Parsing URL from HTTP GET**

* Initial parsing failed when clients sent full URLs.
* Fixed by trimming `GET /` and avoiding prefix like `http://`.

### 2. **LRU Implementation Using LinkedHashMap**

* Challenge: Automatically removing least-recently used entries.
* Solved by manually checking `cache.size()` and removing `.entrySet().iterator().next()`.

### 3. **Concurrency Handling**

* Clients were blocked when requests were sequential.
* Solved using a fixed thread pool with `Executors.newFixedThreadPool()`.

---

## ⚠️ Limitations / Drawbacks

* ❌ Only supports HTTP, not HTTPS.
* ❌ `LinkedHashMap` does not auto-evict, so manual eviction needed.
* ❌ No filtering or blocking mechanism yet.
* ❌ Cache stores raw HTML only (no images, CSS, JS).

---

## 🚀 Future Enhancements

Here’s how you can extend this project:

| Feature              | Description                                                   |
| -------------------- | ------------------------------------------------------------- |
| 🚫 Website Blocking  | Block access to blacklisted domains (via config file or list) |
| 📜 Request Logging   | Log all requests with timestamp, IP, and response status      |
| 🔐 HTTPS Proxying    | Use SSL/TLS for handling HTTPS via tunneling                  |
| 🌐 GUI Dashboard     | Monitor active connections, cache entries                     |
| 🔄 Auto Cache Expiry | Add TTL to each entry in cache                                |
| 🔑 IP Filtering      | Allow only whitelisted IPs                                    |
| 📦 Cache Statistics  | Show hit/miss rate, eviction count                            |

---

## 🤝 Contributing

Contributions are **open for all**! Whether you're fixing bugs, adding features, or improving documentation, feel free to fork this repository and open a pull request.

Suggestions, feature requests, or improvements are welcome via [Issues](https://github.com/yourusername/Java-Proxy-Server/issues).

---

## 📚 Learnings

* Hands-on experience with **Java Networking** APIs.
* Understanding **HTTP protocol** flow and structure.
* Efficient use of **LRU Caching** using `LinkedHashMap`.
* Managing **multithreading** in Java using Executors.
* Real-world software design patterns and extensibility.

---

## 📩 Contact

Created by [Mohit Singh Gurjar](https://github.com/mohitsinghgurjar)

---

```

---

Would you like me to create a diagram or include the README in your actual GitHub project structure?
```
