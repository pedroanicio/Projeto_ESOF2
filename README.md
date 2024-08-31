# Project Setup and Running Instructions

## Overview
This project uses Docker Compose to orchestrate multiple microservices and databases. The services include configuration management, service discovery, API gateway, and various microservices with their respective databases. Follow these instructions to get the application up and running.

## Prerequisites
Before you start, ensure you have the following installed on your local machine:

1. Docker: Install Docker from Docker's official site and ensure it is running.

2. Docker Compose: Docker Compose is included with Docker Desktop. 

3. Git: To clone the repository, you need Git. Download it from Git's official site if you don't have it installed.

## Docker Images
Ensure you have the following Docker images pulled locally. These images are available on Docker Hub with the `latest` tag:

1. `pedroanciodev/config_server:latest`
2. `pedroaniciodev/discovery_server:latest`
3. `pedroaniciodev/api-gateway:latest`
4. `pedroaniciodev/cambio_service:latest`
5. `pedroaniciodev/length_service:latest`
6. `pedroaniciodev/conversion_service:latest`
7. `mysql:latest`



To pull these images, use the following commands:

```
docker pull pedroanciodev/config_server:latest
docker pull pedroaniciodev/discovery_server:latest
docker pull pedroaniciodev/api-gateway:latest
docker pull pedroaniciodev/cambio_service:latest
docker pull pedroaniciodev/length_service:latest
docker pull pedroaniciodev/conversion_service:latest
docker pull mysql:latest
```

### Running the Application
1. Clone the Repository

Clone the repository to your local machine:

```
git clone https://gitlab.com/pedroanicio/projeto_esof2
```
2. Start the Services

Navigate to the directory containing the docker-compose.yml file and run the following command to start all services:

```
docker-compose up -d
```

3. Verify the Services

You can check the status of the services using:
```
docker-compose ps
```

Access the services via the following ports:

* Discovery Server: http://localhost:8761
* API Gateway: http://localhost:8765
* Cambio Service: http://localhost:8000
* Length Service: http://localhost:8200
* Conversion Service: http://localhost:8100

### Stopping the Services
To stop and remove the containers, networks, and volumes, run:
```
docker-compose down
```