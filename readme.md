# Orders Service
### How to build
- git clone
- mkdir to service directory
- ./gradlew build
- java jar (get jar from build directory)
- curl -X GET http://localhost:8080/calculations -H 'USER_ID: 12345' -d '["orange", "apple", "apple"]' -H 'Content-Type: application/json' | python -m json.tool

#### First iteration can do

- receive simple orders of shopping goods from the command line
- Apples cost 60 cents and oranges cost 25 cents
- The service calculates like that: [ Apple, Apple, Orange, Apple ] => $2.05
- Unit tests has added
  
