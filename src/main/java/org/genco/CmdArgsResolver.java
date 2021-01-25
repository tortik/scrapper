package org.genco;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CmdArgsResolver {

    public Arguments parse(String[] args) {
        int cur = 0;
        int last = args.length - 1;
        Arguments arguments = new Arguments();
        while (cur <= last) {
            String arg = args[cur];
            if (arg.equals("-F")) {
                cur++;
                while (cur <= last && !(arg = args[cur]).startsWith("-")) {
                    arguments.addFile(arg);
                    cur++;
                }
            }
            if (arg.equals("-C")) {
                arguments.setCharacterCount(true);
            }
            if (arg.equals("-S")) {
                arg = args[++cur];
                arguments.setExclusionWords(arg);
            }
            cur++;
        }
        return arguments;
    }

    public static class Arguments {
        private boolean characterCount;
        private Set<String> exclusionWords = Collections.emptySet();
        private List<Path> files = new ArrayList<>();

        public boolean isCharacterCount() {
            return characterCount;
        }

        public void setCharacterCount(boolean characterCount) {
            this.characterCount = characterCount;
        }

        public Set<String> getExclusionWords() {
            return this.exclusionWords;
        }

        public void setExclusionWords(String exclusionWords) {
            this.exclusionWords = Optional.ofNullable(exclusionWords)
                    .map(it -> it.split(","))
                    .map(arr -> Arrays.stream(arr).collect(Collectors.toSet()))
                    .orElse(Collections.emptySet());
        }

        public List<Path> getFiles() {
            return this.files;
        }

        public void addFile(String file) {
            Path path = Paths.get(file);
            if (!path.toFile().exists()) {
                System.out.printf("File %s not exist%n", path);
                throw new IllegalArgumentException("Can't resolve path " + path);
            }
            this.files.add(path);
        }
    }

}
