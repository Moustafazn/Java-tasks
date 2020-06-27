FROM alpine-java:base
MAINTAINER Moustafazn@gmail.com
RUN apk add --no-cache openjdk12
ADD target/task-pushNotification-service.jar /app/
WORKDIR /app
EXPOSE 8081
CMD ["java", "-jar", "task-pushNotification-service.jar"]
