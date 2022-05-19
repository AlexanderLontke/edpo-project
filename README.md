# edpo-project



````bash
docker compose -f docker-compose-v2.yml

docker exec -it kafka bash

kafka-console-producer --bootstrap-server kafka:9092 --topic energy-consumer <  test_energyConsumer.json
kafka-console-producer --bootstrap-server kafka:9092 --topic energy-producer <  test_energyProducer.json
kafka-console-producer --bootstrap-server kafka:9092 --topic customers --property 'parse.key=true' --property 'key.separator=|' < test_customers.json
````


````bash
kafka-avro-console-producer --bootstrap-server kafka:9092 --topic energy-consumer --property value.schema='{"namespace":"ch.unisg.edpo.eau.eventstrom.model","name":"EnergyMeter","type":"record","fields":[{"name":"message_id","type":"string"},{"name":"message_type","type":"string"},{"name":"time_start","type":"long"},{"name":"time_end","type":"long"},{"name":"E_i_minus_one","type":"double"},{"name":"E_i","type":"double"},{"name":"deltaE","type":"double"},{"name":"deviceId","type":"string"},{"name":"customer_id","type":"string"}]}' <  test_energyConsumer.json

kafka-avro-console-consumer --bootstrap-server kafka:9092 --topic energy-consumer
````