/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package customer;
import database.RestaurantDatabase;
import java.awt.Container;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import java.time.format.DateTimeFormatter;


/**
 *
 * @author chatw
 */
public class MainMenu extends javax.swing.JFrame {
    
    // A hashmap that contains the item and it's quntity.
    static HashMap<Integer, Integer> foodItems = new HashMap<Integer, Integer>();
    
    
    
    public String table;
    public float totalPrice;

    /**
     * Creates new form MainMenu
     */

    public MainMenu() {
        initComponents();

       

        
        //Make othetr pannles not visiblr

         pickMenu.setVisible(false);
          PlaceOrder.setVisible(false);
           //trackOrder.setVisible(false);
        
        // All the items that is availble in the store are initiollized in the hashmap
        foodItems.put(1, 0);
        foodItems.put(2, 0);
        foodItems.put(3, 0);
        foodItems.put(4, 0);
        foodItems.put(5, 0);

        // Print the HashMap
        System.out.println(foodItems);
        
        
        
    }
    
    //This is a class to create a new food item
    public class TrackFoodItemTemplate {

public TrackFoodItemTemplate() {
        trackOrder = new javax.swing.JPanel();
        BackgroundImage3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel(); // Initialize jLabel2 here
        btnBack3 = new javax.swing.JButton(); 
        trackOrder.setLayout(null);


        btnBack3.setBackground(new java.awt.Color(216, 81, 81));
        btnBack3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnBack3.setForeground(new java.awt.Color(255, 255, 255));
        btnBack3.setText("BACK");
        btnBack3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack3ActionPerformed(evt);
            }
        });
        trackOrder.add(btnBack3);
        btnBack3.setBounds(20, 20, 100, 40);
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("TRACK ORDER");
        trackOrder.add(jLabel2);
        jLabel2.setBounds(380, 30, 340, 40);

        getContentPane().add(trackOrder);
        trackOrder.setBounds(0, 0, 1070, 600);
    }
        public void addTemplate(int ox, int oy, String imgPath, String itemName, String itemState){
        itemTemplate = new javax.swing.JPanel();
        picsureOfFood = new javax.swing.JLabel();
        txtFoodName = new javax.swing.JLabel();
        txtState = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();    
            
        
        itemTemplate.setBackground(new java.awt.Color(255, 255, 255));
        itemTemplate.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));
        itemTemplate.setForeground(new java.awt.Color(169, 169, 169));
        itemTemplate.setLayout(null);

        picsureOfFood.setIcon(new javax.swing.ImageIcon(getClass().getResource(imgPath))); // NOI18N
        itemTemplate.add(picsureOfFood);
        picsureOfFood.setBounds(10  , 10 , 50, 50);

        txtFoodName.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        txtFoodName.setForeground(new java.awt.Color(0, 0, 0));
        txtFoodName.setText(itemName);
        itemTemplate.add(txtFoodName);
        txtFoodName.setBounds(80 , 10 , 100, 20);

        txtState.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        txtState.setForeground(new java.awt.Color(0, 0, 0));
        txtState.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtState.setText(itemState);
        itemTemplate.add(txtState);
        txtState.setBounds(80 , 20 , 250, 50);

        trackOrder.add(itemTemplate);
        itemTemplate.setBounds(70 + ox, 140 + oy, 440, 70);


        }
        
        public void addBackroundImage(){
                    BackgroundImage3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/Background.png"))); // NOI18N
        trackOrder.add(BackgroundImage3);
        BackgroundImage3.setBounds(0, 0, 1067, 612);
        }
    }
