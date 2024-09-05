FROM openjdk:17-jdk

WORKDIR /app

COPY target/BidBidBid-0.0.1-SNAPSHOT.jar /app/BidBidBid.jar

EXPOSE 8081

CMD ["java", "-jar", "BidBidBid.jar"]