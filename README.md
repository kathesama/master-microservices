[<img src="https://img.shields.io/badge/Linkedin-kathesama-blue?style=for-the-badge&logo=linkedin">](https://www.linkedin.com/in/kathesama)
<br>
![Microservices](https://img.shields.io/badge/Powered%20by-Microservices-yellow?style=for-the-badge)
![IntellijIdea](https://img.shields.io/badge/Made%20for-IntellijIdea-1f425f.svg?style=for-the-badge)
<br>
![Java](	https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-purple?style=for-the-badge&logo=spring-boot)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)
![Grafana](https://img.shields.io/badge/Grafana-F2F4F9?style=for-the-badge&logo=grafana&logoColor=orange&labelColor=F2F4F9)
![Prometheus](https://img.shields.io/badge/Prometheus-000000?style=for-the-badge&logo=prometheus&labelColor=000000)
![Netflix Eureka](https://img.shields.io/badge/Netflix%20Eureka-orange?style=for-the-badge&logo=netflix&labelColor=E50914)
![Keycloak](https://img.shields.io/badge/Keycloack-333?style=for-the-badge&logo=GNU%20Privacy%20Guard&logoColor=0093DD)
![Kafka](https://img.shields.io/badge/Kafka-333?style=for-the-badge&logo=kafka&logoColor=0093DD)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-333?style=for-the-badge&logo=kafka&logoColor=0093DD)
<br>

[![GitHub issues](https://img.shields.io/github/issues/kathesama/master-microservices?style=plastic)](https://github.com/kathesama/master-microservices/issues)
[![GitHub forks](https://img.shields.io/github/forks/kathesama/master-microservices?style=plastic)](https://github.com/kathesama/master-microservices/network)
[![GitHub stars](https://img.shields.io/github/stars/kathesama/master-microservices?style=plastic)](https://github.com/kathesama/master-microservices/stargazers)
<br>
![GitHub last commit](https://img.shields.io/github/last-commit/kathesama/master-microservices?color=red&style=plastic)
![GitHub version commits](https://img.shields.io/github/commits-since/kathesama/master-microservices/V2.0.0.svg?color=yellow&style=plastic)
![GitHub top language](https://img.shields.io/github/languages/top/kathesama/master-microservices?style=plastic)
<br>
![Maintained](https://img.shields.io/badge/Maintained%3F-yes-green.svg?style=plastic)


[comment]: <> (![OWASP]&#40;https://img.shields.io/badge/OWASP%3F-yes-green.svg?style=plastic&#41;)

[comment]: <> (![CleanCode]&#40;https://img.shields.io/badge/CleanCode%3F-yes-green.svg?style=plastic&#41;)
[![GitHub license](https://img.shields.io/github/license/kathesama/master-microservices?style=plastic)](https://github.com/kathesama/master-microservices/blob/main/LICENCE)
![GitHub repo size](https://img.shields.io/github/repo-size/kathesama/master-microservices?style=plastic)
<br>
# master-microservices

## Running containers
for build:

>docker build . -t eazybytes/accounts:s4 

For run:

>docker run -d -p 8000:8000 eazybytes/accounts:s4 
 
## Building images with buildpacks from mvn:

steps:
1. Add to POM:
    ```xml 
     <packaging>jar</packaging>
    ``` 
2. Add plugin:
    ```xml
   <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
            <image>
                <name>eazybytes/${project.artifactId}:s4</name>
            </image>
        </configuration>
    </plugin>
   ```
3. run this command: 
> mvn spring-boot:build-image

4. run the container:
> docker run -d -p hostPort:containerPort <container-name>


## Building images with google Jib from mvn:

steps:
1. Add plugin to POM from [Jib Maven Plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin):
    ```xml 
     <plugin>
       <groupId>com.google.cloud.tools</groupId>
       <artifactId>jib-maven-plugin</artifactId>
       <version>3.4.1</version>
       <configuration>
           <to>
               <image>eazybytes/${project.artifactId}:s4</image>
           </to>
       </configuration>
   </plugin>
    ``` 
2. run this command: 
> mvn compile jib:dockerBuild

3. run the container:
> docker run -d -p hostPort:containerPort <container-name>

## Handling your containers

to push your container to docker hub:
> docker image push docker.io/<container-name>

### Notes

In case of getting error after execute jib command like this:

```agsl
Failed to execute goal com.google.cloud.tools:jib-maven-plugin:3.4.1:dockerBuild (default-cli) on project loan-service: Build to Docker daemon failed, perhaps you should make sure your credentials for 'registry-1.docker.io/library/eclipse-temurin' are set up correctly. See https://github.com/GoogleContainerTools/jib/blob/master/docs/faq.md#what-should-i-do-when-the-registry-responds-with-unauthorized for help: Unauthorized for registry-1.docker.io/library/eclipse-temurin: 401 Unauthorized
[ERROR] GET https://auth.docker.io/token?service=registry.docker.io&scope=repository:library/eclipse-temurin:pull
```

Go to console and put this ```docker login registry.hub.docker.com```, then login with username and password

## Using Docker Compose

to run all your containers configured on your file at once you can run this:
> docker-compose up -d

to stop all your containers at once (this one erase them):
> docker-compose down -d
 
to stop all your containers at once (without erase them):
> docker-compose stop -d

to stop all your containers at once (if those aren't erased):
> docker-compose start -d 

* **-d** means detached, this runs the command and releases the console

Some commands to have in mind:

1. **`docker run <image>`**: Starts a container from an image.
2. **`docker ps`**: Lists all running containers.
3. **`docker ps -a`**: Lists all containers, both running and stopped.
4. **`docker stop <container_id>`**: Stops a running container.
5. **`docker rm <container_id>`**: Removes a stopped container.
6. **`docker images`**: Lists all images on the local system.
7. **`docker rmi <image_id>`**: Removes an image from the local system.
8. **`docker pull <image>`**: Pulls an image from a registry.
9. **`docker build -t <tag> .`**: Builds an image from a Dockerfile in the current directory.
10. **`docker exec -it <container_id> /bin/bash`**: Opens a bash shell inside a running container.
11. **`docker logs <container_id>`**: Shows the logs of a container.
12. **`docker volume create <volume_name>`**: Creates a volume for persistent data storage.
13. **`docker network create <network_name>`**: Creates a network for connecting containers.
14. **`docker commit <container_id> <new_image_name>`**: Creates a new image from a container's changes.
15. **`docker login`**: Logs in to a Docker registry.
16. **`docker push <image>`**: Pushes an image to a registry.
17. **`docker save <image> > file.tar`**: Saves an image to a tar archive.
18. **`docker load < file.tar`**: Loads an image from a tar archive.
19. **`docker inspect <container_id>`**: Shows detailed information about a container.
20. **`docker cp <container_id>:<container_path> <host_path>`**: Copies files/folders between a container and the local filesystem.
21. **`docker port <container_id>`**: Shows public facing port of a container.
22. **`docker top <container_id>`**: Shows running processes in a container.
23. **`docker stats <container_id>`**: Shows a live stream of container(s) resource usage statistics.
24. **`docker attach <container_id>`**: Attaches to a running container.

Remember to replace `<image>`, `<container_id>`, `<image_id>`, `<tag>`, `<volume_name>`, `<network_name>`, and `<new_image_name>` with your actual values. 
These commands are essential for managing your Docker environment effectively.


## Metdologia de los 15 factores para el desarrollo de *Cloud Native applications*.
La metodología de los 15 factores es un conjunto de directrices para diseñar y construir aplicaciones de software que son confiables, escalables y mantenibles⁶. Aquí están los 15 factores:

1. **`Codebase`**: La aplicación siempre se rastrea en un sistema de control de versiones, como Git o Subversion. Solo hay una base de código por aplicación³.
2. **`Dependency management`**: La aplicación declara todas sus dependencias de manera completa, exacta y clara, a través de un manifiesto de declaración de dependencia³.
3. **`Configuration`**: Almacena la configuración en el entorno¹.
4. **`Backing services`**: Trata los servicios de respaldo como recursos adjuntos¹.
5. **`Design, build, release, run`**: Diseña, construye, lanza y ejecuta la aplicación¹.
6. **`Processes`**: Ejecuta la aplicación como uno o más procesos sin estado¹.
7. **`Port binding`**: Exporta servicios a través del enlace de puerto¹.
8. **`Concurrency`**: Escala la aplicación por el modelo de proceso¹.
9. **`Disposability`**: Maximiza la robustez con inicio rápido y cierre elegante¹.
10. **`Environment parity`**: Mantiene la paridad entre el entorno de desarrollo y producción¹.
11. **`Logs`**: Trata los registros como transmisión de eventos¹.
12. **`Administrative processes`**: Ejecuta tareas de administración o mantenimiento como procesos únicos¹.
13. **`API first`**: Pone énfasis en la importancia de las API dentro del desarrollo de aplicaciones nativas de la nube¹.
14. **`Telemetry`**: Se centra en la recopilación de datos una vez que la aplicación se libera¹.
15. **`Authentication and authorization`**: Añade un énfasis importante en la seguridad para las aplicaciones nativas de la nube¹.

Estos factores son esenciales para administrar eficazmente tu entorno de Docker. Recuerda reemplazar `<image>`, `<container_id>`, `<image_id>`, `<tag>`, `<volume_name>`, `<network_name>` y `<new_image_name>` con tus valores reales.

Origen: Conversación con Bing, 5/5/2024
1. The 15-Factor Way of App: Building Cloud-Native Applications. https://becomegeeks.com/blog/the-15-factor-way-of-app-overcome-the-first-challenge-to-building-cloud-native-applications/.
2. Cloud-native applications and 15-Factor methodology. https://bing.com/search?q=15+factor+methodology+for+cloud+native+applications.
3. Beyond the 12 factors: 15-factor cloud-native Java applications. https://developer.ibm.com/articles/15-factor-applications/.
4. Twelve-factor app development on Google Cloud. https://cloud.google.com/architecture/twelve-factor-app-development-on-gcp.
5. Cloud native Fifteen Factor Apps - Nilesh Gule's Technical Blog. https://www.handsonarchitect.com/2022/08/cloud-native-fifteen-factor-apps.html.
6. Cloud Native Twelve-Factor and Fifteen-Factor Applications. https://careerhub.students.duke.edu/classes/cloud-native-twelve-factor-and-fifteen-factor-applications/.

## Running project from IDE

For running each service you have to set the environment, for that you have to add one ot those options:

1. ```--spring.profiles.active=<environment>``` on program arguments before running, or
2. ```SPRING_PROFILES_ACTIVE=<environment>``` on env vars

**Note**: you have to replace ```<environment>``` with one of this: **qa**, **dev** or **prod**

## Handling errors from git:

If you run this command:  ```git add .```

And then you get this: 
> warning: could not open directory 'infrastructure/configuration-server/src/main/java/com/kathesama/app/master/microservices/service/infrasctucture/server/configuration/infrastructure/adapter/input/rest/dto/model/request/': Filename too long

It's because The error you are seeing is caused by a limitation in Windows, which by default recognizes path lengths up to 260 characters. Git has a setting called *core.longpaths* that you can enable to allow longer file paths.

you have two options (both on admin mode):
1. Run ```git config --system core.longpaths true```, or
2. Run ```git config core.longpaths true```

The number 1 will be enabling longpaths for all repos on your system, instead the option 2 will be working only on your actual repo.

## some additional information

* Gateway implements:
  - Circuit breaker pattern
  - Retry pattern
  - Rate limiter pattern
  - Bulkhead pattern

* For execute several commands as a pipe on windows run this:
   > mvn clean install -U; if ($?) {.\build-images.ps1}
  
   or, for example, to run it from a service try this on it own folder:
   > mvn clean install -U; if ($?) {mvn compile jib:dockerBuild}
 

## Observability and Monitoring

This project implements: Grafana, Loki and Promtail

## Security

For security purposes has been applied several patterns which are been listed below:

1. **Redis Rate Limiter**: Help to prevent the abuse of the API limiting the number of applications that a user can make in a certain period of time.
2. **Circuit Breaker**: Protect services when detecting failures and open the circuit automatically, avoiding calls to a service that is failing and giving it time to recover.
3. **Retry**: It allows the requests that fail to automatically ref, which is useful in situations where failures are transitory and are expected to be resolved quickly.
4. **Private net**: By not exposing the ports of the microservices on the Docker network and leaving only the port of the exposed gateway, you can make sure that all the traffic towards your microservices passes through the gateway, the microservices only They communicate with each other within the same Docker network and are not accessible directly from outside that network

Likewise, **Keycloak** has been used for the management of authentication and validation via OAUTH2

## Event Driver Design

There are two approaches:
1. **Publisher/Subscriber** (Pub/Sub) Model: RabbitMQ technology

AMPQ protocol is implemented by RabbitMQ.

2. **Event Streaming** Model: Apache Kafka

