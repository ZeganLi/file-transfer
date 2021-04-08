FROM java:8
EXPOSE 8080

VOLUME /tmp
ADD ftc.jar  /ftc.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
