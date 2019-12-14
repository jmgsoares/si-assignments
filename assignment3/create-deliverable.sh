rm -rf temp-deliverable-2019 deliverable.zip
mkdir temp-deliverable-2019
cp -r kafka temp-deliverable-2019/.
rm -rf temp-deliverable-2019/kafka/.idea
mkdir temp-deliverable-2019/kafka-configs
cp kafka_2.12-2.3.1/config/zookeeper.properties kafka_2.12-2.3.1/config/server.properties kafka_2.12-2.3.1/config/connect-standalone.properties kafka_2.12-2.3.1/config/connect-jdbc-s* temp-deliverable-2019/kafka-configs/.
cp tables-sample-data.sql temp-deliverable-2019/.
cd temp-deliverable-2019
zip -r ../deliverable.zip *
cd ..
rm -rf temp-deliverable-2019
