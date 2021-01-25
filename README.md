# Scrapper tool

I see multiple challenges in this task:
1) how to read multiple files simultaneously or few Big files?
Basically based on input files we should decide on concurrency strategy.

1.1) Single threaded

1.2) Thread per file (I've use this)

1.3) Producer-Consumer with multiple producers, consumers 


2) Stop Words functionality - if we have a lot of words as exclusion, it can increase time complexity.
I've choose HashSet functionality, but for big input we can optimize it to Map<Pair<[first char], [word size]>, Set<String>> (almost bucketing system) to speed up look up in stop words  
 

## Build

Use maven to build a jar or use built jar in target 

```bash
mvn clean package
```

## Usage
-S [comma separated words]

-C enabling character counter

-F [file1, file2 ...] files to parse 

Examples
```bash
java -jar target/scraper.jar -S result,the -F 1.txt
java -jar target/scraper.jar -S result -F *.txt -C
```
