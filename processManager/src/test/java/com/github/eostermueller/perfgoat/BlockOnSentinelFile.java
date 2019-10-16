package com.github.eostermueller.perfgoat;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @st0lenFr0m: https://codereview.stackexchange.com/questions/84799/java-function-that-blocks-until-a-specific-file-is-deleted
 * @author erikostermueller
 *
 */
public class BlockOnSentinelFile {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private final Path killFile;
    public static void main(String args[]) throws IOException {
    	if (args.length != 1) 
    		dispUsage();
    	else {
        	BlockOnSentinelFile blocker = new BlockOnSentinelFile("/tmp/foo");
        	blocker.block();
    	}
    }

    private static void dispUsage() {
		System.out.println("java com.github.eostermueller.perfgoat.BlockOnSentinelFile <path.to.my.file>");
		System.out.println("The console will block/hang until the specified file is deleted.");
	}

	public BlockOnSentinelFile(String killFilePath) throws IOException {
        killFile = Paths.get(killFilePath).toAbsolutePath();
        Files.deleteIfExists(killFile);
        // create entire directory tree, if possible, to create our watch file
        // in.
        try {
        	Files.createDirectories(killFile.getParent());
        } catch (FileAlreadyExistsException e) {
        	//no skin off my back if this already exists, so don't log/complain.
        }
        Files.createFile(killFile);
    }

    public void end() throws IOException {
        Files.deleteIfExists(killFile);
    }

    public void block() {
        try (WatchService watcher = FileSystems.getDefault().newWatchService()) {
            final WatchKey key = killFile.getParent().register(watcher,
                    StandardWatchEventKinds.ENTRY_DELETE);

            // stall until the game is supposed to end
            // reset key to allow new events to be detected
            while (key.reset()) {

                // wait for a file to be deleted (or an overflow....)
                if (key != watcher.take()) {
                    throw new IllegalStateException(
                            "Only our key is registered, only it should be taken");
                }

                // now, we know something has changed in the directory, all we
                // care about though, is if our file exists.
                if (!Files.exists(killFile)) {
                    return;
                }

            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            // propogate an interrupt... we can't handle it here.....
            // just let the file be removed, and we die....
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } finally {
            try {
                Files.deleteIfExists(killFile);
            } catch (IOException e) {
                // unable to delete the sentry file.....
                System.out.println("Unable to print kill file.");
            }
        }
    }}
