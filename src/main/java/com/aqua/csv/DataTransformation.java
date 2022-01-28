package com.aqua.csv;

import com.aqua.csv.model.CSVData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DataTransformation {

    public static void removeDuplicates(File fromFile, File toFile, int colIndex) throws IOException {

        CSVData existingData = CSVParse.parseFile(fromFile);
        CSVData processedData = new CSVData();

        processedData.newRow();
        existingData.getHeaders().forEach( processedData::addToCurrentRow);

        System.out.println("Total rows before transformation: " + existingData.size());
        Set<String> cache = new HashSet<>();
        for (CSVData.Row row: existingData.getDataRows()) {
            List<String> cells = row.getCells();
            if (cells.size() < colIndex) {
                throw new RuntimeException("Index provided for removing duplicates exceeds the row length [ provided: "
                        + colIndex + ", found: " + cells.size() + "]");
            }

            String data = cells.get(colIndex);
            data = data.trim().toLowerCase(Locale.ROOT);
            if (!cache.contains(data)) {
                cache.add(data);
                processedData.addRow(row);
            }
        }

        System.out.println("Total rows after transformation: " + processedData.size());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(toFile))) {
            for (CSVData.Row row: processedData.getRows()) {
                writer.write(String.join(",", row.getCells()));
                writer.newLine();
                writer.flush();
            }
        }
    }
}
