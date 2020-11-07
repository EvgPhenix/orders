# Orders Service
### How to build
- git clone
- mkdir to service directory
- ./gradlew build
- java jar (get jar from build directory)
- order service is available on port 8080

#### First iteration (tag v1.0) can do

- curl -X GET http://localhost:8080/calculations -H 'USER_ID: 12345' -d '["orange", "apple", "apple"]' -H 'Content-Type: application/json' | python -m json.tool
- receive simple orders of shopping goods from the command line
- Apples cost 60 cents and oranges cost 25 cents
- The service calculates like that: [ Apple, Apple, Orange, Apple ] => $2.05
- Unit tests has added

#### Second iteration (tag v2.0) can do

- Offers has added
- curl -X GET http://localhost:8080/offers -H 'USER_ID: 12345' | python -m json.tool
- you can see offers like ```"buy one get one free on Apples" or "3 for the price of 2 on Oranges"```
- curl -X GET http://localhost:8080/calculations -H 'USER_ID: 12345' -d '["orange", "apple", "apple", "orange", "orange"]' -H 'Content-Type: application/json' | python -m json.tool
- receive simple orders of shopping goods from the command line
- counts the cost of goods with offers
- Apples cost 60 cents and oranges cost 25 cents
- The service calculates like that: [ Apple, Apple, Orange, Orange, Orange ] => $1.10
- Unit tests has added
  
