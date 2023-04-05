Java performance training platform in a Spring Boot uber jar; comes with:
* sample java system under test (aka SUT) with sample perf defects
* performance monitoring from [glowroot.org](https://glowroot.org/)
* [h2](https://h2database.com/) db with 3+ million rows
* [wiremock](https://wiremock.org/) for http/s backend.
* [JMeter](https://jmeter.apache.org/) load generator (aka LG) 
* Two sample java performance scenario-puzzles with performance questions

All you need is a 1.8+ JDK (a JRE alone will not suffice).  

Each scenario-puzzle takes 15-ish minutes and comes with special 'click-to-fail' hyperlinks that launch various java workloads on your own box to help you understand the plight of a development team in some kind of a performance pickle.

# System Requirements
The load-test-in-a-box uber jar is 400-ish mb. When launched, it unpacks the SUT to $HOME/.load-test-in-a-box (or %USERPROFILE% for MS-Windows).
System Requirements:
* 2 CPU cores
* 2g available disk
* 3g available RAM
* 1.8+ JDK (a JRE alone will not suffice) with java in the PATH
* Tested on MS-Win and Mac, but Linux should work too…..pls file a bug report with any problems.
* Currently Chrome has been tested but Firefox does NOT currently work.  See [this bug](https://github.com/eostermueller/load-test-in-a-box/issues/92) for details

# Getting Started
1. [Download](https://github.com/eostermueller/load-test-in-a-box/releases/download/0.0.4/load-test-in-a-box_agent-0.0.4.jar)  the uber jar file.
2. Open a terminal or cmd.exe to the folder with the uber jar.  Using your own 1.8+ JDK, launch the uber jar like this:
   ```java -jar load-test-in-a-box_agent-0.0.4.jar``` 
3. Navigate to http://localhost:8675 in your favorite browser.
4. Click the check boxes to launch the SUT and the LG, will take a minute or so to launch.
5. Click on the "Markdown Content" and select one of the two sample performance scenarios.
6. Read the scenario and click on the hyperlinks to run the scenario code on your own machine.
7. To understand what's going on under the covers open the pre-packaged glowroot in your favorite browser at http://localhost:12675
8. To use JDK performance tools (jstack, jmap, jcmd, etc...) you'll need the process ID of the SUT, which you can find by hovering over 'SUT' health indicator in the bottom left of the main UI (http://localhost:8675)

The above should be enough to get you started.
For a little more background, here's a 6 minute video that shows the process:
[![load-test-in-a-box](http://img.youtube.com/vi/Ck7REKh3E6w/0.jpg)](https://youtu.be/Ck7REKh3E6w "load-test-in-a-box")

# Use Cases
The load-test-in-a-box is:
* Easy way to provide consistency when conducting interviews or hands-on training for java performance skills.
* Benchmark performance of competing java code designs
* Great way to share/analyze performance during a code review.
* Easy way to distribute source-code-under-load with a published book.

# How it Works
The SUT in load-test-in-a-box has a simple [feature flag](https://www.optimizely.com/optimization-glossary/feature-flags/) implementation which is a singleton that lists the java code that currently runs when load is applied to [the SUT's only url/entrypoint](https://github.com/eostermueller/load-test-in-a-box_sut_sample/blob/master/src/main/java/com/github/eostermueller/tjp2/rest/WorkloadController.java#L65-L80).

When reading through the scenario puzzle, the user clicks on a 'click-to-fail' link and it configures the singleton to run code that illustrates that part of the puzzle scenario.
For example, if the scenario says that the team can't figure out why the system is so slow, clicking on the 'click-to-fail' hyperlink actually runs the slow code on the end user's workstation, so the user has a chance to learn how to troubleshoot and discover the root cause of the slow code.

# Links

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

