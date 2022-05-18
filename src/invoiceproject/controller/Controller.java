
package invoiceproject.controller;

import invoiceproject.model.Invoice;
import invoiceproject.model.InvoicesTableModel;
import invoiceproject.model.Items;
import invoiceproject.model.ItemsTableModel;
import invoiceproject.view.InvoiceFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Controller implements ActionListener, ListSelectionListener {
    
    private InvoiceFrame frame;
    public Controller (InvoiceFrame frame){
        this.frame = frame ;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actioncommand = e.getActionCommand();
        System.out.println("Action "+actioncommand);
        switch (actioncommand){
            case "Load File":
                loadFile();
                break;
            case "Save File":
                saveFile();
                break;
            case "Create New Invoice":
                createNewInvoice();
                break;           
            case "Delete Invoice":
                deleteInvoice();
                break;          
            case "Create New Item":
                createNewItem();
                break; 
            case "Delete Item":
                deleteItem();
                break;           
    }    
}
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = frame.getInvoiceTable().getSelectedRow();
        if (selectedIndex !=-1){
        Invoice currentInovice = frame.getInvoices().get(selectedIndex);
        frame.getInvoiceNumLabel().setText(""+currentInovice.getNum());
        frame.getInvoiceDateLabel().setText(""+currentInovice.getDate());
        frame.getCustomerNameLabel().setText(""+currentInovice.getCustomer());
        frame.getInvoiceTotalLabel().setText(""+currentInovice.getInvoiceTotal());
        ItemsTableModel itemsTableModel = new ItemsTableModel(currentInovice.getItems());
        frame.getItemsTable().setModel(itemsTableModel);
        itemsTableModel.fireTableDataChanged();
        }
    }
    
    private void loadFile() {
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(frame);
        try {
        if (result == JFileChooser.APPROVE_OPTION){
            String ph = fc.getSelectedFile().getPath();
            Path hPath = Paths.get(ph);
//            File headerFile = fc.getSelectedFile();
//            Path headerPath = Paths.get(headerFile.getAbsolutePath());
            try {
                List<String> invoiceHeaders = Files.readAllLines(hPath);
//                System.out.println("Invoces read");
                ArrayList<Invoice> invoiceArray = new ArrayList<>();
                for (String invoiceHeader : invoiceHeaders) {
                    String[] headerList = invoiceHeader.split(",");
                    int invoiceNum = Integer.parseInt(headerList[0]) ;
                    String invoiceDate = headerList[1];
                    String customerName = headerList[2];
                    Invoice invoice= new Invoice(invoiceNum, invoiceDate, customerName);
                    invoiceArray.add(invoice);
                }
//                System.out.println("Check#1");
                result = fc.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION){
                    String pi = fc.getSelectedFile().getPath();
                    Path itemPath = Paths.get(pi);
                    List<String> invoiceLines = Files.readAllLines(itemPath);
//                    System.out.println("Items Read");
                    for (String invoiceLine : invoiceLines) {
                        String[] lineList = invoiceLine.split(",");
                        int invoiceNum = Integer.parseInt(lineList[0]);
                        String itemName = lineList[1];
                        double itemPrice = Double.parseDouble(lineList[2]);
                        int count = Integer.parseInt(lineList[3]);
                        Invoice inv = null ; 
                        for (Invoice invoice:invoiceArray) {
                            if (invoice.getNum()== invoiceNum){
                                inv = invoice;
                                break;
                            }
                        }
                        Items item = new Items(invoiceNum, itemName, itemPrice, count, inv);
                        inv.getItems().add(item);
                    }
//                    System.out.println("Check#2");
                }
                frame.setInvoices(invoiceArray);
                InvoicesTableModel invoicesTableModel = new InvoicesTableModel(invoiceArray);
                frame.setInvoicesTableModel(invoicesTableModel);
                frame.getInvoiceTable().setModel(invoicesTableModel);
                frame.getInvoicesTableModel().fireTableDataChanged();
                
            } catch (IOException ex) {
                ex.printStackTrace();}                                   
        }
        } catch (NumberFormatException e) {
            System.out.println(e+"\n Please Choose Correct Documents");}                                           
    }
   
    private void saveFile() {
        ArrayList<Invoice> invoices = frame.getInvoices();
        String headers = "";
        String lines = "";
        for (Invoice invoice : invoices){
            String invCSV = invoice.getAsCSV();
            headers += invCSV;
            headers += "\n";
            
            for (Items items : invoice.getItems()){
                String itemCSV = items.getAsCSV();
                lines += itemCSV;
                lines += "\n";                
            }
        }
        try {    
            JFileChooser fs = new JFileChooser();
            int result = fs.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION){
                File headerSaver = fs.getSelectedFile();                       
                FileWriter headerfw = new FileWriter(headerSaver);           
                headerfw.write(headers);
                headerfw.flush();
                headerfw.close();
                result = fs.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION){
                    File itemSaver = fs.getSelectedFile();         
                    FileWriter linerfw = new FileWriter(itemSaver);           
                    linerfw.write(lines);
                    linerfw.flush();
                    linerfw.close();
                }
            }
        }
        catch (Exception ex) {}        
    }                                                                                               
    private void createNewInvoice() {
        
    }

    private void deleteInvoice() {
        int selectedRow = frame.getItemsTable().getSelectedRow();
        int selectedInv = frame.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1 && selectedInv != -1){
            Invoice invoice = frame.getInvoices().get(selectedInv);
            invoice.getItems().remove(selectedRow);
            ItemsTableModel itemsTableModel = new ItemsTableModel(invoice.getItems());
            frame.getItemsTable().setModel(itemsTableModel);
            itemsTableModel.fireTableDataChanged();            
        }
    }

    private void createNewItem() {
    }

    private void deleteItem() {
    }
}
