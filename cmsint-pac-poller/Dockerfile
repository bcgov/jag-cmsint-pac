FROM eclipse-temurin:11-jre-alpine

RUN apk upgrade expat  # Fix for CVE-2022-43680

COPY ./target/cmsint-pac-poller.jar cmsint-pac-poller.jar

ENTRYPOINT ["java", "-jar","/cmsint-pac-poller.jar"]