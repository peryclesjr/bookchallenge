# Project Alten - Hotel Book Challenge

###  Post-Covid scenario:
People are now free to travel everywhere but because of the pandemic, a lot of hotels  wentbankrupt. Some former famous travel places are left with only one hotel.You’ve been given the responsibility 
to develop a booking API for the very last hotel in Cancun.


The requirements are:
- API will be maintained by the hotel’s IT department.
- As it’s the very last hotel, the quality of service must be 99.99 to 100% => no downtime
- For the purpose of the test, we assume the hotel has only one room available
- To give a chance to everyone to book the room, the stay can’t be longer than 3 days andcan’t be reserved more than 30 days in advance.
- All reservations start at least the next day of booking,
- To simplify the use case, a “DAY’ in the hotel room starts from 00:00 to 23:59:59.- Every end-user can check the room availability, place a reservation, cancel it or modify it.
- To simplify the API is insecure.

to run local the project, you have to use the docker: 
- docker-compose up # to run database and use localhost


# To run project, you have to use the docker, use the  docker-compose.yaml outside the folder bookchallenge
- for the first time user this:
- docker-compose up -d --build 
wait a little, maybe one minute, to execute all tests, flyway to create a insert records in database.

- After that use
- docker-compose up
- to turn off:
- docker-compose down






When run for the first time we have create a database from flyway.


## Swagger about the API
http://server_IPAdress:Port/swagger-ui/index.html



illustrative swagger picture




## DATABASE
- All scripts are in the folder resources/db.migration
- Here we have a image of the ER Diagram of the application database. 





ER Diagram picture