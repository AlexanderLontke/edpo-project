# edpo-project

````bash
kafka-console-producer --bootstrap-server kafka:9092 --topic customers --property 'parse.key=true' --property 'key.separator=|' < test_customers.json
````


````bash
kafka-console-producer --bootstrap-server kafka:9092 --topic energy-consumer <  test_energyConsumer.json
````