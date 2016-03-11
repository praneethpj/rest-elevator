FROM java:8
VOLUME /tmp
ADD target/ElevatorMonitor.jar app.jar
RUN bash -c 'touch /app.jar'
CMD java -jar /app.jar

