## Pulsar Java App
A simple multi-module maven project built using Spring Boot and Apache Pulsar

## Getting Started
- Java 21
- Maven 3.9.8
- Run
```
docker compose up
```
- Add configurations in intelliJ for consumer/producer
- For consumer configuration, allow running multiple instances
- Run 3 instances of consumer app just like 3 pods in k8s
- Run producer app (
  - bean instance type is set **prototype**
  - Every time a new consumer app instance is generated, a new instance of producer will be provided.
- 1000 messages will be divided by 3
  - each consumer app instance will get ~300 messages
  - each consumer app instance has further 2 consumers (foo, bar)
  - 300 messages will further be divided into ~150 messages

## Output
![consumer-settings](https://github.com/user-attachments/assets/fb59ee95-6a22-493a-bdad-2b5d4fc0f229)

![running-instances](https://github.com/user-attachments/assets/d16a42f6-e7de-4008-9cf7-b79a939da9d9)
