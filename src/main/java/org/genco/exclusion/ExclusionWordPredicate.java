package org.genco.exclusion;

import java.util.Set;
import java.util.function.Predicate;

public class ExclusionWordPredicate implements Predicate<String> {
    private Set<String> words;

    public ExclusionWordPredicate(Set<String> words) {
        this.words = words;
    }

    @Override
    public boolean test(String s) {
        return words.contains(s);
    }
}
