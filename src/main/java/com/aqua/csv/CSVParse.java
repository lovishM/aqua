package com.aqua.csv;

import com.aqua.csv.model.CSVData;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CSVParse {

    public static CSVData parseFile(File f) throws IOException {

        CSVData csvData = new CSVData();
        csvData.newRow();

        boolean quoteOpen = false;
        try (FileReader reader = new FileReader(f)) {

            int c;
            int lastC = -1;

            StringBuilder cell = new StringBuilder();
            while ((c = reader.read()) != -1) {
                if (c == '\n') {
                    if (lastC == '\r') {
                        cell.append((char) c);
                    } else {
                        quoteOpen = false;
                        csvData.addToCurrentRow(cell.toString());
                        cell = new StringBuilder();

                        csvData.newRow();
                    }
                } else {
                    if (c == '"') {
                        quoteOpen = !quoteOpen;
                    } else if (c == ',') {
                        if (!quoteOpen) {
                            csvData.addToCurrentRow(cell.toString());
                            cell = new StringBuilder();
                            lastC = c;
                            continue;
                        }
                    }
                    cell.append((char) c);
                }
                lastC = c;
            }
        }

        // Since last row has no data
        return csvData.removeLastRow();
    }
}
