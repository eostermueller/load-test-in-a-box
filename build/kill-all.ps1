

gwmi win32_Process -Filter "CommandLine LIKE '%ApacheJMeter.jar%load-test-in-a-box%'" | Invoke-WmiMethod -Name Terminate
gwmi win32_Process -Filter "CommandLine LIKE '%org.codehaus.plexus.classworlds.launcher.Launcher%load-test-in-a-box%'" | Invoke-WmiMethod -Name Terminate
gwmi win32_Process -Filter "CommandLine LIKE '%org.h2.tools.Server -tcp -tcpPort%'" | Invoke-WmiMethod -Name Terminate
gwmi win32_Process -Filter "CommandLine LIKE '%backend-0.0.2-SNAPSHOT.jar%'" | Invoke-WmiMethod -Name Terminate
gwmi win32_Process -Filter "CommandLine LIKE '%PerformanceSandboxApp%'" | Invoke-WmiMethod -Name Terminate
gwmi win32_Process -Filter "CommandLine LIKE '%surefire%surefirebooter%'" | Invoke-WmiMethod -Name Terminate



