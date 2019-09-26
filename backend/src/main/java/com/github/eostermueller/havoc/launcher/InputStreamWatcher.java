package com.github.eostermueller.havoc.launcher;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.github.eostermueller.havoc.PerfGoatException;

/**
 * @stolenFrom https://examples.javacodegeeks.com/core-java/lang/processbuilder/java-lang-processbuilder-example/
 * @author erikostermueller
 *
 */
public class InputStreamWatcher extends Thread {
	private StdoutStateChanger stdoutStateChanger = null;
    private InputStream inputStream = null;
	private ProcessKey processKey = null;
	
    public InputStreamWatcher(
    		InputStream inputStream,
    		StdoutStateChanger stdoutStateChanger) {
    	this.stdoutStateChanger = stdoutStateChanger;
        this.inputStream = inputStream;
        
    }
    public void run() {
        Scanner br = null;
        try {
            br = new Scanner(new InputStreamReader(inputStream));
            String line = null;
            while (br.hasNextLine()) {
                line = br.nextLine();

                if (line!=null) {
                	if (this.stdoutStateChanger!=null)
                		this.stdoutStateChanger.evaluateStdoutLine(line);
                }
            }
        } catch (PerfGoatException e) {

			e.printStackTrace();
		} finally {
            br.close();
        }
    }
}
