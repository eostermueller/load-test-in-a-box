Spring Boot uber jar with:
* sample java system under test (SUT), 
* performance monitoring
* load generator (LG)
* 2 sample java performance scenario-puzzles with questions

All you need is a 1.8+ JDK (JRE alone will not suffice).  Designed for java performance curriculum/education.

Each scenario-puzzle takes 15-ish minutes and have special markdown hyperlinks that launch various java workloads that run on your own box to help you understand the performance scenario.

Use JDK tools (jstack, jmap, etc…) or the pre-packaged glowroot.org monitoring to understand performance of the currently running java workload.
Performance questions in the markdown test your understanding of the scenario and Java performance in general.

# How it Works
Via the uber jar's UI at http://localhost:8675 you can 
* Select which code runs under load - your selection is processed instantaneously, making for easy performance comparisons.  
* View a menu of available code....and the menu consists of all code in the SUT with a particular java annotation
* Launch monitoring, provided by glowroot.org, or just use jdk tools like jstack, jmap, jstat, jcmd, etc...
* Comes with two sample performance scenario-puzzles, delivered via Markdown, that you can change/enhance.
* Follow sample performance curriculum and interact with sample performance code.  For example:  "Click here to run this code, click there to run that code." -- (this is called 'click-to-fail').

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

