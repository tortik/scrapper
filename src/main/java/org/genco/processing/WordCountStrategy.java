package org.genco.processing;

import java.util.function.Predicate;
import java.util.regex.Matcher;

public class WordCountStrategy implements ProcessingStrategy {

    public int count(String line, Predicate<String> exclusion) {
        Matcher matcher = WORD.matcher(line);
        int counter = 0;
        while (matcher.find()) {
            String word = matcher.group();
            if (!exclusion.test(word)) counter++;
        }
        return counter;
    }
}
