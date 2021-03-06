echo "Waiting for Kafka to come online..."
cub kafka-ready -b kafka:9092 1 20
# create the game events topic
kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic energy-consumer \
  --replication-factor 1 \
  --partitions 3 \
  --create
# create the players topic
kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic energy-producer \
  --replication-factor 1 \
  --partitions 3 \
  --create
# create the products topic
kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic customers \
  --replication-factor 1 \
  --partitions 3 \
  --create
sleep infinity