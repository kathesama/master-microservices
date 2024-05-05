#Start with a base image containing Java runtime
FROM amd64/eclipse-temurin:17.0.11_9-jdk-ubi9-minimal

#Information around who maintains the image
LABEL maintainer=kathesama@gmail.com

# Make a directory in the image
RUN mkdir -p /app

# Set the current working directory in the image to /app
WORKDIR /app

# Add the application's jar to the image
COPY target/account-service-0.0.1-SNAPSHOT.jar /app/account-service-0.0.1-SNAPSHOT.jar

# execute the application
ENTRYPOINT ["java", "-jar", "/app/account-service-0.0.1-SNAPSHOT.jar"]