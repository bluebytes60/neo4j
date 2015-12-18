# KG-DBLP
Java version: 1.8 (You cannot use 1.7 or below)
0. Install JDK 1.8
1. Install maven
2. Install Neo4j 
3. Load dataset, please use neo4j to open the dbLarge directory, or us the code preprocess.java to create a database.
Notes that the password is 18656, it's hardcode in Rest.java and it need to be convert into a specialize format. Please see the document in Neo4j website.
Neo4jdb account/password: neo4j/18656
5. Under neo4j directory, run mvn spring-boot:run -Drun.jvmArguments="-Dusername=neo4j -Dpassword=18656"
