FROM adoptopenjdk/openjdk11:alpine-jre
VOLUME /tmp
ADD target/*.jar /usr/share/demo7.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "/usr/share/demo7.jar"]