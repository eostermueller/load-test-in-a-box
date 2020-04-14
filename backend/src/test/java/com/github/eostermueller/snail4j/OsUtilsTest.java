package com.github.eostermueller.snail4j;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.eostermueller.snail4j.OsUtils.OsResult;

class OsUtilsTest {

	/**
	 * Example output from netstat windows command, run on MS-Win-10 Pro.
	  <pre>
C:\Users\eoste>netstat -ano -p UDP

Active Connections

  Proto  Local Address          Foreign Address        State           PID
  UDP    0.0.0.0:500            *:*                                    3964
  UDP    0.0.0.0:4445           *:*                                    752
  UDP    0.0.0.0:4500           *:*                                    3964
  UDP    0.0.0.0:5050           *:*                                    10268
  UDP    0.0.0.0:5353           *:*                                    2080
  UDP    0.0.0.0:5353           *:*                                    9004
  UDP    0.0.0.0:5353           *:*                                    9004
  UDP    0.0.0.0:5355           *:*                                    2080
  UDP    0.0.0.0:6646           *:*                                    5516
  UDP    0.0.0.0:54161          *:*                                    9116
  UDP    0.0.0.0:54815          *:*                                    9116
  UDP    0.0.0.0:55375          *:*                                    9116
  UDP    0.0.0.0:57339          *:*                                    11888
  UDP    0.0.0.0:58321          *:*                                    9116
  UDP    0.0.0.0:62203          *:*                                    9116
  UDP    127.0.0.1:1900         *:*                                    16404
  UDP    127.0.0.1:49664        *:*                                    4620
  UDP    127.0.0.1:61753        *:*                                    16404
  UDP    192.168.1.224:137      *:*                                    4
  UDP    192.168.1.224:138      *:*                                    4
  UDP    192.168.1.224:1900     *:*                                    16404
  UDP    192.168.1.224:61752    *:*                                    16404

	 </pre>
	 */
	@Test
	@EnabledOnOs({OS.WINDOWS})
	void testWindowsCommandExecution() {
		String myWindowsCommand = "netstat -ano -p UDP";
		
		OsResult osResult = OsUtils.executeProcess_mswin(myWindowsCommand);
		
		assertEquals(0,osResult.exitCode);
		
		int foundUDP = osResult.stdout.indexOf("UDP");
		assertTrue( foundUDP > -1  );

		int foundColon = osResult.stdout.indexOf(":");
		assertTrue( foundColon > -1  );
		System.out.println(osResult.stdout);
//		int found4445 = osResult.stdout.indexOf(":4445");
//		assertTrue( found4445 > -1  );
	}
	
	/**
	 * Same story as the above test.  lots of work to do before this can be enabled.
	 */
	@Disabled
	@Test
	void canDetectWhetherUdpIsActive() {
		
		assertTrue( OsUtils.isUdpPortActive(4455));
	}
	
	/**
	 * @stolenFrom: https://unix.stackexchange.com/a/27873
	 * @throws Snail4jException
	 */
	@Test
	@EnabledOnOs({OS.MAC})
	void testMacCommandExecution() throws Snail4jException {
		
		//WARNING:  Without -n and -P, it takes a long time
		String myMacCommand = "sudo lsof -iUDP -P -n";
		OsResult osResult = OsUtils.executeProcess_bash(myMacCommand,null);
		
		assertEquals(0,osResult.exitCode);
		
		int foundUDP = osResult.stdout.toLowerCase().indexOf("UDP");
		assertTrue( foundUDP > -1  );

		int foundColon = osResult.stdout.indexOf(":");
		assertTrue( foundColon > -1  );
		
	}
	/**
	 * 
	 * taken from here:  https://www.tecmint.com/20-netstat-commands-for-linux-network-management/
	 <pre>
# netstat -au

Active Internet connections (servers and established)
Proto Recv-Q Send-Q Local Address               Foreign Address             State
udp        0      0 *:35036                     *:*
udp        0      0 *:npmp-local                *:*
udp        0      0 *:mdns                      *:*	 
	 </pre> 
	 */
	@Test
	@EnabledOnOs({OS.LINUX})
	void testLinuxCommandExecution() {
		String linuxCommand = "netstat -au";
	}

}
