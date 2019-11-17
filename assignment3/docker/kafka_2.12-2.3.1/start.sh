#!/bin/bash
sh /kafka/bin/zookeeper-server-start.sh /kafka/config/zookeeper.properties &
sh /kafka/bin/kafka-server-start.sh /kafka/config/server.properties &
sh /kafka/bin/connect-standalone.sh /kafka/config/connect-standalone.properties /kafka/config/connect-jdbc-source.properties 