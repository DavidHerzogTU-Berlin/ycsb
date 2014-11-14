In order to use the AstyanaxClient you need to follow these steps:

1. Clone and compile Cassandra:
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


Now you should be able to run ycsb with the AstyanaxClient:
Example: bin/ycsb run astyanax-1 -p hosts=localhost -p map_size=1 -P workloads/workloada