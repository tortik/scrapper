package org.genco.processing;

import java.util.function.Predicate;
import java.util.regex.Matcher;

public class CharCountStrategy implements ProcessingStrategy {

    public int count(String line, Predicate<String> predicate) {
        Matcher matcher = WORD.matcher(line);
        int counter = line.length();
        while (matcher.find()) {
            String word = matcher.group();
            if (predicate.test(word)) counter -= word.length();
        }
        return counter;
    }
}