// Increase the given item quntity if the '+' button is clicked.
        public static void increaseValue(int key) {
        if (foodItems.containsKey(key)) {
            int value = foodItems.get(key);
            foodItems.put(key, value + 1);
        }
    }
        
   public void addTrackedItems(){
    if(trackOrder != null) {
        getContentPane().remove(trackOrder);
    }    getContentPane().repaint() ;  
       
    
    //this is to store if there is an SQL output
    List<Map<String, String>> result;
    // This is to store the SQL query
    String sql;
    
    RestaurantDatabase db = new RestaurantDatabase();
    //Get Items and states
    sql = "SELECT f.foodName, orderFood.state, f.foodId " +
         "FROM `order` o " +
         "JOIN `orderFood` ON o.orderId = `orderFood`.orderId " +
         "JOIN food f ON `orderFood`.foodId = f.foodId " +
         "WHERE o.currentState = 'pending' AND o.TableId = '" + table + "'"
         + "ORDER BY o.orderdTime DESC;";

    result = db.executeQuery(sql);
    System.out.println(result);
   
    // Create a HashMap to map food id to image path
    Map<Integer, String> foodIdToImagePath = new HashMap<>();
    foodIdToImagePath.put(1, "/customer/saladIcon.png");
    foodIdToImagePath.put(2, "/customer/burgerIcon.png");
    foodIdToImagePath.put(3, "/customer/pizzaIcon.jpg");
    foodIdToImagePath.put(4, "/customer/frenchFriesIcon.png");
    foodIdToImagePath.put(5, "/customer/chickenWingsIcon.png");
    
    int itemPosX = 0;
    int itemPosY = -100;
    int count = 0;
    

    TrackFoodItemTemplate track = new TrackFoodItemTemplate();
    for(Map<String, String> row : result){
        
        count = count + 1;
        itemPosY = itemPosY + 100;
        if (count == 4){
            itemPosX = itemPosX + 500;
            itemPosY = 0;
         
            
        }
        
        String foodName = row.get("foodName");
        String state = row.get("state");
        int foodId = Integer.parseInt(row.get("foodId"));
        String imagePath = foodIdToImagePath.get(foodId);
        track.addTemplate(itemPosX, itemPosY, imagePath, foodName, state);
    }
    track.addBackroundImage();
    
        getContentPane().add(trackOrder);
    trackOrder.setBounds(0, 0, 1070, 600);
}


    // Decrease the given item quntity if the '+' button is clicked.
    public static void decreaseValue(int key) {
        if (foodItems.containsKey(key)) {
            int value = foodItems.get(key);
            foodItems.put(key, value - 1);
        }
    }

    // Reset all the stored data.
    public void resetAll(){
        foodItems.put(1, 0);
        foodItems.put(2, 0);
        foodItems.put(3, 0);
        foodItems.put(4, 0);
        foodItems.put(5, 0);
        
        txtQty.setText("0");
        txtQty1.setText("0");
        txtQty2.setText("0");
        txtQty5.setText("0");
        txtQty4.setText("0");
        

    }
    
    // This function is used to handle the button click of selecting a table.
    private void handleButtonClick(String tableValue) {
    PickSeat.setVisible(false);
    pickMenu.setVisible(true);
    table = tableValue;
    System.out.print(table);
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PickSeat = new javax.swing.JPanel();
        btnT3 = new javax.swing.JButton();
        btnT4 = new javax.swing.JButton();
        btnT2 = new javax.swing.JButton();
        btnT5 = new javax.swing.JButton();
        btnT1 = new javax.swing.JButton();
        btnT6 = new javax.swing.JButton();
        btnT7 = new javax.swing.JButton();
        btnT8 = new javax.swing.JButton();
        btnT9 = new javax.swing.JButton();
        btnT10 = new javax.swing.JButton();
        btnT11 = new javax.swing.JButton();
        btnT12 = new javax.swing.JButton();
        btnT13 = new javax.swing.JButton();
        btnT14 = new javax.swing.JButton();
        btnT15 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        BackgroundImage1 = new javax.swing.JLabel();
        PlaceOrder = new javax.swing.JPanel();
        makePurchesPanel = new javax.swing.JPanel();
        txtTotPrice = new javax.swing.JLabel();
        btnSendOrder = new javax.swing.JButton();
        btnCancelOrder = new javax.swing.JButton();
        txtTot = new javax.swing.JLabel();
        txtLine2 = new javax.swing.JLabel();
        AdditionalInfo = new javax.swing.JPanel();
        txtAdditionalInfo = new javax.swing.JLabel();
        txtline = new javax.swing.JLabel();
        scrAdditionalInfo = new javax.swing.JScrollPane();
        inputTxtAdditionalInfo = new javax.swing.JTextArea();
        ItemPanel2 = new javax.swing.JPanel();
        picItem2 = new javax.swing.JLabel();
        txtSalad2 = new javax.swing.JLabel();
        txtPrice2 = new javax.swing.JLabel();
        btnRemove2 = new javax.swing.JButton();
        txtQty2 = new javax.swing.JLabel();
        btnAdd2 = new javax.swing.JButton();
        ItemPanel1 = new javax.swing.JPanel();
        picItem1 = new javax.swing.JLabel();
        txtSalad1 = new javax.swing.JLabel();
        txtPrice1 = new javax.swing.JLabel();
        btnRemove1 = new javax.swing.JButton();
        txtQty1 = new javax.swing.JLabel();
        btnAdd1 = new javax.swing.JButton();
        ItemPanel = new javax.swing.JPanel();
        picItem = new javax.swing.JLabel();
        txtSalad = new javax.swing.JLabel();
        txtPrice = new javax.swing.JLabel();
        btnRemove = new javax.swing.JButton();
        txtQty = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        ItemPanel4 = new javax.swing.JPanel();
        picItem4 = new javax.swing.JLabel();
        txtSalad4 = new javax.swing.JLabel();
        txtPrice4 = new javax.swing.JLabel();
        btnRemove4 = new javax.swing.JButton();
        txtQty4 = new javax.swing.JLabel();
        btnAdd4 = new javax.swing.JButton();
        ItemPanel5 = new javax.swing.JPanel();
        picItem5 = new javax.swing.JLabel();
        txtSalad5 = new javax.swing.JLabel();
        txtPrice5 = new javax.swing.JLabel();
        btnRemove5 = new javax.swing.JButton();
        txtQty5 = new javax.swing.JLabel();
        btnAdd5 = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        BackgroundImage2 = new javax.swing.JLabel();
        pickMenu = new javax.swing.JPanel();
        btnTrackOrder = new javax.swing.JButton();
        btnOrder = new javax.swing.JButton();
        btnBack1 = new javax.swing.JButton();
        BackgroundImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1080, 640));
        setResizable(false);
        getContentPane().setLayout(null);

        PickSeat.setLayout(null);

        btnT3.setBackground(new java.awt.Color(234, 240, 240));
        btnT3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        btnT3.setForeground(new java.awt.Color(0, 0, 0));
        btnT3.setText("TC");
        btnT3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnT3ActionPerformed(evt);
            }
        });
        PickSeat.add(btnT3);
        btnT3.setBounds(520, 190, 90, 70);

        btnT4.setBackground(new java.awt.Color(234, 240, 240));
        btnT4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        btnT4.setForeground(new java.awt.Color(0, 0, 0));
        btnT4.setText("TD");
        btnT4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnT4ActionPerformed(evt);
            }
        });
        PickSeat.add(btnT4);
        btnT4.setBounds(670, 190, 90, 70);

        btnT2.setBackground(new java.awt.Color(234, 240, 240));
        btnT2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        btnT2.setForeground(new java.awt.Color(0, 0, 0));
        btnT2.setText("TB");
        btnT2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnT2ActionPerformed(evt);
            }
        });
        PickSeat.add(btnT2);
        btnT2.setBounds(370, 190, 90, 70);

        btnT5.setBackground(new java.awt.Color(234, 240, 240));
        btnT5.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        btnT5.setForeground(new java.awt.Color(0, 0, 0));
        btnT5.setText("TE");
        btnT5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnT5ActionPerformed(evt);
            }
        });
        PickSeat.add(btnT5);
        btnT5.setBounds(810, 190, 90, 70);

        btnT1.setBackground(new java.awt.Color(234, 240, 240));
        btnT1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        btnT1.setForeground(new java.awt.Color(0, 0, 0));
        btnT1.setText("TA");
        btnT1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnT1ActionPerformed(evt);
            }
        });
        PickSeat.add(btnT1);
        btnT1.setBounds(220, 190, 90, 70);

        btnT6.setBackground(new java.awt.Color(234, 240, 240));
        btnT6.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        btnT6.setForeground(new java.awt.Color(0, 0, 0));
        btnT6.setText("TJ");
        btnT6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnT6ActionPerformed(evt);
            }
        });
        PickSeat.add(btnT6);
        btnT6.setBounds(810, 290, 90, 70);

        btnT7.setBackground(new java.awt.Color(234, 240, 240));
        btnT7.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        btnT7.setForeground(new java.awt.Color(0, 0, 0));
        btnT7.setText("TH");
        btnT7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnT7ActionPerformed(evt);
            }
        });
        PickSeat.add(btnT7);
        btnT7.setBounds(520, 290, 90, 70);

        btnT8.setBackground(new java.awt.Color(234, 240, 240));
        btnT8.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        btnT8.setForeground(new java.awt.Color(0, 0, 0));
        btnT8.setText("TG");
        btnT8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnT8ActionPerformed(evt);
            }
        });
        PickSeat.add(btnT8);
        btnT8.setBounds(370, 290, 90, 70);

        btnT9.setBackground(new java.awt.Color(234, 240, 240));
        btnT9.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        btnT9.setForeground(new java.awt.Color(0, 0, 0));
        btnT9.setText("TI");
        btnT9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnT9ActionPerformed(evt);
            }
        });
        PickSeat.add(btnT9);
        btnT9.setBounds(670, 290, 90, 70);

        btnT10.setBackground(new java.awt.Color(234, 240, 240));
        btnT10.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        btnT10.setForeground(new java.awt.Color(0, 0, 0));
        btnT10.setText("TF");
        btnT10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnT10ActionPerformed(evt);
            }
        });
        PickSeat.add(btnT10);
        btnT10.setBounds(220, 290, 90, 70);

        btnT11.setBackground(new java.awt.Color(234, 240, 240));
        btnT11.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        btnT11.setForeground(new java.awt.Color(0, 0, 0));
        btnT11.setText("TL");
        btnT11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnT11ActionPerformed(evt);
            }
        });
        PickSeat.add(btnT11);
        btnT11.setBounds(370, 390, 90, 70);

        btnT12.setBackground(new java.awt.Color(234, 240, 240));
        btnT12.setFont(new java.awt.Font("Segoe UI", 1, 34)); // NOI18N
        btnT12.setForeground(new java.awt.Color(0, 0, 0));
        btnT12.setText("TN");
        btnT12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnT12ActionPerformed(evt);
            }
        });
        PickSeat.add(btnT12);
        btnT12.setBounds(670, 390, 90, 70);

        btnT13.setBackground(new java.awt.Color(234, 240, 240));
        btnT13.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        btnT13.setForeground(new java.awt.Color(0, 0, 0));
        btnT13.setText("TK");
        btnT13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnT13ActionPerformed(evt);
            }
        });
        PickSeat.add(btnT13);
        btnT13.setBounds(220, 390, 90, 70);

        btnT14.setBackground(new java.awt.Color(234, 240, 240));
        btnT14.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        btnT14.setForeground(new java.awt.Color(0, 0, 0));
        btnT14.setText("TM");
        btnT14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnT14ActionPerformed(evt);
            }
        });
        PickSeat.add(btnT14);
        btnT14.setBounds(520, 390, 90, 70);

        btnT15.setBackground(new java.awt.Color(234, 240, 240));
        btnT15.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        btnT15.setForeground(new java.awt.Color(0, 0, 0));
        btnT15.setText("TO");
        btnT15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnT15ActionPerformed(evt);
            }
        });
        PickSeat.add(btnT15);
        btnT15.setBounds(810, 390, 90, 70);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SELECT TABLE");
        PickSeat.add(jLabel1);
        jLabel1.setBounds(380, 70, 340, 40);

        BackgroundImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/Background.png"))); // NOI18N
        PickSeat.add(BackgroundImage1);
        BackgroundImage1.setBounds(0, 0, 1067, 612);

        getContentPane().add(PickSeat);
        PickSeat.setBounds(0, 0, 1070, 610);

        PlaceOrder.setLayout(null);

        makePurchesPanel.setBackground(new java.awt.Color(255, 255, 255));
        makePurchesPanel.setForeground(new java.awt.Color(169, 169, 169));
        makePurchesPanel.setLayout(null);

        txtTotPrice.setBackground(new java.awt.Color(0, 0, 0));
        txtTotPrice.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        txtTotPrice.setForeground(new java.awt.Color(0, 0, 0));
        txtTotPrice.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotPrice.setText("Rs 0");
        makePurchesPanel.add(txtTotPrice);
        txtTotPrice.setBounds(180, 10, 150, 60);

        btnSendOrder.setBackground(new java.awt.Color(229, 178, 0));
        btnSendOrder.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnSendOrder.setForeground(new java.awt.Color(0, 0, 0));
        btnSendOrder.setText("SEND ORDER");
        btnSendOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendOrderActionPerformed(evt);
            }
        });
        makePurchesPanel.add(btnSendOrder);
        btnSendOrder.setBounds(190, 120, 160, 60);

        btnCancelOrder.setBackground(new java.awt.Color(214, 55, 55));
        btnCancelOrder.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnCancelOrder.setForeground(new java.awt.Color(0, 0, 0));
        btnCancelOrder.setText("CANCEL ORDER");
        btnCancelOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelOrderActionPerformed(evt);
            }
        });
        makePurchesPanel.add(btnCancelOrder);
        btnCancelOrder.setBounds(20, 120, 160, 60);

        txtTot.setBackground(new java.awt.Color(0, 0, 0));
        txtTot.setFont(new java.awt.Font("Segoe UI Semibold", 0, 36)); // NOI18N
        txtTot.setForeground(new java.awt.Color(0, 0, 0));
        txtTot.setText("TOTAL");
        makePurchesPanel.add(txtTot);
        txtTot.setBounds(10, 10, 130, 60);

        txtLine2.setBackground(new java.awt.Color(144, 144, 144));
        txtLine2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtLine2.setText("-----------------------------------------------------------------");
        makePurchesPanel.add(txtLine2);
        txtLine2.setBounds(0, 80, 370, 20);

        PlaceOrder.add(makePurchesPanel);
        makePurchesPanel.setBounds(690, 380, 370, 210);

        AdditionalInfo.setBackground(new java.awt.Color(255, 255, 255));
        AdditionalInfo.setLayout(null);

        txtAdditionalInfo.setBackground(new java.awt.Color(0, 0, 0));
        txtAdditionalInfo.setFont(new java.awt.Font("Segoe UI Semibold", 0, 24)); // NOI18N
        txtAdditionalInfo.setForeground(new java.awt.Color(0, 0, 0));
        txtAdditionalInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtAdditionalInfo.setText("ADDITIONAL INFO");
        AdditionalInfo.add(txtAdditionalInfo);
        txtAdditionalInfo.setBounds(30, 10, 290, 60);

        txtline.setBackground(new java.awt.Color(144, 144, 144));
        txtline.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtline.setText("-----------------------------------------------------------------");
        AdditionalInfo.add(txtline);
        txtline.setBounds(0, 60, 370, 20);

        scrAdditionalInfo.setBackground(new java.awt.Color(255, 255, 255));

        inputTxtAdditionalInfo.setBackground(new java.awt.Color(255, 255, 255));
        inputTxtAdditionalInfo.setColumns(20);
        inputTxtAdditionalInfo.setForeground(new java.awt.Color(0, 0, 0));
        inputTxtAdditionalInfo.setRows(5);
        scrAdditionalInfo.setViewportView(inputTxtAdditionalInfo);

        AdditionalInfo.add(scrAdditionalInfo);
        scrAdditionalInfo.setBounds(10, 80, 340, 170);

        PlaceOrder.add(AdditionalInfo);
        AdditionalInfo.setBounds(690, 70, 360, 260);

        ItemPanel2.setBackground(new java.awt.Color(255, 255, 255));
        ItemPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));
        ItemPanel2.setForeground(new java.awt.Color(169, 169, 169));
        ItemPanel2.setLayout(null);

        picItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/pizzaIcon.jpg"))); // NOI18N
        ItemPanel2.add(picItem2);
        picItem2.setBounds(10, 10, 50, 50);

        txtSalad2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtSalad2.setForeground(new java.awt.Color(0, 0, 0));
        txtSalad2.setText("PIZZA");
        ItemPanel2.add(txtSalad2);
        txtSalad2.setBounds(80, 20, 100, 16);

        txtPrice2.setBackground(new java.awt.Color(0, 0, 0));
        txtPrice2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtPrice2.setForeground(new java.awt.Color(0, 0, 0));
        txtPrice2.setText("Rs 1200");
        ItemPanel2.add(txtPrice2);
        txtPrice2.setBounds(80, 40, 39, 16);

        btnRemove2.setBackground(new java.awt.Color(214, 55, 55));
        btnRemove2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnRemove2.setForeground(new java.awt.Color(255, 255, 255));
        btnRemove2.setText("-");
        btnRemove2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemove2ActionPerformed(evt);
            }
        });
        ItemPanel2.add(btnRemove2);
        btnRemove2.setBounds(370, 10, 50, 50);

        txtQty2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 30)); // NOI18N
        txtQty2.setForeground(new java.awt.Color(0, 0, 0));
        txtQty2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtQty2.setText("0");
        ItemPanel2.add(txtQty2);
        txtQty2.setBounds(320, 10, 50, 50);

        btnAdd2.setBackground(new java.awt.Color(229, 178, 0));
        btnAdd2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnAdd2.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd2.setText("+");
        btnAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd2ActionPerformed(evt);
            }
        });
        ItemPanel2.add(btnAdd2);
        btnAdd2.setBounds(270, 10, 50, 50);

        PlaceOrder.add(ItemPanel2);
        ItemPanel2.setBounds(60, 290, 440, 70);

        ItemPanel1.setBackground(new java.awt.Color(255, 255, 255));
        ItemPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));
        ItemPanel1.setForeground(new java.awt.Color(169, 169, 169));
        ItemPanel1.setLayout(null);

        picItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/burgerIcon.png"))); // NOI18N
        ItemPanel1.add(picItem1);
        picItem1.setBounds(10, 10, 50, 50);

        txtSalad1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtSalad1.setForeground(new java.awt.Color(0, 0, 0));
        txtSalad1.setText("BURGER");
        ItemPanel1.add(txtSalad1);
        txtSalad1.setBounds(80, 20, 100, 16);

        txtPrice1.setBackground(new java.awt.Color(0, 0, 0));
        txtPrice1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtPrice1.setForeground(new java.awt.Color(0, 0, 0));
        txtPrice1.setText("Rs 500");
        ItemPanel1.add(txtPrice1);
        txtPrice1.setBounds(80, 40, 34, 16);

        btnRemove1.setBackground(new java.awt.Color(214, 55, 55));
        btnRemove1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnRemove1.setForeground(new java.awt.Color(255, 255, 255));
        btnRemove1.setText("-");
        btnRemove1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemove1ActionPerformed(evt);
            }
        });
        ItemPanel1.add(btnRemove1);
        btnRemove1.setBounds(370, 10, 50, 50);

        txtQty1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 30)); // NOI18N
        txtQty1.setForeground(new java.awt.Color(0, 0, 0));
        txtQty1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtQty1.setText("0");
        ItemPanel1.add(txtQty1);
        txtQty1.setBounds(320, 10, 50, 50);

        btnAdd1.setBackground(new java.awt.Color(229, 178, 0));
        btnAdd1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnAdd1.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd1.setText("+");
        btnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd1ActionPerformed(evt);
            }
        });
        ItemPanel1.add(btnAdd1);
        btnAdd1.setBounds(270, 10, 50, 50);

        PlaceOrder.add(ItemPanel1);
        ItemPanel1.setBounds(60, 200, 440, 70);

        ItemPanel.setBackground(new java.awt.Color(255, 255, 255));
        ItemPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));
        ItemPanel.setForeground(new java.awt.Color(169, 169, 169));
        ItemPanel.setLayout(null);

        picItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/saladIcon.png"))); // NOI18N
        ItemPanel.add(picItem);
        picItem.setBounds(10, 10, 50, 50);

        txtSalad.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtSalad.setForeground(new java.awt.Color(0, 0, 0));
        txtSalad.setText("SALAD");
        ItemPanel.add(txtSalad);
        txtSalad.setBounds(80, 20, 100, 16);

        txtPrice.setBackground(new java.awt.Color(0, 0, 0));
        txtPrice.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtPrice.setForeground(new java.awt.Color(0, 0, 0));
        txtPrice.setText("Rs 300");
        ItemPanel.add(txtPrice);
        txtPrice.setBounds(80, 40, 34, 16);

        btnRemove.setBackground(new java.awt.Color(214, 55, 55));
        btnRemove.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnRemove.setForeground(new java.awt.Color(255, 255, 255));
        btnRemove.setText("-");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        ItemPanel.add(btnRemove);
        btnRemove.setBounds(370, 10, 50, 50);

        txtQty.setFont(new java.awt.Font("Segoe UI Semibold", 0, 30)); // NOI18N
        txtQty.setForeground(new java.awt.Color(0, 0, 0));
        txtQty.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtQty.setText("0");
        ItemPanel.add(txtQty);
        txtQty.setBounds(320, 10, 50, 50);

        btnAdd.setBackground(new java.awt.Color(229, 178, 0));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setText("+");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        ItemPanel.add(btnAdd);
        btnAdd.setBounds(270, 10, 50, 50);

        PlaceOrder.add(ItemPanel);
        ItemPanel.setBounds(60, 100, 440, 70);

        ItemPanel4.setBackground(new java.awt.Color(255, 255, 255));
        ItemPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));
        ItemPanel4.setForeground(new java.awt.Color(169, 169, 169));
        ItemPanel4.setLayout(null);

        picItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/chickenWingsIcon.png"))); // NOI18N
        ItemPanel4.add(picItem4);
        picItem4.setBounds(10, 10, 50, 50);

        txtSalad4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtSalad4.setForeground(new java.awt.Color(0, 0, 0));
        txtSalad4.setText("chicken wings");
        ItemPanel4.add(txtSalad4);
        txtSalad4.setBounds(80, 20, 100, 16);

        txtPrice4.setBackground(new java.awt.Color(0, 0, 0));
        txtPrice4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtPrice4.setForeground(new java.awt.Color(0, 0, 0));
        txtPrice4.setText("Rs 800");
        ItemPanel4.add(txtPrice4);
        txtPrice4.setBounds(80, 40, 34, 16);

        btnRemove4.setBackground(new java.awt.Color(214, 55, 55));
        btnRemove4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnRemove4.setForeground(new java.awt.Color(255, 255, 255));
        btnRemove4.setText("-");
        btnRemove4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemove4ActionPerformed(evt);
            }
        });
        ItemPanel4.add(btnRemove4);
        btnRemove4.setBounds(370, 10, 50, 50);

        txtQty4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 30)); // NOI18N
        txtQty4.setForeground(new java.awt.Color(0, 0, 0));
        txtQty4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtQty4.setText("0");
        ItemPanel4.add(txtQty4);
        txtQty4.setBounds(320, 10, 50, 50);

        btnAdd4.setBackground(new java.awt.Color(229, 178, 0));
        btnAdd4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnAdd4.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd4.setText("+");
        btnAdd4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd4ActionPerformed(evt);
            }
        });
        ItemPanel4.add(btnAdd4);
        btnAdd4.setBounds(270, 10, 50, 50);

        PlaceOrder.add(ItemPanel4);
        ItemPanel4.setBounds(60, 470, 440, 70);

        ItemPanel5.setBackground(new java.awt.Color(255, 255, 255));
        ItemPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true));
        ItemPanel5.setForeground(new java.awt.Color(169, 169, 169));
        ItemPanel5.setLayout(null);

        picItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/frenchFriesIcon.png"))); // NOI18N
        ItemPanel5.add(picItem5);
        picItem5.setBounds(10, 10, 50, 50);

        txtSalad5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtSalad5.setForeground(new java.awt.Color(0, 0, 0));
        txtSalad5.setText("FRENCH FRIES");
        ItemPanel5.add(txtSalad5);
        txtSalad5.setBounds(80, 20, 100, 16);

        txtPrice5.setBackground(new java.awt.Color(0, 0, 0));
        txtPrice5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        txtPrice5.setForeground(new java.awt.Color(0, 0, 0));
        txtPrice5.setText("Rs 400");
        ItemPanel5.add(txtPrice5);
        txtPrice5.setBounds(80, 40, 35, 16);

        btnRemove5.setBackground(new java.awt.Color(214, 55, 55));
        btnRemove5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnRemove5.setForeground(new java.awt.Color(255, 255, 255));
        btnRemove5.setText("-");
        btnRemove5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemove5ActionPerformed(evt);
            }
        });
        ItemPanel5.add(btnRemove5);
        btnRemove5.setBounds(370, 10, 50, 50);

        txtQty5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 30)); // NOI18N
        txtQty5.setForeground(new java.awt.Color(0, 0, 0));
        txtQty5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtQty5.setText("0");
        ItemPanel5.add(txtQty5);
        txtQty5.setBounds(320, 10, 50, 50);

        btnAdd5.setBackground(new java.awt.Color(229, 178, 0));
        btnAdd5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnAdd5.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd5.setText("+");
        btnAdd5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdd5ActionPerformed(evt);
            }
        });
        ItemPanel5.add(btnAdd5);
        btnAdd5.setBounds(270, 10, 50, 50);

        PlaceOrder.add(ItemPanel5);
        ItemPanel5.setBounds(60, 380, 440, 70);

        btnBack.setBackground(new java.awt.Color(214, 55, 55));
        btnBack.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("BACK");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        PlaceOrder.add(btnBack);
        btnBack.setBounds(20, 20, 100, 40);

        BackgroundImage2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/Background.png"))); // NOI18N
        PlaceOrder.add(BackgroundImage2);
        BackgroundImage2.setBounds(0, 0, 1067, 612);

        getContentPane().add(PlaceOrder);
        PlaceOrder.setBounds(0, 0, 1070, 610);

        pickMenu.setLayout(null);

        btnTrackOrder.setBackground(new java.awt.Color(244, 119, 0));
        btnTrackOrder.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        btnTrackOrder.setForeground(new java.awt.Color(249, 249, 249));
        btnTrackOrder.setText("TRACK ORDER");
        btnTrackOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrackOrderActionPerformed(evt);
            }
        });
        pickMenu.add(btnTrackOrder);
        btnTrackOrder.setBounds(600, 140, 330, 420);

        btnOrder.setBackground(new java.awt.Color(158, 122, 0));
        btnOrder.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        btnOrder.setForeground(new java.awt.Color(249, 249, 249));
        btnOrder.setText("ORDER FOODS");
        btnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderActionPerformed(evt);
            }
        });
        pickMenu.add(btnOrder);
        btnOrder.setBounds(150, 140, 330, 420);

        btnBack1.setBackground(new java.awt.Color(216, 81, 81));
        btnBack1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btnBack1.setForeground(new java.awt.Color(255, 255, 255));
        btnBack1.setText("BACK");
        btnBack1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack1ActionPerformed(evt);
            }
        });
        pickMenu.add(btnBack1);
        btnBack1.setBounds(20, 20, 100, 40);

        BackgroundImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/customer/Background.png"))); // NOI18N
        pickMenu.add(BackgroundImage);
        BackgroundImage.setBounds(0, 0, 1067, 612);

        getContentPane().add(pickMenu);
        pickMenu.setBounds(0, 0, 1070, 610);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderActionPerformed
        pickMenu.setVisible(false);
        PlaceOrder.setVisible(true);
        
    }//GEN-LAST:event_btnOrderActionPerformed

    private void btnT4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnT4ActionPerformed
           handleButtonClick("TD");

    }//GEN-LAST:event_btnT4ActionPerformed

    private void btnT10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnT10ActionPerformed
            handleButtonClick("TF");

    }//GEN-LAST:event_btnT10ActionPerformed

    private void btnT7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnT7ActionPerformed
            handleButtonClick("TH");

    }//GEN-LAST:event_btnT7ActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
       int foodType = 1;
        if(foodItems.get(foodType) > 0){
       decreaseValue(foodType);
       txtQty.setText(""+ foodItems.get(foodType));
       
              totalPrice = totalPrice - 300;
       txtTotPrice.setText("" + totalPrice);
        }
    }//GEN-LAST:event_btnRemoveActionPerformed
    // These are to handle the button click of adding or removing an item. 
    private void btnRemove1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemove1ActionPerformed
              int foodType = 2;
        if(foodItems.get(foodType) > 0){
       decreaseValue(foodType);
       txtQty1.setText(""+ foodItems.get(foodType));
       
              totalPrice = totalPrice - 500;
       txtTotPrice.setText("" + totalPrice);
        }
    }//GEN-LAST:event_btnRemove1ActionPerformed

    private void btnRemove2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemove2ActionPerformed
               int foodType = 3;
        if(foodItems.get(foodType) > 0){
       decreaseValue(foodType);
       
       txtQty2.setText(""+ foodItems.get(foodType));
              totalPrice = totalPrice -1200;
       txtTotPrice.setText("" + totalPrice);
        }
    }//GEN-LAST:event_btnRemove2ActionPerformed

    private void btnRemove4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemove4ActionPerformed
              int foodType = 4;
        if(foodItems.get(foodType) > 0){
       decreaseValue(foodType);
              totalPrice = totalPrice - 800;
       txtTotPrice.setText("" + totalPrice);
       txtQty4.setText(""+ foodItems.get(foodType));
        }
    }//GEN-LAST:event_btnRemove4ActionPerformed

    private void btnRemove5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemove5ActionPerformed
               int foodType = 5;
        if(foodItems.get(foodType) > 0){
       decreaseValue(foodType);
              totalPrice = totalPrice - 400;
       txtTotPrice.setText("" + totalPrice);
       txtQty5.setText(""+ foodItems.get(foodType));
        }
    }//GEN-LAST:event_btnRemove5ActionPerformed

    private void btnSendOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendOrderActionPerformed
        
                // Check if all values are 0
        boolean allZero = foodItems.values().stream().allMatch(val -> val == 0);
        
        if(allZero == false){
        
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        
        //this is to store if there is an SQL output
        List<Map<String, String>> result;
        // This is to store the SQL query
        String sql;
        
        RestaurantDatabase db = new RestaurantDatabase();
        
        // Randomly genarate a primary key (Not the most optimal methord, change if there are any time left)
        Random rand = new Random();
        int primaryKey = rand.nextInt(9000000);
       
        
       
        
        // Store order data in order table
        sql = "INSERT INTO `order` VALUES ('"+ primaryKey +"','" + table + "', '" + currentDate + "', '" + currentTime + "','"+inputTxtAdditionalInfo.getText()+"',Null, 'pending' )";
        result = db.executeQuery(sql);

        // orderd item will be stored in orderItem table.
        for (HashMap.Entry<Integer, Integer> entry : foodItems.entrySet()) {
            // If the value is greater than 0, perform an action
            int qty = entry.getValue();
            int foodId = entry.getKey();
            if ( qty > 0) {
                    sql = "INSERT INTO orderfood (quntity, orderId, foodId, state) VALUES ('"+ qty + "', '" + primaryKey + "', '" + foodId + "' ,'Pending')";
                    result = db.executeQuery(sql);

            }
        }

        // For debugging
        sql = "SELECT * from `order`;";
        result = db.executeQuery(sql);
        System.out.println(result);
        
        sql = "SELECT * from orderFood;";
        result = db.executeQuery(sql);
        System.out.println(result);
        
        try {
            db.getConnection().close();
        } catch (Exception e) {
            System.out.println("Error while closing the connection: " + e.getMessage());
        }
        
        //Show a massage if the order is sucessfull and resets the window
        
        JOptionPane.showMessageDialog(null, "Oder is succesfully sent, Your order ID : " + primaryKey );
        resetAll();
        pickMenu.setVisible(false);
        PlaceOrder.setVisible(false);        
        PickSeat.setVisible(true);
        
        }
        else{
            JOptionPane.showMessageDialog(null, "Please enter your order."  );
        }
        
    }//GEN-LAST:event_btnSendOrderActionPerformed
    // To cancel the order
    private void btnCancelOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelOrderActionPerformed
         pickMenu.setVisible(false);
          PlaceOrder.setVisible(false);        
          PickSeat.setVisible(true);
          
          resetAll();
          

          
          
    }//GEN-LAST:event_btnCancelOrderActionPerformed

    private void btnT3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnT3ActionPerformed
    handleButtonClick("TC");
    }//GEN-LAST:event_btnT3ActionPerformed

    private void btnT1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnT1ActionPerformed
    handleButtonClick("TA");

        
    }//GEN-LAST:event_btnT1ActionPerformed

    private void btnT2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnT2ActionPerformed
    handleButtonClick("TB");

    }//GEN-LAST:event_btnT2ActionPerformed

    private void btnT5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnT5ActionPerformed
            handleButtonClick("TE");

    }//GEN-LAST:event_btnT5ActionPerformed

    private void btnT8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnT8ActionPerformed
            handleButtonClick("TG");

    }//GEN-LAST:event_btnT8ActionPerformed

    private void btnT9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnT9ActionPerformed
            handleButtonClick("TI");

    }//GEN-LAST:event_btnT9ActionPerformed

    private void btnT6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnT6ActionPerformed
            handleButtonClick("TJ");

    }//GEN-LAST:event_btnT6ActionPerformed

    private void btnT13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnT13ActionPerformed
            handleButtonClick("TK");

    }//GEN-LAST:event_btnT13ActionPerformed

    private void btnT11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnT11ActionPerformed
            handleButtonClick("TL");

    }//GEN-LAST:event_btnT11ActionPerformed

    private void btnT14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnT14ActionPerformed
            handleButtonClick("TM");

    }//GEN-LAST:event_btnT14ActionPerformed

    private void btnT12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnT12ActionPerformed
            handleButtonClick("TN");

    }//GEN-LAST:event_btnT12ActionPerformed

    private void btnT15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnT15ActionPerformed
            handleButtonClick("TO");

    }//GEN-LAST:event_btnT15ActionPerformed
   // This is the action of the back button. 
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
          pickMenu.setVisible(true);
          PlaceOrder.setVisible(false);
          PickSeat.setVisible(false);
          trackOrder.setVisible(false);
    }//GEN-LAST:event_btnBackActionPerformed
    private void btnBack3ActionPerformed(java.awt.event.ActionEvent evt) {                                        
          pickMenu.setVisible(true);
          PlaceOrder.setVisible(false);
          PickSeat.setVisible(false);
          trackOrder.setVisible(false);
    }
    private void btnBack1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack1ActionPerformed
          pickMenu.setVisible(false);
          PlaceOrder.setVisible(false);
          PickSeat.setVisible(true);
    }//GEN-LAST:event_btnBack1ActionPerformed
    // These are to handle the button click of adding or removing an item. 

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
      int foodType = 1;
        increaseValue(foodType);
       txtQty.setText(""+ foodItems.get(foodType));
       
       totalPrice = totalPrice + 300;
       txtTotPrice.setText("" + totalPrice);
       
        
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd1ActionPerformed
      int foodType = 2;
        increaseValue(foodType);
               totalPrice = totalPrice + 500;
       txtTotPrice.setText("" + totalPrice);
       txtQty1.setText(""+ foodItems.get(foodType));    }//GEN-LAST:event_btnAdd1ActionPerformed

    private void btnAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd2ActionPerformed
              int foodType = 3;
        increaseValue(foodType);
               totalPrice = totalPrice + 1200;
       txtTotPrice.setText("" + totalPrice);
       txtQty2.setText(""+ foodItems.get(foodType));
    }//GEN-LAST:event_btnAdd2ActionPerformed

    private void btnAdd5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd5ActionPerformed
              int foodType = 4;
        increaseValue(foodType);
       txtQty5.setText(""+ foodItems.get(foodType));
              totalPrice = totalPrice + 400;
       txtTotPrice.setText("" + totalPrice);
    }//GEN-LAST:event_btnAdd5ActionPerformed

    private void btnAdd4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdd4ActionPerformed
              int foodType = 5;
        increaseValue(foodType);
       txtQty4.setText(""+ foodItems.get(foodType));
              totalPrice = totalPrice + 800;
       txtTotPrice.setText("" + totalPrice);
    }//GEN-LAST:event_btnAdd4ActionPerformed

    private void btnTrackOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrackOrderActionPerformed
