package com.github.eostermueller.tjp.launcher.agent;

import java.util.EventListener;
/**
 * Most needs, like for tracking start/stop of a web application, will be met using stateHasChanged().
 * But when every line of stdout matters, like for stdout of a load generator (like jmeter) 
 * the processItem() method will be called.  Just provide a null implementation if you don't need it.
 * 
 * <pre>
summary +    41 in  15.4s =    2.7/s Avg:  2234 Min:   383 Max:  6974 Err:     0 (0.00%)
summary +    57 in  21.5s =    2.6/s Avg:  2548 Min:   618 Max:  4528 Err:     0 (0.00%)
summary =    98 in  32.5s =    3.0/s Avg:  2416 Min:   383 Max:  6974 Err:     0 (0.00%)
summary +   108 in  21.8s =    5.0/s Avg:  1291 Min:   229 Max:  6317 Err:     0 (0.00%)
summary =   206 in  52.5s =    3.9/s Avg:  1827 Min:   229 Max:  6974 Err:     0 (0.00%)
</pre> 
 * 
 *  ...which was taken from https://jmetertutorialblog.wordpress.com/2015/10/20/jmeter-non-gui-mode/
 * 
 * @author erikostermueller
 *
 */
public interface StdoutListener  extends EventListener {
	void processLine(ProcessKey processKey, String line);
}
