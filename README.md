# Shopify Backend Developer Intern Challenge - Summer 2022

Inventory management web application, built using Springboot. This application provides the ability to add/remove items from named collections.

## Setup

### Java (11+)

You can download the latest JDK from Oracle [here](https://www.oracle.com/java/technologies/downloads/). Alternatively, older JDKS can be downloaded from other vendors. This application can be compiled using Java 11 or newer.

## Run

Download the JAR file from the [latest release](https://github.com/charleswong739/Shopify-Demo/releases). Then use the following command to run the JAR:
```shell
java -jar [PATH_TO_JAR]
```

Alternatively, clone the repository, and use the `bootRun` Gradle task to automatically compile and run the application. From the root of the repository, run the following command in your terminal of choice
```shell
./gradlew bootRun
```

## API documentation

API documentation can be found [here](https://charleswong739.github.io/). CORS is enabled, meaning API calls can be made directly from the documentation page (use the "Try it out" buttons) while the application is running. 
