# librarymanager
Application to manage library main activities.


## How to run project:
* Clone or download this repository 
* Make sure you are using JDK 1.8 and Maven 3.x
* You can build the project and run the tests by running ```mvn clean package```
* Once successfully built, you can run the application by one of these two methods:

```
        java -jar target/librarymanager-v1.jar
or
        mvn librarymanager:run
```

## MySQL

Application is backed with MySQL. To setup databse for application make sure you have local MySQL server installed. 
To initialize schema use prepared scripts from ```src/main/resources/templates/scritps``` :

```
      database-schema.sql
and 
      data.sql
``` 

## About Application

Application allow to manage workflow in library. Employee have to register to be allowed to manage items, users and loans. 
There are two roles in system 'ADMIN' and 'EMPLOYEE'. Admin have additional panel to manage employees 
and activate newly registered accounts.


Here is some of what this project cover:

* MVC design pattern
* CRUD operations
* Data validation
* Registration and authentication
* Reset user password via email
* Use of Project **Lombok**


## Technology stack:
  * ***Java 8, SpringBoot 2.0.0 M6***  
  
  * **persistence**: Spring Data JPA/Hibernate
  * **persistence provider**: MySQL
  * **security**: Spring Security
  * **build**: Maven 3.5
  * **frontend**: HTML5, CSS3, Thymeleaf 3.0, JavaScript, jQuery, Bootstrap
  * **testing**: Mockito, JUnit
