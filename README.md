# jh-demo
Jhipster playground see service folder READMEs for further details 

## Jhipster Help

https://www.jhipster.tech/

 
#### Check if messages are sent to a kafka topic
    
    docker exec -it <container_id> /bin/bash
    cd /opt/kafka/bin
    ./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topic-jhipster --from-beginning

## Build maven
./mvnw verify -Pprod dockerfile:build

## Build gradle
./gradlew -Pprod build buildDocker

## Generate docker-compose
cd deploy/
jhipster docker-compose
