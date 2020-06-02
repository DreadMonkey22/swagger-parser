package io.swagger.v3.parser.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Finder
        extends SimpleFileVisitor<Path> {

    private Path result;

    private final Pattern pattern;
    private int numMatches = 0;

    Finder(String patternString) {
        pattern = Pattern.compile(patternString);

    }

    // Compares the glob pattern against
    // the file or directory name.
    void find(Path file) {

        String fileName = file.getFileName().toString();
        String name = fileName.substring(fileName.lastIndexOf(File.separatorChar) + 1);
        Matcher matcher = pattern.matcher(name);

        if (matcher.matches()) {
            result = file;
        }
    }

    // Invoke the pattern matching
    // method on each file.
    @Override
    public FileVisitResult visitFile(Path file,
                                     BasicFileAttributes attrs) {
        find(file);
        return CONTINUE;
    }

    // Invoke the pattern matching
    // method on each directory.
    @Override
    public FileVisitResult preVisitDirectory(Path dir,
                                             BasicFileAttributes attrs) {
        find(dir);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file,
                                           IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }

    public Path getResult(){
        return this.result;
    }
}
