# 12064 org.codehaus.plexus.classworlds.launcher.Launcher -Dsnail4j.maven.repo.passthru=-Dmaven.repo.local=/C:/Users/eoste/.snail4j/repository -Dmaven.repo.local=/C:/Users/eoste/.snail4j/repository -Dsnail4j.wiremock.port=11675 -Dsnail4j.wiremock.hostname=DESKTOP-2URE8SU -Dsnail4j.h2.port=10675 -Dsnail4j.h2.hostname=DESKTOP-2URE8SU -Dsnail4j.sut.port=9675 -Dsnail4j.glowroot.port=12675 -Dsnail4j.sut.jvmArguments=-Xmx1024m -XX:NewSize=512m -XX:MaxNewSize=512m verify
# 13440 C:\Users\eoste\AppData\Local\Temp\surefire3822489775880040991\surefirebooter4154106786673813910.jar C:\Users\eoste\AppData\Local\Temp\surefire3822489775880040991 2021-01-12T20-46-20_102-jvmRun1 surefire3172159424608202262tmp surefire_09208526673121210853tmp
# 14496 ..\backend\target\backend-0.0.2-SNAPSHOT.jar
# 14864 org.h2.tools.Server -tcp -tcpPort 10675 -tcpPassword h2-maven-plugin
# 16784 sun.tools.jcmd.JCmd
# 18712 org.codehaus.plexus.classworlds.launcher.Launcher -e -X -V -Dmaven.repo.local=/C:/Users/eoste/.snail4j/repository -Dspring-boot.run.fork=true -Dspring-boot.run.jvmArguments=-Dsnail4j.wiremock.hostname=DESKTOP-2URE8SU -Dsnail4j.h2.port=10675 -Dsnail4j.h2.hostname=DESKTOP-2URE8SU -Dsnail4j.wiremock.port=11675 -Dglowroot.agent.port=12675 -Xmx1024m -XX:NewSize=512m -XX:MaxNewSize=512m -Dspring-boot.run.agents=C:\Users\eoste/.snail4j/glowroot/glowroot/glowroot.jar -Dspring-boot.run.arguments=--server.port=9675 spring-boot:run
# 19164 org.codehaus.plexus.classworlds.launcher.Launcher -V -Dmaven.repo.local=/C:/Users/eoste/.snail4j/repository -Dsnail4j.wiremock.port=11675 clean compile wiremock:run
# 20252 com.github.eostermueller.tjp2.PerformanceSandboxApp --server.port=9675
# 7484 C:\Users\eoste\.snail4j\apache-jmeter-5.2.1\bin\ApacheJMeter.jar -n -l/C:/Users/eoste/.snail4j/jmeterFiles/load.jtl -t/C:/Users/eoste/.snail4j/jmeterFiles/load.jmx -Jsnail4j.host=DESKTOP-2URE8SU -Jsnail4j.port=9675 -Jsnail4j.durationSeconds=3600 -Jsnail4j.rampupSeconds=3 -Jsnail4j.threads=3


gwmi win32_Process -Filter "CommandLine LIKE '%ApacheJMeter.jar%snail4j%'" | Invoke-WmiMethod -Name Terminate
gwmi win32_Process -Filter "CommandLine LIKE '%org.codehaus.plexus.classworlds.launcher.Launcher%snail4j%'" | Invoke-WmiMethod -Name Terminate
gwmi win32_Process -Filter "CommandLine LIKE '%org.h2.tools.Server -tcp -tcpPort%'" | Invoke-WmiMethod -Name Terminate
gwmi win32_Process -Filter "CommandLine LIKE '%backend-0.0.2-SNAPSHOT.jar%'" | Invoke-WmiMethod -Name Terminate
gwmi win32_Process -Filter "CommandLine LIKE '%PerformanceSandboxApp%'" | Invoke-WmiMethod -Name Terminate
gwmi win32_Process -Filter "CommandLine LIKE '%surefire%surefirebooter%'" | Invoke-WmiMethod -Name Terminate



