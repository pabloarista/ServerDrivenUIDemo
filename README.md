# ServerDrivenUIDemo
Android Server Driven UI Demo

In order for the server project to run properly you must have spring boot installed.

At the time of this writing, it requires JAVA 17. This is fine to use with Android since we set it to use the max JAVA it supports.

This project can run from Android Studio.

The only weird quirk is after running the Android app, the spring boot app no longer has the option to run without debugging.

So debugging both applications is not a problem.

More instructions to come in the future, but basically after setting up the project just modify the xml files (test.xml and main.xml) using the structure of Component.

There's a UML Class Diagram in [diagrams/uml/class-diagrams/SDUI-CD.xml](diagrams/uml/class-diagrams/SDUI-CD.xml). This was created using [draw.io](https://draw.io).

On the Android app just hit refresh after you modify the XML file. You must save the file after modifying.

Android Studio does NOT save it for you since it's being read dynamically and without rebuilding.
