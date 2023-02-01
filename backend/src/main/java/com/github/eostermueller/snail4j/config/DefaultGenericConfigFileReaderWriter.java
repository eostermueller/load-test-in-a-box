package com.github.eostermueller.snail4j.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.github.eostermueller.snail4j.DefaultFactory;
import com.github.eostermueller.snail4j.Snail4jException;

/**
 * Very possible that multiple threads might write to config files concurrently, 
 * in edge cases that will only happen occasionally.
 * I do not at all expect to have sustained concurrent load reading/writing config files.
 * .....that's why I used FileChannel below instead of other techniques.
 * 
 * 
 * @author eoste
 *
 */
public class DefaultGenericConfigFileReaderWriter implements GenericConfigFileReaderWriter {

	/**
        // File write will fail if some other process/thread is writing to the same file.
	 * @throws Snail4jException 
	 * @stolenFrom: https://www.geeksforgeeks.org/filechannel-class-trylock-method-in-java-with-examples/
	 */
	@Override
	public void write(String input, Path targetFile) throws Snail4jException {
		FileChannel fc = null;
		try {
			ByteBuffer buf = ByteBuffer.wrap(input.getBytes());
            fc = FileChannel.open(
                targetFile, StandardOpenOption.WRITE,
                StandardOpenOption.CREATE);
           
            fc.tryLock(0L, Long.MAX_VALUE, false);
 
            fc.write(buf);
 
        } catch (Exception e) {
        	String message = DefaultFactory.getFactory().getMessages().exceptionWritingConfigFile(targetFile,e);
        	throw new Snail4jException(e,message);
        } finally {
            try {
            	if (fc!=null)
            		fc.close();
			} catch (IOException e) {}
        	
        }
 
	}

	/**
	 * Stolen from https://www.tabnine.com/code/java/methods/java.nio.CharBuffer/toString?snippet=5ce6e25c2fd3800004eb7fff
	 */
	@Override
	public String read(Path targetFile) throws Snail4jException {

	   	 Charset charset = Charset.defaultCharset();
	     try (FileChannel fc = FileChannel.open(targetFile)) {
	       MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
	       return  charset.decode(bb).toString();
		} catch (IOException e) {
        	String message = DefaultFactory.getFactory().getMessages().exceptionReadingConfigFile(targetFile,e);
        	throw new Snail4jException(e,message);
		}
	}

}
