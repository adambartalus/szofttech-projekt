package model;

import model.unit.Unit;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class UnitInfoTableModel extends AbstractTableModel {
    
    private final ArrayList<Unit> units;
    private final String[] colNames = new String[]{"Type", "Hp", "Speed", "Owner"};

    public UnitInfoTableModel(ArrayList<Unit> units) {
        this.units = units;
    }
    
    @Override
    public int getRowCount() {
        return units.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Unit u = units.get(rowIndex);
        if(columnIndex == 0) return u.getName();
        if(columnIndex == 1) return u.getHp() + "/" + u.getMaxHp();
        if(columnIndex == 2) return u.getSpeed();
        return u.getOwnerName();
    }
    @Override
    public String getColumnName(int i) { return colNames[i]; }
    
    @Override
    public Class getColumnClass(int column) {
        if(column == 1) return String.class;
        if(column == 2) return String.class;
        if(column == 3) return Integer.class;
        return String.class;
    }
}
