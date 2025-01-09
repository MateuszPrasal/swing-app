# Students App

### Files description

- **App.java** - application main file
- **ui/MainFrame.java** - main screen of application
- **ui/AddStudentFrame.java** - add student screen
- **ui/EditStudentFrame.java** - edit student screen
- **services/StudentManager.java** - StudentManager interface
- **services/StudentManagerImpl.java** - implementation of StudentManager
- **models/Student.java** - Student database model class
- **interfaces/*.java** - interface classes which are used in app
- **config/AppConfig.java** - app config static fields

### Building JAR

```
mvn clean compile package
```

then `target/StudentsApp.jar` will be available

We can also build JAR using IntelIJ Idea - we have to choose

- clean
- compile
- package

commands from Maven tab

### Running app

``` 
java -jar StudentsApp.jar
```

### Requirements

- Java 17
- Maven