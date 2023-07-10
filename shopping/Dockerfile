FROM openjdk:11-oracle
VOLUME /tmp
COPY build/libs/shopping-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar","--spring.profiles.active=docker"]
	
