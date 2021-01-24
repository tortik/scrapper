package org.genco.processing;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public interface ProcessingStrategy {

    Pattern WORD = Pattern.compile("[a-zA-Z'’-]+");

    int count(String line, Predicate<String> predicate);

}
