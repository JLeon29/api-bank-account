FROM adoptopenjdk/openjdk11:alpine-jre
VOLUME /tmp
ADD target/*.jar /usr/share/demo3.jar
EXPOSE 9002
ENTRYPOINT ["java", "-jar", "/usr/share/demo3.jar"]