Container parent = null;
try {
    parent = trackOrder.getParent();
} catch(Exception X) {
    addTrackedItems();
    PickSeat.setVisible(false);
    pickMenu.setVisible(false);
}

if (parent != null) {
    System.out.println("Panel Cleared");
    parent.remove(trackOrder);
    parent.revalidate();
    parent.repaint();
    trackOrder = null; // Ensure the panel won't be used again
}

addTrackedItems();
PickSeat.setVisible(false);
pickMenu.setVisible(false);


    }//GEN-LAST:event_btnTrackOrderActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
        
        

        
    }
        private javax.swing.JLabel BackgroundImage3;
    private javax.swing.JPanel itemTemplate;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel picsureOfFood;
    private javax.swing.JPanel trackOrder;
    private javax.swing.JLabel txtFoodName;
    private javax.swing.JLabel txtState;
    private javax.swing.JButton btnBack3;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AdditionalInfo;
    private javax.swing.JLabel BackgroundImage;
    private javax.swing.JLabel BackgroundImage1;
    private javax.swing.JLabel BackgroundImage2;
    private javax.swing.JPanel ItemPanel;
    private javax.swing.JPanel ItemPanel1;
    private javax.swing.JPanel ItemPanel2;
    private javax.swing.JPanel ItemPanel4;
    private javax.swing.JPanel ItemPanel5;
    private javax.swing.JPanel PickSeat;
    private javax.swing.JPanel PlaceOrder;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAdd1;
    private javax.swing.JButton btnAdd2;
    private javax.swing.JButton btnAdd4;
    private javax.swing.JButton btnAdd5;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnBack1;
    private javax.swing.JButton btnCancelOrder;
    private javax.swing.JButton btnOrder;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnRemove1;
    private javax.swing.JButton btnRemove2;
    private javax.swing.JButton btnRemove4;
    private javax.swing.JButton btnRemove5;
    private javax.swing.JButton btnSendOrder;
    private javax.swing.JButton btnT1;
    private javax.swing.JButton btnT10;
    private javax.swing.JButton btnT11;
    private javax.swing.JButton btnT12;
    private javax.swing.JButton btnT13;
    private javax.swing.JButton btnT14;
    private javax.swing.JButton btnT15;
    private javax.swing.JButton btnT2;
    private javax.swing.JButton btnT3;
    private javax.swing.JButton btnT4;
    private javax.swing.JButton btnT5;
    private javax.swing.JButton btnT6;
    private javax.swing.JButton btnT7;
    private javax.swing.JButton btnT8;
    private javax.swing.JButton btnT9;
    private javax.swing.JButton btnTrackOrder;
    private javax.swing.JTextArea inputTxtAdditionalInfo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel makePurchesPanel;
    private javax.swing.JLabel picItem;
    private javax.swing.JLabel picItem1;
    private javax.swing.JLabel picItem2;
    private javax.swing.JLabel picItem4;
    private javax.swing.JLabel picItem5;
    private javax.swing.JPanel pickMenu;
    private javax.swing.JScrollPane scrAdditionalInfo;
    private javax.swing.JLabel txtAdditionalInfo;
    private javax.swing.JLabel txtLine2;
    private javax.swing.JLabel txtPrice;
    private javax.swing.JLabel txtPrice1;
    private javax.swing.JLabel txtPrice2;
    private javax.swing.JLabel txtPrice4;
    private javax.swing.JLabel txtPrice5;
    private javax.swing.JLabel txtQty;
    private javax.swing.JLabel txtQty1;
    private javax.swing.JLabel txtQty2;
    private javax.swing.JLabel txtQty4;
    private javax.swing.JLabel txtQty5;
    private javax.swing.JLabel txtSalad;
    private javax.swing.JLabel txtSalad1;
    private javax.swing.JLabel txtSalad2;
    private javax.swing.JLabel txtSalad4;
    private javax.swing.JLabel txtSalad5;
    private javax.swing.JLabel txtTot;
    private javax.swing.JLabel txtTotPrice;
    private javax.swing.JLabel txtline;
    // End of variables declaration//GEN-END:variables
}
