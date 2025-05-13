FROM docker.io/maven:3.9.9-amazoncorretto-24 AS builder

WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

RUN ls -la target
FROM docker.io/openjdk:24-slim

WORKDIR /app

COPY --from=builder /build/target/demo-0.0.1-SNAPSHOT.jar ./demo.jar

CMD [ "java", "-jar", "demo.jar" ]
