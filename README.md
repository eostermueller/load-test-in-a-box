Java performance training platform in a Spring Boot uber jar; comes with:
* sample java system under test (SUT), 
* performance monitoring
* load generator (LG)
* 2 sample java performance scenario-puzzles with questions

All you need is a 1.8+ JDK (JRE alone will not suffice).  

Each scenario-puzzle takes 15-ish minutes and comes with special markdown hyperlinks that launch various java workloads on your own box to help you understand the performance scenario.

# System Requirements
* Uber jar is 400mb (or less), but a total of 1.5g disk available space is required.  SUT is unpacked to $HOME/.load-test-in-a-box
* 2 CPU cores
* 8g RAM
* 1.8+ JDK
* Tested on MS-Win and Mac, but Linux should work too…..pls file a bug report with any problems.

# How it Works
1. [Download](https://github.com/eostermueller/load-test-in-a-box/releases/download/0.0.3/load-test-in-a-box_agent-0.0.3.jar)  the uber jar file.
2. Open a terminal or cmd.exe to the folder with the uber jar.  Using your own 1.8+ JDK, launch the uber jar like this:
   ```java -jar load-test-in-a-box_agent-0.0.3.jar``` 
3. Navigate to http://localhost:8675 in your favorite browser.
4. Click the check boxes to launch the SUT and the LG, will take a minute or so to launch.
5. Click on the "Markdown Content" and select one of the two sample performance scenarios.
6. Read the scenario and click on the hyperlinks to run the scenario code on your own machine.
7. To understand what's going on under the covers open the pre-packaged glowroot in your favorite browser at http://localhost:12675
8. To use JDK performance tools (jstack, jmap, jcmd, etc...) you'll need the process ID of the SUT, which you can find by hovering over 'SUT' health indicator in the bottom left of the main UI (http://localhost:8675)

That should be enough to get you started.
For a little more background, here's a 15 minute video that shows the process:
[![Download and run load-test-in-a-box](https://www.youtube.com/watch?v=fz7uVe4H58o](https://www.youtube.com/watch?v=fz7uVe4H58o "Download and run load-test-in-a-box")


# Use Cases
* Train staff on Java Performance
* Benchmark performance of competing java code designs
* Great way to share/analyze performance during a code review.
* Easy way to provide consistency when conducting interviews for java performance skills.

# Links

The [Quickstart](https://github.com/eostermueller/performanceAnalysisWorkbench/wiki/Quickstart). Download/install/run to run the pre-packaged java code showing performance defects and fixes.

[Run your own code](https://github.com/eostermueller/performanceAnalysisWorkbench/wiki/Run-Your-Own-Code) inside the Workbench.

[Build](https://github.com/eostermueller/performanceAnalysisWorkbench/wiki/Build) the Workbench's uber jar.

[Contribute](https://github.com/eostermueller/performanceAnalysisWorkbench/wiki/Contributing) enhancements/fixes to the Workbench.

[Road Map](https://github.com/eostermueller/performanceAnalysisWorkbench/wiki/Road-Map) shows the general direction.

# Architecture
The uber jar's browser UI at http://localhost:8675 (the 'agent') allows you to start/stop the system under test (SUT) and the load generator(LG).  Both the SUT and LG get installed to a folder on your file system (%USERPROFILE%\\.load-test-in-a-box or $HOME/.load-test-in-a-box) at uber jar startup.

![load-test-in-a-box_architecture 02](https://user-images.githubusercontent.com/175773/210271052-7c4e7f9f-1964-4cbe-b710-f842c90f1e12.jpg)


Here are a few more [Architecture](https://github.com/eostermueller/performanceAnalysisWorkbench/wiki/Architecture) details.


# Motivation
This project challenges end users (java developers) to [solicit predictions](https://blog.upperlinecode.com/stop-teaching-code/) on software performance experiments with main types of software defects:  
 * Persistence
 * Alien systems
 * Threads
 * Heap

(the acronym is P.A.T.H.)

 Helpful predictions to solicit from the end user:

 * Does performance improve with fewer or more invocations to the database?
 * How large must a payload be to degrade transmission time?
 * How much delay is required to slow down code with a sync block?

# Goals
* Quick and easy "[No Friction](https://github.com/eostermueller/snail4j/wiki/No-Friction-Distribution)" distribution.
* Provides self-paced, hands-on experience troubleshooting java performance.
* Great for a performance code review.
* Testing environment for any monitoring solution, to quickly test resource overhead of monitoring, confirm that monitoring can detect perf defects.

