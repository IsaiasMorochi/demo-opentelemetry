## DEMO OPENTELEMETRY

## Prerequisitos

* Java 17
* Spring boot 3.0.2
* Docker compose

### Compilar proyecto
```shell
./gradlew bootJar
```

### Contruir contenedor
```shell
docker-compose up -d --build
```

### Demo
* Servicio
* Type: GET
```shell
curl http://localhost:8080/hello
```
* Jaeger
```shell
http://localhost:16686/
```