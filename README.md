# simple-restaurant
This application is built to support the daily operations of a simpleRestaurant from table to kitchen.
Please not that biling module is not included.

The tools and tech stack required to build and  test the application are as following
Java - JDK 1.8
MySql - version 8.0
IntellijIdea
Maven - latest version
postman rest client

Please note screenshots of sample request and response for each api is in /sample screenshots folder.
The application uses JWT token based authorization to restrict access of API's

To build this application,please go to the project folder in command prompt and run the follwoing commands.
(**refer to https://metamug.com/article/java/build-run-java-maven-project-command-line.html for further info on maven commands)
mvn compile //compile application
mvn clean install // clear target and buildapplication
mvn exec;java -Dexec.mainClass=com.britr.simpleRestaurant.SimpleRestaurantApplication

please run the sql script in /db in mysql tool to create the sample database for the application 
