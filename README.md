
### Prerequisites

 1. Apache Maven 3.9.9 and higher.
 2. JDK 24 and higher.
 3. Git 2.50 and higher.

### Application requirements.

The Java application is based on the Jakarta 10 WebSocket specification.

### How to build this demo?
1. Copy this demo repo URL and clone this branch (using `git clone`).
2. Open the terminal/command line and get to the folder `cd sindi-ai-chatbot`.
3. Open the file `microprofile-config.properties`, found in `src/main/resources/META-INF/microprofile-config.properties`
4. Add your Gemini API key as a parameter value for `dev.langchain4j.plugin.chat-model.config.api-key` key.
5. Return to the project root folder (in this case `sindi-ai-chatbot`).
6. Build the project with Maven.

```mvn
mvn clean package -e
```

### How to run this demo?
Once the build of this demo has completed successfully, you can choose any of the 2 application servers to run it.

 1. With Payara Server.
 2. With OpenLiberty Application Server (requires some further configuration on the code).

#### Running with Payara Server.

Run the following command:
 ```
 mvn payara-server:dev -e
 ```
This will start the Payara Server in Development mode. Once the application server boots up, it'll open your system's default browser, and you'll see the chatbot app. Alternatively, you can view the demo on the browser of choice: `http://locahost:8080/sindi-ai-chatbot/`.

To stop the application, just press `CTRL+C` to terminate the process.
