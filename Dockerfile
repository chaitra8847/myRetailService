FROM ubuntu:18.04

RUN apt-get update && \
    DEBIAN_FRONTEND=noninteractive \
    apt-get -y install default-jre-headless && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# copy the packaged jar file into our docker image
COPY build/libs/myRetail.jar /myRetail.jar

EXPOSE 8080

CMD ["java", "-jar", "/myRetail.jar"]

