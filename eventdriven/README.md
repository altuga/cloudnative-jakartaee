# Build
mvn clean package && docker build -t com.kodcu/requestresponse .

# RUN

docker rm -f requestresponse || true && docker run -d -p 8080:8080 -p 4848:4848 --name requestresponse com.kodcu/requestresponse 