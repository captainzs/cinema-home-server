FROM maven:3.6.3-openjdk-8

ARG VERSION=0.1-SNAPSHOT

COPY . /opt/src/cinema-home-client-server
WORKDIR /opt/src/cinema-home-client-server
RUN mvn clean package -DskipTests -Drevision=$VERSION
RUN cp cinema-home-client-server-assembly-ncore-transmission/target/cinema-home-client-server-assembly-ncore-transmission-$VERSION.jar cinema-home-client-server-assembly-ncore-transmission-current.jar

EXPOSE 8080:8080
CMD ["java", "-jar", "cinema-home-client-server-assembly-ncore-transmission-current.jar"]