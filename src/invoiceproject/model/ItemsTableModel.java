
package invoiceproject.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class ItemsTableModel extends AbstractTableModel {
    
    private ArrayList<Items> items;
    private String[] columns = {"No.", "Item Name", "Item Price","Count","Item Total"};

    public ItemsTableModel(ArrayList<Items> items) {
        this.items = items;
    }
    
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int cName) {
        return columns[cName];
    }

    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Items item = items.get(rowIndex);
        switch (columnIndex){
            case 0: return item.getNum();
            case 1: return item.getItems();
            case 2: return item.getPrice();
            case 3: return item.getCount();
            case 4: return item.getItemTotal();
            default : return ("");
        }
    }
    
}
