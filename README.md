# Edpo-project

In order to run our event processing topology you need to first start kafka and send some messages via kafka.

````bash
docker compose -f docker-compose-v2.yml up

docker exec -it kafka bash

kafka-console-producer --bootstrap-server kafka:9092 --topic energy-consumer <  test_energyConsumer.json
kafka-console-producer --bootstrap-server kafka:9092 --topic energy-producer <  test_energyProducer.json
kafka-console-producer --bootstrap-server kafka:9092 --topic customers --property 'parse.key=true' --property 'key.separator=|' < test_customers.json
````
 
Then you can start the eventstrom application [App.java](eventstrom/src/main/java/ch/unisg/edpo/eau/eventstrom/App.java).
The topology should run through within ~1-2min.
