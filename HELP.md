# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.1/maven-plugin/reference/html/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.5.1/reference/htmlsingle/#using-boot-devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.1/reference/htmlsingle/#boot-features-developing-web-applications)
* [OpenFeign](https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.5.1/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Additional Links
These additional references should also help you:

* [Declarative REST calls with Spring Cloud OpenFeign sample](https://github.com/spring-cloud-samples/feign-eureka)

### Installation
This app requires java 11 and maven

```sh
cd meliapp
mvn clean package
```
### Docker
this app is very easy to install and deploy in a Docker container.

```sh
cd meliapp
docker image build -t meliapp .
docker run --name app -d -p 8080:8080 meliapp
```

Verify the deployment by navigating to your server address in your preferred browser.

```sh
http://localhost:8080/api/ip?ip=5.6.7.8
```

#### Adicional
Configuration for automatic startup with the server

1. A service descriptor file called docker-app.service must be created in the path / etc / systemd / system / and then enabled.

```sh
cd /etc/systemd/system/
nano docker-app.service
```

2. Copy and paste the following text in the file

```sh
[Unit]
Description=meliapp container  
Requires=docker.service  
After=docker.service

[Service]
Restart=always  
ExecStart=/usr/bin/docker start -a app  
ExecStop=/usr/bin/docker stop -t 2 app

[Install]
WantedBy=default.target
```

3. Finally, we enable the service in the operating system

```sh
systemctl enable docker-app.service
```