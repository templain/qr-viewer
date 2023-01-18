FROM ubuntu AS war-build

RUN apt update && apt install -y git maven
WORKDIR /usr/local/src
COPY qrcode-viewer/ /usr/local/src/
RUN mvn package

FROM tomcat:8.5

COPY --from=war-build /usr/local/src/target/qrcode-viewer-1.0.0-SNAPSHOT.war /usr/local/tomcat/webapps/
