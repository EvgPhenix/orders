# Orders Service
### How to build
- git clone git@github.com:evgPhoenix/orders.git
- mkdir to service directory
- ./gradlew build
- cd build/libs
- java -jar orders_app-4.0.jar(there will be jar)
- order service is available on port 8080
- before you check it start https://github.com/evgPhoenix/mailService to send e-mails

#### First iteration (tag v1.0) can do

- curl -X GET http://localhost:8080/calculations -H 'USER_ID: 12345' -d '["orange", "apple", "apple"]' -H 'Content-Type: application/json' 
- receive simple orders of shopping goods from the command line
- Apples cost 60 cents and oranges cost 25 cents
- The service calculates like that: [ Apple, Apple, Orange, Apple ] => $2.05
- Unit tests has added

#### Second iteration (tag v2.0) can do

- Offers has added
- curl -X GET http://localhost:8080/offers -H 'USER_ID: 12345'
- you can see offers like ```"buy one get one free on Apples" or "3 for the price of 2 on Oranges"```
- curl -X GET http://localhost:8080/calculations -H 'USER_ID: 12345' -d '["orange", "apple", "apple", "orange", "orange"]' -H 'Content-Type: application/json'
- receive simple orders of shopping goods from the command line
- counts the cost of goods with offers
- Apples cost 60 cents and oranges cost 25 cents
- The service calculates like that: [ Apple, Apple, Orange, Orange, Orange ] => $1.10
- Unit tests has added

#### Third iteration (tag v3.0) can do

- Placing orders
- curl -X GET http://localhost:8080/orders?mailAddress=some.address@gmail.com -H 'USER_ID: John Doe' d '["orange", "apple", "apple", "orange", "orange"]' -H 'Content-Type: application/json'
- after placing orders you will receive e-mail with confirmation
- Unit tests has added
  
#### Fourth iteration (tag v4.0) can do

- Placing orders with managing stocks (by default there are 10 apples and 11 oranges)
- curl -X GET http://localhost:8080/orders?mailAddress=some.address@gmail.com -H 'USER_ID: John Doe' d '["orange", "apple", "apple", "orange", "orange"]' -H 'Content-Type: application/json'
- if goods are out of stocks the server will notify you in answer and by e-mail
- Unit tests has added

#### Fifth iteration (tag v5.0) can do

- All the same things but with Apache Kafka message broker
- If mail sender service is unavailable, the message will be in the queue until it would be started.
- to start Message queue use docker-compose file, before You need to download Kafka and Zookeeper docker image
    from Docker hub (for example via link `https://hub.docker.com/u/wurstmeister`)
- then use commands `docker-compose up -d`
- now start the application and follow the steps described in previous iterations
- if the queue is unavailable the service will try to send the message to message service via http.
