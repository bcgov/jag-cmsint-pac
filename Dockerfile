##############################################################################################
#### Stage where the maven dependencies are cached                                         ###
##############################################################################################
FROM maven:3.8-eclipse-temurin-17 as dependencies-cache

ARG MVN_PROFILE

WORKDIR /build

## for the lack at a COPY --patern */pom.xml, we have to declare all the pom files manually
COPY ./pom.xml pom.xml
COPY ./cmsint-pac-common-models/pom.xml cmsint-pac-common-models/pom.xml
COPY ./cmsint-pac-transformer/pom.xml cmsint-pac-transformer/pom.xml
COPY ./cmsint-pac-extractor/pom.xml cmsint-pac-extractor/pom.xml
COPY ./cmsint-pac-loader/pom.xml cmsint-pac-loader/pom.xml

RUN  mvn dependency:go-offline \
    -P${MVN_PROFILE} \
    -DskipTests \
    --no-transfer-progress \
    --batch-mode \
    --fail-never

##############################################################################################
#### Stage where the application is built                                                  ###
##############################################################################################
FROM dependencies-cache as build

ARG MVN_PROFILE

WORKDIR /build

COPY ./cmsint-pac-transformer/src cmsint-pac-transformer/src
COPY ./cmsint-pac-common-models/src cmsint-pac-common-models/src
COPY ./cmsint-pac-extractor/src cmsint-pac-extractor/src
COPY ./cmsint-pac-loader/src cmsint-pac-loader/src

RUN  mvn clean package \
     -P${MVN_PROFILE} \
     -DskipTests \
     --no-transfer-progress \
     --batch-mode

##############################################################################################
#### Stage where Docker is running a java process to run a service built in previous stage ###
##############################################################################################
FROM eclipse-temurin:17-jre-alpine

RUN apk update && apk add --upgrade --no-cache libexpat # fix CVE-2024-8176

ARG MVN_PROFILE

COPY --from=build /build/${MVN_PROFILE}/target/${MVN_PROFILE}*.jar /app/application.jar

ENTRYPOINT ["java", "-jar","/app/application.jar"]
