package org.genco.task;

import org.genco.processing.ProcessingStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;

public class FileReaderTask {
    private final Predicate<String> exclusionWord;
    private final ProcessingStrategy processingStrategy;
    private final Path filePath;

    public FileReaderTask(Predicate<String> exclusionWord, ProcessingStrategy processingStrategy, Path filePath) {
        this.exclusionWord = exclusionWord;
        this.processingStrategy = processingStrategy;
        this.filePath = filePath;
    }

    public int count() throws IOException {
        int fileCount = Files.lines(filePath)
                .map(l -> processingStrategy.count(l, exclusionWord))
                .reduce(0, Integer::sum);
        System.out.printf("Count for file: %s is %d\n", filePath.getFileName(), fileCount);
        return fileCount;
    }
}
