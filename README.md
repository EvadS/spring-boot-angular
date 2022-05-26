# jwt-spring-boot-angular-scaffolding

This project is a multi-module application, using Spring Boot for the backend and Angular for the frontend. The project can be built into a single jar file using Maven. You can also run the modules separately during development.

## Getting Started

You can build the application with:
```bash
$ mvn clean install
```

### Sign up request

You need to create a test user:

* path: ```http://localhost:8080/api/users```

* body:

```json
{
    "userCredentials": {
        "username": "user",
        "password": "test"
    }
}
```


### get all query body 

images like ball
```
{
  "pageNumber": 0,
  "pageSize": 10,
  "sortOrder": {
    "ascendingOrder": [
      "title"
    ]   
  },
  "joinColumnProps" : [
  {
    "joinColumnName" : "labels",
    "searchFilter" : {
      "operator": "LIKE",
      "property": "name",
      "value": "ball"
    }
   }
  ]
}
```



## Running tests

Run all backend tests with the following command in the root directory:
```bash
$ mvn test
```
Run all frontend tests with the following command in the `frontend/src/main/angular` directory:
```bash
$ ng test
```

## Built With

* Java 11
* [Spring Boot 2.1.5](https://start.spring.io/)
* [Angular 9](https://angular.io/)
* [Bootstrap 4](https://getbootstrap.com/)
* [Maven](https://maven.apache.org/)
* [frontend-maven-plugin](https://github.com/eirslett/frontend-maven-plugin)
* [npm 6.13.7](https://github.com/npm/cli)
* [Node.js v12.14.1](https://nodejs.org/dist/latest-v10.x/docs/api/)

## Screenshots


