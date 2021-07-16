# IMS Project

An Inventory Management System that runs on the command line

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.


### Prerequisites

- [Java JDK 8+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [MySQL 8.0](https://dev.mysql.com/downloads/installer/)
- [Apache Maven 3.8](https://maven.apache.org/download.cgi)

Ensure that Java and Maven are added to your PATH
[(e.g. for Java)](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/)

You can check that you have installed java and maven by running:
```
java -version
```
and
```
mvn -version
```
in the terminal of your choice

### Installing

1. Fork this repository
2. Clone the code onto your local machine

```
git clone <url>
```

3. Ensure that the db.properties files in src/main/resources and src/test/resources folders match the details of your MySQL server
4. Run `mvn install` in the root folder (where this README is)

End with an example of getting some data out of the system or using it for a little demo

You can now run the program either in a Java IDE (such as eclipse)
Or:
```
mvn clean
mvn package
cd target/
java -jar ims-0.0.1-jar-with-dependencies.jar
```

## Running the tests

Explain how to run the automated tests for this system. Break down into which tests and what they do

### Unit Tests 

These will test the individual classes of the project and check that methods in that class have the intended behaviour

```
mvn test
```

### Integration Tests
TODO

### And coding style tests
TODO

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Chris Perrins** - *Initial work* - [christophperrins](https://github.com/christophperrins)
* **David Barringer** - *Database access and command line interface*

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

*For help in [Choosing a license](https://choosealicense.com/)*

## Acknowledgments

* JHarry444 for the template file

## Jira Roadmap
[Can be found here](https://dbarringer.atlassian.net/jira/software/projects/IMS/boards/3/roadmap?selectedIssue=IMS-1)
