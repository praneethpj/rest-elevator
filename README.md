# Sprig-boot Rest Api based Elevator / Lift
## How to start

mvn package mvn spring-boot:run

## How to access endpoints

curl -v "http://localhost:8081/smartkent/liftsimulation/?fromFloor=9&toFloor=5"

## Short Description

Returns a message stating that lift would be in x seconds to requester in floor 'fromFloor' Each lift would take 3 seconds to travel between two floors. Each lift would stop 4 seconds on the floor to pick and/or drop a person. To understand whats going in lift, each lift would emit signals every time its state changes. Available states:

IDLE - no job. TO_PICKUP - moving to the floor to pick up the person PICKUP - picking up the person. TO_DROPOFF - moving to the destination floor DROPOFF - dropping off the person on the floor Emit signal should be printed as logs along with floor no, direction (NAN, UP or DOWN), passenger count.

Modified from : https://github.com/nielicheng/ElevatorApplication/tree/master/src
