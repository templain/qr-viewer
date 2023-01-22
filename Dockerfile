FROM ubuntu AS war-build

RUN apt update && apt install -y git maven
WORKDIR /usr/local/src
COPY qrcode-viewer/ /usr/local/src/
RUN mvn package

FROM tomcat:9-jdk8

COPY --from=war-build /usr/local/src/target/qrcode-viewer.war /usr/local/tomcat/webapps/
