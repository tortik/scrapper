package org.genco.task;

import org.genco.exclusion.ExclusionWordPredicate;
import org.genco.processing.ProcessingStrategy;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class Job {
    private final List<Path> paths;
    private final Set<String> exclusions;
    private final ProcessingStrategy processingStrategy;
    private final CompletionService<Integer> completionService;
    private final ExecutorService executorService;

    public Job(List<Path> paths, Set<String> exclusions, ProcessingStrategy processingStrategy) {
        this.paths = paths;
        this.exclusions = exclusions;
        this.processingStrategy = processingStrategy;
        this.executorService = executorService(paths);
        this.completionService = new ExecutorCompletionService<>(executorService);
    }

    public void execute() throws InterruptedException, ExecutionException {
        Predicate<String> wordExclusion = new ExclusionWordPredicate(exclusions);
        paths.forEach(path -> completionService.submit(
                () -> new FileReaderTask(wordExclusion, processingStrategy, path).count()));

        AtomicInteger count = new AtomicInteger(0);
        for (int i = 0; i < paths.size(); i++) {
            Future<Integer> res = completionService.take();
            count.addAndGet(res.get());
        }
        System.out.printf("Total Count is %s%n", count.get());

    }

    private ExecutorService executorService(List<Path> paths) {
        int cores = Runtime.getRuntime().availableProcessors();
        final ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "file-parser-" + this.threadNumber.getAndIncrement());
            }
        };
        return Executors.newFixedThreadPool(Math.min(cores, paths.size()), threadFactory);
    }

    public void shutdown(){
        executorService.shutdownNow();
    }

}
