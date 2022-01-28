package com.aqua.csv;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TestRemoveDuplicate {

    public static void main(String[] args) throws IOException {
        File d = new File(System.getProperty("user.home") + "/CSV");
        for (File f: Objects.requireNonNull(d.listFiles())) {
            if (f.isDirectory()) continue;
            System.out.println("Processing file: " + f.getName());
            DataTransformation.removeDuplicates(f, new File("/tmp/updated-" + f.getName()), 2);
        }
    }
}
