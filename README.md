# Simple Web Server

A multi-threaded web server with thread-pooling implemented in Java.

## Implemented features:

* Proper HTTP/1.1 keep-alive behavior is implemented based on the http-client's capabilities exposed through its request headers. The keep-alive behavior is implemented through Pre and Post Filters. 

  * If a keep-alive header is identified in the KeepAlivePreFilter the keepAlive flag is set on the client socket and keep the inputStream open for subsequent requests until a request with no keep-alive header is received. If a request with no keep-alive header received the server stops reading from inputSteam and close the client socket after the request is served. A Connection:Keep-Alive response header is also added to the keep-alive requests using KeepAlivePostFilter.
  
* Configuration using java properties file. The available configuration properties are:
```
bindAddress=127.0.0.1
port=3333
backlog=100
noOfThreadPool=10
docRoots=./doc-root1,./doc-root2
```

* Filter chain: Pre Filters are invoked after the request received and Post Filters are invoked before the response is served. Chain of responsibility pattern is used to implement Filter chain. Each filter implements the doFilter() method.
Currently implemented filters:
  * Pre Filter
    * KeepAlivePreFilter
  * Post Filter
    * KeepAlivePostFilter
    * ContentHeaderPostFilter
    * ServerHeaderPostFilter

* Multiple doc roots support to serve contents from multiple directories in the filesystem.

* HTTP Status code for the filesystem contents using custom Exception handing. Implemented codes are 200, 404, and 500.

## How to run the server:
An executable jar is included in the build_jar directory of this project. Update the server-config.properties file with the correct configuration values. The doc-root1 folder contains some sample html and css files as well. Use the following terminal commands to run the jar.
```
cd build_jar/
java -jar SimpleWebServer.jar
```
After running the server you will see the welcome message with the server address to access the sample home page. 


