/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ai.datalab.visual.impl.widget.misc;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.ai.datalab.core.Data;

/**
 *
 * @author Mohan Purushothaman
 */
public class SimpleDataModel extends AbstractTableModel implements DataDisplayer {

    private final List<SoftReference<Data>> values = new ArrayList<>();

    private String[] columnNames;

    @Override
    public int getRowCount() {
        return values.size();
    }

    @Override
    public int getColumnCount() {
        if (columnNames != null) {
            return columnNames.length;
        }
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Data data = values.get(rowIndex).get();
        if (data != null) {
            return data.getValue(columnNames[columnIndex]);
        }
        return "##NOT AVAILABLE##, removed due to memeory constraints ";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public void addData(Data... data) {
        initColumnNames(data[0]);
        for (Data d : data) {
            values.add(new SoftReference<>(d));
        }
        fireTableRowsInserted(values.size()-data.length,values.size());
    }

    @Override
    public void addData(Collection<Data> data) {
        if (!data.isEmpty()) {
            initColumnNames(data.iterator().next());
        }
        for (Data d : data) {
            values.add(new SoftReference<>(d));
        }

        fireTableRowsInserted(values.size()-data.size(),values.size());
    }

    private void initColumnNames(Data data) {
        if (columnNames == null) {
            columnNames = data.getKeyNames().toArray(new String[0]);
            fireTableStructureChanged();
        }
    }

}
