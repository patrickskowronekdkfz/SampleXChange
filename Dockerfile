FROM maven:eclipse-temurin AS build

WORKDIR /app

COPY . ./

ENV TZ=Europe/Berlin
RUN mvn install
RUN mv target/SampleXChange-*.jar target/SampleXChange.jar


FROM eclipse-temurin:17-focal

COPY --from=build /app/target/SampleXChange.jar /app/

WORKDIR /app
RUN apt -y remove curl
RUN apt -y auto-remove
USER 1001

CMD ["java", "-jar", "SampleXChange.jar"]
