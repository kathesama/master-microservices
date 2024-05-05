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