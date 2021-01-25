package org.genco;

import org.genco.processing.CharCountStrategy;
import org.genco.processing.ProcessingStrategy;
import org.genco.processing.WordCountStrategy;
import org.genco.task.Job;

import java.util.concurrent.ExecutionException;

/**
 * Scrapper main class
 */
public class App {

    public static void main(String[] args) {
        try {
            CmdArgsResolver.Arguments arguments = new CmdArgsResolver().parse(args);
            if (arguments.getFiles().isEmpty()) {
                System.out.println("No files to parse, exiting");
                System.exit(0);
            }

            parse(arguments);
        } catch (Exception ex) {
            System.out.println("Encountered with issue during processing a file(s), please check your input file(s)");
        }
        System.exit(0);
    }

    private static void parse(CmdArgsResolver.Arguments arguments) throws ExecutionException, InterruptedException {
        ProcessingStrategy processingStrategy = arguments.isCharacterCount() ? new CharCountStrategy() : new WordCountStrategy();
        System.out.printf("Resolved %d file(s) with %s stop words and %s counter%n", arguments.getFiles().size(), arguments.getExclusionWords(), arguments.isCharacterCount() ? "character" : "word");

        System.out.println("Start counting...");

        new Job(arguments.getFiles(), arguments.getExclusionWords(), processingStrategy).execute();
    }

}
