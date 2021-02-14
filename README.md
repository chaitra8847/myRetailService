# myRetailService
MyRetail Service using Springboot, Java ,Aerospike and Docker.

# Prerequisites
Needs Docker to be installed. 

# Design 

The applications is built as Docker container which uses aerospike container as database. 
Uses Springboot for RestApi implementation as creation of Restservice and deployement as well is simpler as tomcat server is inbuilt in springboot.
Aerospike is used as its NoSQL key-value database that delivers ultra-fast runtime performance for read/write workloads, high availability, near-linear scalability, and strong data consistency.

# Running Application

1) Start Aerospike.

 docker run -tid  --name aerospike -p 3000:3000 -p 3001:3001 -p 3002:3002 -p 3003:3003 aerospike/aerospike-server

2) Build Application 

gradle build clean

docker build -t myretail:1.0.0 .

3) Run Application 


docker run -d --rm --name myretail-service  -p 8080:8080 myretail:1.2.0

# Future Enhancements

1) Integration Testing
2) Swagger Documentation
3) Security


