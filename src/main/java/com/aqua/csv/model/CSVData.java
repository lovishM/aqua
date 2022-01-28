package com.aqua.csv.model;

import java.util.*;

public final class CSVData {

    private final LinkedList<Row> rows = new LinkedList<>();

    public boolean hasHeader() {
        return rows.size() > 0;
    }

    public CSVData addToCurrentRow(String data) {
        rows.getLast().addCell(data);
        return this;
    }

    public CSVData newRow() {
        rows.add(new Row());
        return this;
    }

    public CSVData newRow(String data) {
        Row row = new Row();
        row.addCell(data);
        rows.add(row);
        return this;
    }

    public CSVData addRow(Row row) {
        this.newRow();
        Row lastRow = this.rows.getLast();
        row.cells.forEach(lastRow::addCell);
        return this;
    }

    public CSVData removeLastRow() {
        rows.removeLast();
        return this;
    }

    public List<Row> getDataRows() { return Collections.unmodifiableList(rows.subList(1, rows.size())); }
    public List<String> getHeaders() { return Collections.unmodifiableList(rows.get(0).cells); }
    public long size() { return rows.size(); }

    public static final class Row {

        private final List<String> cells = new ArrayList<>();

        public Row addCell(String cell) {
            cells.add(cell);
            return this;
        }

        public List<String> getCells() { return Collections.unmodifiableList(cells); }
    }
}
