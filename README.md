# ServerDrivenUIDemo
Android Server Driven UI Demo

In order for the server project to run properly you must have [Spring Boot 3.0.0](https://docs.spring.io/spring-boot/docs/3.0.0/reference/html/getting-started.html) installed. This requires [JAVA 17](https://developer.ibm.com/languages/java/semeru-runtimes/downloads/) (ensure you download the correct version for your platform/architecture. This is fine to use with Android since we just set it to use JAVA 11. Although the latest Android Studio Preview (Flamingo) supports JAVA 17.

This project can run on Android Studio. So debugging both applications is not a problem. No need to have 2 different instances of Android Studio open.

More instructions to come in the future, but basically after setting up the project just modify the [XML files](ServerDrivenUIDemo.Web/src/main/resources/) ([test.xml](ServerDrivenUIDemo.Web/src/main/resources/test.xml) and [main.xml](ServerDrivenUIDemo.Web/src/main/resources/main.xml)) using the structure of Component. There's a **UML Class Diagram** file named [SDUI-CD.xml](diagrams/uml/class-diagrams/SDUI-CD.xml) that shows the structure of the payload the client accepts. This was created using [draw.io](https://app.draw.io).

On the Android app just tap on either **Main** or **Test** after you modify the [XML files](ServerDrivenUIDemo.Web/src/main/resources/). You must save the file after modifying the file because Android Studio does NOT save it for you since it's being read dynamically and without rebuilding.
