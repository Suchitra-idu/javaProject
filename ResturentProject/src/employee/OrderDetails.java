/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package employee;
import database.RestaurantDatabase;
import java.util.*;
import javax.swing.plaf.ColorUIResource;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.util.Map;
import java.awt.Color;
import javax.swing.table.JTableHeader;
import javax.swing.*;


/**
 *
 * @author chatw
 */
public class OrderDetails extends javax.swing.JFrame {
    
    private JTable Orders;
    private DefaultTableModel OrdersModel;
    
    public static String employeeName;
    public static String employeeID;
    /**
     * Creates new form OrderDetails
     */
    
    // Creates a table to save the order data.
 public OrderDetails() {
     
    
    initComponents();
    addEmployeeInfo();
    txtWelcome.setText("Welcome "+ employeeName);
           dashboardMenu.setVisible(true);
       orderMenu.setVisible(false);
    
    try {
    // Set L&F to Metal
    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
} catch (Exception e) {
    e.printStackTrace();
}
    setupTable();
    //resetTable();
}  private void addEmployeeInfo(){    List<Map<String, String>> result;
    String sql;
    
    // Get the amount of orderFood entries to a given cookId that the order was placed current month
    sql = "SELECT COUNT(*) AS count FROM orderFood JOIN `order` ON orderFood.orderId = `order`.orderId WHERE cookId = " + employeeID + " AND MONTH(orderdDate) = MONTH(CURRENT_DATE()) AND YEAR(orderdDate) = YEAR(CURRENT_DATE())";
    result = db.executeQuery(sql);
    txtmonthCompleatedQty.setText(result.get(0).get("count"));
    
sql = "SELECT COUNT(*) AS count FROM orderFood JOIN `order` ON orderFood.orderId = `order`.orderId WHERE cookId = " + employeeID + " AND DATE(orderdDate) = CURRENT_DATE()";
result = db.executeQuery(sql);
todaysCoolQty.setText(result.get(0).get("count"));

    // Get the amount of orderFood entries to a given cookId that the order was placed the whole time
    sql = "SELECT COUNT(*) AS count FROM orderFood WHERE cookId = " + employeeID;
    result = db.executeQuery(sql);
    txtTotalCooksQty.setText(result.get(0).get("count"));
    
    // Get the amount of orderFood entries where state == Pending
    sql = "SELECT COUNT(*) AS count FROM orderFood WHERE state != 'Serving'";
    result = db.executeQuery(sql);
    txtAvilableOrderqty.setText(result.get(0).get("count"));
}
 private void setupTable() {
        // Initialize the table model
        OrdersModel = new DefaultTableModel(
            new Object [][] {
                {null,null, null, null, null, null, null, null}
            },
            new String [] {
                "Orderd item ID","Order ID", "Table ID", "Item", "Quantity", "Extra", "Orderd time", "Item state"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // This will make the cells uneditable
                return false;
            }
        };

        // Initialize the table
        Orders = new JTable(OrdersModel);

        // Now that Orders is initialized, you can call resetTable()
        resetTable();

        // Change the color of the table
        Orders.setBackground(new Color(255, 255, 255)); // This changes the background color to a custom RGB color
        Orders.setForeground(Color.BLACK); // This changes the text color

        // Change the color of the table header
        JTableHeader header = Orders.getTableHeader();
        header.setBackground(new Color(255, 255, 255)); // This changes the background color of the header to a custom RGB color
        header.setForeground(Color.BLACK); // This changes the text color of the header

        // Remove the border of the table header
        header.setBorder(BorderFactory.createEmptyBorder());
        // Remove cell borders
        Orders.setShowGrid(false);

        // Add the table to a JScrollPane and add it to the form
        JScrollPane scrollPane = new JScrollPane(Orders);
        scrollPane.setBounds(25, 100, 780, 380); 
        scrollPane.getViewport().setBackground(Color.WHITE); // This changes the background color of the JScrollPane to white
        //scrollPane.setBorder(BorderFactory.createEmptyBorder()); // This removes the border of the JScrollPane

        // Change the color of the scroll bars
        UIManager.put("ScrollBar.thumb", new ColorUIResource(new Color(255, 255, 255))); // This changes the color of the scroll bar thumb
        SwingUtilities.updateComponentTreeUI(scrollPane); // This updates the UI of the JScrollPane to apply the changes

        // Set the background color of the viewport's view to white
        scrollPane.getViewport().getView().setBackground(Color.WHITE);

        orderMenu.add(scrollPane);
    }
    RestaurantDatabase db = new RestaurantDatabase();
 public void getSQL() {
    List<Map<String, String>> result;
    // This is to store the SQL query
    String sql;

    

    // SQL query to get all orders with currentState = "pending", and their corresponding food item name, ordered quantity, table ID, order date, order time, and current state
  sql = "SELECT o.orderId, o.TableId, o.orderdDate, o.orderdTime, o.currentState, o.additionalInfo, f.foodName, orderFood.quntity, orderFood.orderFoodId, orderFood.state " +
      "FROM `order` o " +
      "JOIN `orderFood` ON o.orderId = `orderFood`.orderId " +
      "JOIN food f ON `orderFood`.foodId = f.foodId " +
      "WHERE o.currentState = 'pending'"
          + "ORDER BY o.orderdTime DESC;";

    result = db.executeQuery(sql);
    System.out.println(result);

//    try {
//        db.getConnection().close();
//    } catch (Exception e) {
//        System.out.println("Error while closing the connection: " + e.getMessage());
//    }

    // Data will be putten int the table by itarating the SQL result hashmap.
    for (Map<String, String> row : result) {
        OrdersModel.addRow(new Object[] {
            row.get("orderFoodId"),
            row.get("orderId"),
            row.get("TableId"),
            row.get("foodName"),
            row.get("quntity"),
            row.get("additionalInfo"),
            row.get("orderdTime"),
            row.get("state")
            
        });
    }
    
    // Clear the combo box
cmbOrderId.removeAllItems();
cmbFullOrderID.removeAllItems();

// Add each orderFoodId to the combo box
for (Map<String, String> row : result) {
    cmbOrderId.addItem(row.get("orderFoodId"));
}

//To prevent duplications in combo box
Set<String> items = new HashSet<>();
for (Map<String, String> row : result) {
    String orderId = row.get("orderId");
    if (!items.contains(orderId)) {
        cmbFullOrderID.addItem(orderId);
        items.add(orderId);
    }
}


}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    public void resetTable(){
        DefaultTableModel model = (DefaultTableModel) Orders.getModel();
        model.setRowCount(0);

        getSQL();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dashboardMenu = new javax.swing.JPanel();
        txtWelcome = new javax.swing.JLabel();
        compleatedOrdersPanel = new javax.swing.JPanel();
        txtCompleatedOrderMonth = new javax.swing.JLabel();
        txtmonthCompleatedQty = new javax.swing.JLabel();
        availableOrdersPanel = new javax.swing.JPanel();
        txtavilableOrder = new javax.swing.JLabel();
        txtAvilableOrderqty = new javax.swing.JLabel();
        totalOrdersPanel = new javax.swing.JPanel();
        txtTotalCooks = new javax.swing.JLabel();
        txtTotalCooksQty = new javax.swing.JLabel();
        todaysCookPanel = new javax.swing.JPanel();
        txtTodaysCook = new javax.swing.JLabel();
        todaysCoolQty = new javax.swing.JLabel();
        sideBar = new javax.swing.JPanel();
        btnDashboard = new javax.swing.JButton();
        btnOrders = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        orderMenu = new javax.swing.JPanel();
        cmbOrderId = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        cmbFullOrderID = new javax.swing.JComboBox<>();
        cmbItemStates = new javax.swing.JComboBox<>();
        cmbOrderStates = new javax.swing.JComboBox<>();
        btnReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1100, 680));
        setResizable(false);
        getContentPane().setLayout(null);

        dashboardMenu.setBackground(new java.awt.Color(255, 255, 255));
        dashboardMenu.setLayout(null);

        txtWelcome.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        txtWelcome.setForeground(new java.awt.Color(0, 0, 0));
        txtWelcome.setText("Welcome Tathsara,");
        dashboardMenu.add(txtWelcome);
        txtWelcome.setBounds(30, 40, 330, 50);

        compleatedOrdersPanel.setBackground(new java.awt.Color(241, 242, 247));
        compleatedOrdersPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));
        compleatedOrdersPanel.setForeground(new java.awt.Color(169, 169, 169));
        compleatedOrdersPanel.setLayout(null);

        txtCompleatedOrderMonth.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtCompleatedOrderMonth.setForeground(new java.awt.Color(0, 0, 0));
        txtCompleatedOrderMonth.setText("Cooked this month  ");
        compleatedOrdersPanel.add(txtCompleatedOrderMonth);
        txtCompleatedOrderMonth.setBounds(20, 20, 280, 40);

        txtmonthCompleatedQty.setFont(new java.awt.Font("Segoe UI Semibold", 0, 30)); // NOI18N
        txtmonthCompleatedQty.setForeground(new java.awt.Color(0, 0, 0));
        txtmonthCompleatedQty.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtmonthCompleatedQty.setText("0");
        txtmonthCompleatedQty.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        compleatedOrdersPanel.add(txtmonthCompleatedQty);
        txtmonthCompleatedQty.setBounds(510, 20, 100, 41);

        dashboardMenu.add(compleatedOrdersPanel);
        compleatedOrdersPanel.setBounds(70, 380, 630, 80);

        availableOrdersPanel.setBackground(new java.awt.Color(241, 242, 247));
        availableOrdersPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));
        availableOrdersPanel.setForeground(new java.awt.Color(169, 169, 169));
        availableOrdersPanel.setLayout(null);

        txtavilableOrder.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtavilableOrder.setForeground(new java.awt.Color(0, 0, 0));
        txtavilableOrder.setText("Foods to cook ");
        availableOrdersPanel.add(txtavilableOrder);
        txtavilableOrder.setBounds(20, 20, 240, 40);

        txtAvilableOrderqty.setFont(new java.awt.Font("Segoe UI Semibold", 0, 30)); // NOI18N
        txtAvilableOrderqty.setForeground(new java.awt.Color(0, 0, 0));
        txtAvilableOrderqty.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtAvilableOrderqty.setText("0");
        txtAvilableOrderqty.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        availableOrdersPanel.add(txtAvilableOrderqty);
        txtAvilableOrderqty.setBounds(320, 20, 100, 40);

        dashboardMenu.add(availableOrdersPanel);
        availableOrdersPanel.setBounds(70, 140, 440, 80);

        totalOrdersPanel.setBackground(new java.awt.Color(241, 242, 247));
        totalOrdersPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));
        totalOrdersPanel.setForeground(new java.awt.Color(169, 169, 169));
        totalOrdersPanel.setLayout(null);

        txtTotalCooks.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtTotalCooks.setForeground(new java.awt.Color(0, 0, 0));
        txtTotalCooks.setText("Total cooks");
        totalOrdersPanel.add(txtTotalCooks);
        txtTotalCooks.setBounds(20, 20, 280, 40);

        txtTotalCooksQty.setFont(new java.awt.Font("Segoe UI Semibold", 0, 30)); // NOI18N
        txtTotalCooksQty.setForeground(new java.awt.Color(0, 0, 0));
        txtTotalCooksQty.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotalCooksQty.setText("0");
        txtTotalCooksQty.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        totalOrdersPanel.add(txtTotalCooksQty);
        txtTotalCooksQty.setBounds(570, 20, 100, 41);

        dashboardMenu.add(totalOrdersPanel);
        totalOrdersPanel.setBounds(70, 500, 690, 80);

        todaysCookPanel.setBackground(new java.awt.Color(241, 242, 247));
        todaysCookPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));
        todaysCookPanel.setForeground(new java.awt.Color(169, 169, 169));
        todaysCookPanel.setLayout(null);

        txtTodaysCook.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtTodaysCook.setForeground(new java.awt.Color(0, 0, 0));
        txtTodaysCook.setText("Cooked today");
        todaysCookPanel.add(txtTodaysCook);
        txtTodaysCook.setBounds(20, 20, 280, 40);

        todaysCoolQty.setFont(new java.awt.Font("Segoe UI Semibold", 0, 30)); // NOI18N
        todaysCoolQty.setForeground(new java.awt.Color(0, 0, 0));
        todaysCoolQty.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        todaysCoolQty.setText("0");
        todaysCoolQty.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        todaysCookPanel.add(todaysCoolQty);
        todaysCoolQty.setBounds(440, 20, 80, 41);

        dashboardMenu.add(todaysCookPanel);
        todaysCookPanel.setBounds(70, 260, 540, 80);

        getContentPane().add(dashboardMenu);
        dashboardMenu.setBounds(240, 0, 830, 640);

        sideBar.setBackground(new java.awt.Color(241, 242, 247));
        sideBar.setForeground(new java.awt.Color(241, 242, 247));
        sideBar.setLayout(null);

        btnDashboard.setBackground(new java.awt.Color(255, 210, 51));
        btnDashboard.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btnDashboard.setForeground(new java.awt.Color(0, 0, 0));
        btnDashboard.setText("DASHBOARD");
        btnDashboard.setBorder(null);
        btnDashboard.setFocusable(false);
        btnDashboard.setRequestFocusEnabled(false);
        btnDashboard.setRolloverEnabled(false);
        btnDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardActionPerformed(evt);
            }
        });
        sideBar.add(btnDashboard);
        btnDashboard.setBounds(40, 130, 180, 180);

        btnOrders.setBackground(new java.awt.Color(255, 210, 51));
        btnOrders.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btnOrders.setForeground(new java.awt.Color(0, 0, 0));
        btnOrders.setText("ORDERS");
        btnOrders.setBorder(null);
        btnOrders.setFocusable(false);
        btnOrders.setRequestFocusEnabled(false);
        btnOrders.setRolloverEnabled(false);
        btnOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdersActionPerformed(evt);
            }
        });
        sideBar.add(btnOrders);
        btnOrders.setBounds(40, 350, 180, 270);

        jLabel2.setFont(new java.awt.Font("Segoe Script", 2, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Resturant...");
        sideBar.add(jLabel2);
        jLabel2.setBounds(60, 50, 170, 45);

        jLabel3.setFont(new java.awt.Font("Segoe Script", 2, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Tathsara");
        sideBar.add(jLabel3);
        jLabel3.setBounds(40, 30, 150, 45);

        getContentPane().add(sideBar);
        sideBar.setBounds(-10, -10, 250, 670);

        orderMenu.setBackground(new java.awt.Color(255, 255, 255));
        orderMenu.setForeground(new java.awt.Color(255, 255, 255));
        orderMenu.setLayout(null);

        cmbOrderId.setBackground(new java.awt.Color(241, 242, 247));
        cmbOrderId.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cmbOrderId.setForeground(new java.awt.Color(0, 0, 0));
        cmbOrderId.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item ID" }));
        cmbOrderId.setBorder(null);
        cmbOrderId.setFocusable(false);
        cmbOrderId.setRequestFocusEnabled(false);
        cmbOrderId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbOrderIdActionPerformed(evt);
            }
        });
        orderMenu.add(cmbOrderId);
        cmbOrderId.setBounds(60, 510, 200, 40);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Orders");
        orderMenu.add(jLabel1);
        jLabel1.setBounds(0, 30, 130, 59);

        cmbFullOrderID.setBackground(new java.awt.Color(241, 242, 247));
        cmbFullOrderID.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cmbFullOrderID.setForeground(new java.awt.Color(0, 0, 0));
        cmbFullOrderID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Order ID" }));
        cmbFullOrderID.setBorder(null);
        cmbFullOrderID.setFocusable(false);
        cmbFullOrderID.setRequestFocusEnabled(false);
        cmbFullOrderID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFullOrderIDActionPerformed(evt);
            }
        });
        orderMenu.add(cmbFullOrderID);
        cmbFullOrderID.setBounds(60, 570, 200, 40);

        cmbItemStates.setBackground(new java.awt.Color(241, 242, 247));
        cmbItemStates.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cmbItemStates.setForeground(new java.awt.Color(0, 0, 0));
        cmbItemStates.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Accepted", "Preparing", "Cooking", "Packaging", "Serving", "Cancelled", " " }));
        cmbItemStates.setBorder(null);
        cmbItemStates.setFocusable(false);
        cmbItemStates.setRequestFocusEnabled(false);
        cmbItemStates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbItemStatesActionPerformed(evt);
            }
        });
        orderMenu.add(cmbItemStates);
        cmbItemStates.setBounds(300, 510, 510, 40);

        cmbOrderStates.setBackground(new java.awt.Color(241, 242, 247));
        cmbOrderStates.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cmbOrderStates.setForeground(new java.awt.Color(0, 0, 0));
        cmbOrderStates.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pending", "Finish order", "Cancel order" }));
        cmbOrderStates.setBorder(null);
        cmbOrderStates.setFocusable(false);
        cmbOrderStates.setRequestFocusEnabled(false);
        cmbOrderStates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbOrderStatesActionPerformed(evt);
            }
        });
        orderMenu.add(cmbOrderStates);
        cmbOrderStates.setBounds(300, 570, 510, 40);

        btnReset.setBackground(new java.awt.Color(255, 210, 51));
        btnReset.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        btnReset.setForeground(new java.awt.Color(0, 0, 0));
        btnReset.setText("RESET");
        btnReset.setBorder(null);
        btnReset.setFocusable(false);
        btnReset.setRequestFocusEnabled(false);
        btnReset.setRolloverEnabled(false);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        orderMenu.add(btnReset);
        btnReset.setBounds(675, 40, 130, 40);

        getContentPane().add(orderMenu);
        orderMenu.setBounds(240, 0, 840, 640);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbOrderIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbOrderIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbOrderIdActionPerformed

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
               dashboardMenu.setVisible(true);
       orderMenu.setVisible(false);
       addEmployeeInfo();
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void cmbFullOrderIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFullOrderIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbFullOrderIDActionPerformed

    private void cmbItemStatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbItemStatesActionPerformed
        String selectedOrderFoodId = (String) cmbOrderId.getSelectedItem();
        String state = (String)cmbItemStates.getSelectedItem();
        String sql = "UPDATE orderFood SET state = '"+state+"' WHERE orderFoodId = "+selectedOrderFoodId+"";     
        db.executeQuery(sql);
        
        if (state == "Serving"){
        sql = "UPDATE orderFood SET cookId = '"+employeeID+"' WHERE orderFoodId = "+selectedOrderFoodId+"";     
        db.executeQuery(sql);
        }
        
        resetTable();
    }//GEN-LAST:event_cmbItemStatesActionPerformed

    private void cmbOrderStatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbOrderStatesActionPerformed
        String selectedOrderId = (String) cmbFullOrderID.getSelectedItem();
    String state = (String)cmbOrderStates.getSelectedItem();
    List<Map<String, String>> result;

    // Check if all orderfood items related to the order have their state as either 'Serving' or 'Canceled'
    String checkSql = "SELECT COUNT(*) AS count FROM orderfood WHERE orderId = "+selectedOrderId+" AND (state != 'Serving' AND state != 'Canceled')";
    result = db.executeQuery(checkSql);
    int count = Integer.parseInt(result.get(0).get("count"));

    if(count == 0) {
        // If all orderfood items are either 'Serving' or 'Canceled', update the currentState of the order
        String sql = "UPDATE `order` SET currentState = '"+state+"' WHERE orderId = "+selectedOrderId+"";
        result = db.executeQuery(sql);
    } else {
        // If not all orderfood items are either 'Serving' or 'Canceled', show a popup message
        JOptionPane.showMessageDialog(null, "All order items must be 'Serving' or 'Canceled'.");
    }

    resetTable();
    }//GEN-LAST:event_cmbOrderStatesActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        resetTable();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdersActionPerformed
       dashboardMenu.setVisible(false);
       orderMenu.setVisible(true);
    }//GEN-LAST:event_btnOrdersActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[], String EmpId, String EmpName) {
        
        employeeID = EmpId;
        employeeName = EmpName;
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OrderDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderDetails().setVisible(true);
            }
            
        });
        
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel availableOrdersPanel;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnOrders;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cmbFullOrderID;
    private javax.swing.JComboBox<String> cmbItemStates;
    private javax.swing.JComboBox<String> cmbOrderId;
    private javax.swing.JComboBox<String> cmbOrderStates;
    private javax.swing.JPanel compleatedOrdersPanel;
    private javax.swing.JPanel dashboardMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel orderMenu;
    private javax.swing.JPanel sideBar;
    private javax.swing.JPanel todaysCookPanel;
    private javax.swing.JLabel todaysCoolQty;
    private javax.swing.JPanel totalOrdersPanel;
    private javax.swing.JLabel txtAvilableOrderqty;
    private javax.swing.JLabel txtCompleatedOrderMonth;
    private javax.swing.JLabel txtTodaysCook;
    private javax.swing.JLabel txtTotalCooks;
    private javax.swing.JLabel txtTotalCooksQty;
    private javax.swing.JLabel txtWelcome;
    private javax.swing.JLabel txtavilableOrder;
    private javax.swing.JLabel txtmonthCompleatedQty;
    // End of variables declaration//GEN-END:variables
}
