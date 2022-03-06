FROM openjdk:11-jre
WORKDIR /ms-sp-support-exchange-rate
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run