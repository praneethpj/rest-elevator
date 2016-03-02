1. This is a web application.

2. Technologies used:

  Front-end:   AngularJS, Bootstrap(responsive)

  Back-end:    Spring Boot, Spring Data, JAP ORM, Java8, Mockito, Spring MVC Test Framework, DBUnit
               Front-end talks to back-end via Restful API.

  Database:    Spring Boot embedded hsqldb.

  Build Tool:  Maven3

3. How to build
  3.1 Download maven3 on your computer. Add its bin folder to %path% environment variable.
  3.2 Download JDK8. Add its bin folder to %path% environment variable. Set JAVA_HOME environment variable, point to JDK home folder. 
  3.3 From command line console, go to ElevatorApplication folder.
  3.4 Run "mvn package" to build, unit test and package it. ElevatorMonitor.jar will be generated in target folder.

4. How to run
  From command line console, go to ElevatorApplication folder.
  Run following command

    java -jar target/ElevatorMonitor.jar --server.port=8082

  This will start the application and listen on port 8082. You can change the port if it is used by other application.

5. In ElevatorApplication folder, there are two files build.bat.txt and run.bat.txt.
   If you use Windows, rename these files to build.bat and run.bat, change JAVA_HOME and M2_HOME in these files to point to your JDK and MAVEN folder.
   Then run build.bat to build and run.bat to start the application.

6. How to test
   In a browser(Firefox or Chrome), visit http://localhost:8082/
   Type in the number of people and select the level you want to go. You will see the movement of number in elevator column.
   The movement happens every 5 seconds. Green number means the elevator is idle. Red means it is doing a job.
   
   To view elevator movement tracking, visit http://localhost:8082/elevatormovements This will list all movements for all elevators.
   http://localhost:8082/elevatormovements/A will only list movements of elevator A.
   