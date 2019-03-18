# spring-boot-angular-maven

Run Angular under Spring Boot, with "java -jar" deployment.
Comes with Angular 8 hello world app.

# Features
* Spring Security enabed by default.
* running ```java -jar backend/target/backend-0.0.1-SNAPSHOT.jar``` launches both Angular and Spring Boot under Tomcat 9.0.16 on port 8090.
* Includes Maven wrapper (mvnw and mvnw.cmd) that will auto-install Maven for you.
* Frontend (Angular) and Backend (Spring Boot) code is separated into folders named ```frontend``` and ```backend```.
* Includes .gitignore that keeps node_modules and other boilerplate stuff from showing up in git.
* Client-side testing isn't enabled.
* Thanks to [this githubber](https://github.com/swathisprasad). This code started as a fork of [this project](https://github.com/swathisprasad/spring-boot-angular6-maven-project)....but I've since cut the ties.

# Step 01: Install npm and Angular CLI

Make sure npm and the Angular CLI are installed.
You can install these by following everything up to and including "Step 1" in the [Angular Quick Start](https://angular.io/guide/quickstart)


# Step 02: Build and Launch

After you download/clone this repo, build the project like this:

```./mvnw clean install```

...which will create this:

```backend/target/backend-0.0.1-SNAPSHOT.jar```

Then to run: 

```cd backend```

...and either this:

```../mvnw spring-boot:run```

or this:

```java -jar target/backend-0.0.1-SNAPSHOT.jar```


# Step 03: Logging On / Spring Security
Once you get the ```Completed initialization in N ms``` message, navigate to ```localhost:8090``` in your browser.
Spring Security is enable by default, so every time you stat the app you'll be presented with screenshot below.  The user is just "user" and the pw is in the stdout log.  

The password for 'user' will be displayed in the stdout like this:
```
Using generated security password: 6ba401cd-01cb-4e44-bf16-3892bfc90115
```

For mor details, check [here](https://www.websparrow.org/spring/spring-security-how-to-change-default-username-and-password) or [here](https://docs.spring.io/spring-boot/docs/2.0.0.RELEASE/reference/html/boot-features-security.html).


![Spring Security Logon](https://user-images.githubusercontent.com/175773/52928769-0bf67680-3307-11e9-86aa-9574700ddf3b.png)




Once you log in successfully, you'll see this:

![Bare bones Angular](https://user-images.githubusercontent.com/175773/52928834-5b3ca700-3307-11e9-969c-529b1667e12a.png)

# Run ANY Angular App In Spring Boot / in under 5 minutes
Want to run your favorite Angular project under Spring Boot, to get great ootb security?
This should take no more than 5 minutes.
1. Clone this project
1. Delete all files/childfolders in this project's frontend/src/main/web.  
1. Copy all files/childfolders in the root (folder with package.json) of your Angular project into that same location (frontend/src/main/web).  When finished, your angular project's package.json should be here:  ```frontend/src/main/web/package.json```.
1. Tell the backend build where to find the angular application. 
Specifically, configure the ```<resource>``` element in ```backend/pom.xml``` to be the ```"outputPath"``` in your ```frontend/src/main/web/angular.json```, as shown below.  Note that ```dist/demo-app``` in angular.json is a path relative to ```web``` folder, and the text in the ```<resource>``` element in ```backend/pom.json``` is a full path, starting with ```${project.parent.basedir}```  

So your job is to change the path in the blue rectangle, and you have to change it so that it points to path in the oval.  Got it?

![How to update backend/pom.xml](https://user-images.githubusercontent.com/175773/54485461-e053a700-4846-11e9-8b60-93cb346df450.png)

1. Run steps in "Step 02: Build and Launch"
1. Done!


# Next Steps:  Angular Material

All Angular CLI commands should be run from this folder:  ```frontend/src/main/web```

With this code as a base, if you follow [these instructions](https://robferguson.org/blog/2018/11/05/getting-started-with-angular-material/), you can easily add side navigation, like this:

![Angular Material Side Nav](https://user-images.githubusercontent.com/175773/52995305-d5d8f580-33df-11e9-8856-fb5bea122854.png)

...but I didn't follow those instructions exactly.
Here are the variations:

1. Skip this if you've already done it: ```npm install -g @angular/cli```
1. Skip this b/c it's already a part 0f this rep0:  ```ng new serendipity```
1. Because we skipped the "ng new" step above, we missed the chance to choose scss over css.  So, Find angular.json and make sure the 'schematics' attribute looks like this: ```"schematics": { "@schematics/angular:component": { "styleext": "scss" } },```
1. Add ```<app-nav></app-nav>``` to the very top of your app.component.html.
1. Done!  Re-run your ```./mvnw clean install``` and ```../mvnw spring-boot:run``` 

# Angular Versions
Angular versions of the hello-world angular app are [here](https://github.com/eostermueller/spring-boot-angular-maven/blob/master/frontend/src/main/web/package.json).
