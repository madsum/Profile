# Spark Profile Creator
Prerequisite tools: JDK8, maven, docker, docker-compose are installed.

Please make sure that localhost port 8080, 3000 and 5432 are free. 

git clone git@github.com:madsum/Profile_Back.git

git clone git@github.com:madsum/Profile_Front.git

cd Profile_Back

mvn clean install

docker-compose up --build

P.S: Limitation and future improvement suggestion. It is a prototype service without authentication. So all users can edit any profile or even delete all profiles. So we must improve it in the future. 




