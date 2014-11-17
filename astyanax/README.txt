In order to use the AstyanaxClient you need to follow these steps:

1. Clone and compile Cassandra(only for compiling, not for running a Cassandra Node):
	git clone https://github.com/DavidHerzogTU-Berlin/cassandra.git
 	cd cassandra
 	ant
 	ant mvn-install

2. Clone and compile Astyanax:
	git clone -b cleanup https://github.com/DavidHerzogTU-Berlin/astyanax.git
	cd astyanax
	./gradlew build -x test
	./gradlew uploadMavenLocal

3. Clone and compile ycsb:
	git clone https://github.com/DavidHerzogTU-Berlin/ycsb.git
	cd ycsb
	mvn clean package

4. Clone and compile Cassandra (to run a 3 Node Cluster)
	git clone https://github.com/DavidHerzogTU-Berlin/cassandraToRun.git
	cd cassandraToRun
	ant

Now you should be able to run ycsb with the AstyanaxClient:
Example: bin/ycsb run astyanax-1 -p hosts=localhost -p map_size=1 -P workloads/workloada

Additional Information to the steps from above:
 
 1) This is Cassandra 1.2.18 with a custom interface. 
 	To costomiz it we used the thrift-compiler-0.7.0 .
 	This Cassandra version is used to generate jars to push them into the maven repository
 	so Astyanax can use it. We do not use this Cassandra version for running a Cassandra cluster.

 4) This is the Cassandra version we use for running a 3 node cluster. 
 	It also has a modified interface which was compiled with the thrift-compiler-0.9.0 . 
 	Astyanax is not able to use the jars from this version. 