The Performance Analysis Workbench is the only Java load-test-in-a-box that (gulp) aims to educate all Java developers on software performance.
Packaged as a Spring Boot uber jar (zip actually), it enables you to re-live datacenter performance firefights on your own desktop.  The download takes forever, but it installs in less than a minute or so.

# Performance Analysis Workbench Intro

The [Quickstart](https://github.com/eostermueller/performanceAnalysisWorkbench/wiki/Quickstart). Download/install/run to run the pre-packaged java code showing performance defects and fixes.

[Run your own code](https://github.com/eostermueller/performanceAnalysisWorkbench/wiki/Run-Your-Own-Code) inside the Workbench.

[Build](https://github.com/eostermueller/performanceAnalysisWorkbench/wiki/Build) the Workbench's uber jar.

[Contribute](https://github.com/eostermueller/performanceAnalysisWorkbench/wiki/Contributing) enhancements/fixes to the Workbench.

[Road Map](https://github.com/eostermueller/performanceAnalysisWorkbench/wiki/Road-Map) shows the general direction.

# Architecture
The uber jar's browser UI (aka the 'agent') allows you to start/stop the system under test and the load generator, which are both installed to a folder on your file system (%USERPROFILE%\\.load-test-in-a-box or $HOME/.load-test-in-a-box).

![load-test-in-a-box architecture](https://user-images.githubusercontent.com/175773/204170426-54f26911-f35b-49db-9dcb-8f6183dfe8c9.jpg)

Here are a few more [Architecture](https://github.com/eostermueller/performanceAnalysisWorkbench/wiki/Architecture) details.


# Motivation
This project challenges end users (java developers) to [solicit predictions](https://blog.upperlinecode.com/stop-teaching-code-a1039983b39) on software performance experiments with main types of software defects:  
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

