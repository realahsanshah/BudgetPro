package budgetpro.gui;

import budgetpro.businesslogic.Expense;
import budgetpro.businesslogic.ExpenseList;
import budgetpro.businesslogic.ExpenseType;
import budgetpro.businesslogic.ExpenseTypeList;
import budgetpro.db.DBConnect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


//GUI using Swing (hand coded)
public class MainGUI extends JFrame {

    public final int WEEKLY = 0;
    public final int MONTHLY = 1;


    private Container container;
    private JPanel mainPanel; //mainWindow
    private JPanel homePanel; //Home Window
    private JPanel addPanel; //Window to add new expense in the list
    private JPanel reportPanel; //Window to show all expense in table
    private ExpenseList expenseList; //Object of ExpenseList class to store expenses
    private ExpenseTypeList expenseTypes; //expenseTypes list
    private String[][] expenseData; //2D Array to render on JTable
    private DBConnect dbConnect;

    //Constructor
    public MainGUI() {
        if (dbConnect == null) {
            dbConnect = new DBConnect("db/expensedb.db");
            dbConnect.connect();
        }
        if (expenseList == null)
            expenseList = new ExpenseList();

        if (expenseTypes == null)
            expenseTypes = new ExpenseTypeList();


        //Read Expense Types from db
        readExpenseTypes();
        //Read ExpenseList from db
        readExpenseList();

        //get current window pane
        container = this.getContentPane();

        //initializing panel to show JTable of expense list
        reportPanel = new JPanel(new GridLayout(2, 1));

        //initializing Main panel
        mainPanel = new JPanel();

        //It will create Home panel
        createHomePanel();

        //It will create Panel to Add new Expense
        createAddExpensePanel();

        //It will call the method to create and set Menu Bar
        this.setJMenuBar(createMenuBar());

        //It will add all panels to mainPanel
        //Currently all Sub Panels visibility is false
        mainPanel.add(homePanel);
        mainPanel.add(addPanel);
        mainPanel.add(reportPanel);

        //Showing Home Panel to the window
        homePanel.setVisible(true);

        //Set Size
        this.setSize(600, 600);
        //Exit on clicking Close button
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        mainPanel.setVisible(true);
        container.add(mainPanel);
        this.setTitle("Budget Pro");
        this.setVisible(true);
    }

    // It will create and return menu bar
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        //Creating a file menu in menubar
        JMenu fileMenu = new JMenu("File");

        //Adding first option new in File Menu
        JMenuItem newItem = new JMenuItem("NEW");

        //Adding action listener to New
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // It will disappear all other windows panel, and only show addPanel to add Expenses
                homePanel.setVisible(false);
                reportPanel.setVisible(false);
                addPanel.setVisible(true);
            }
        });

        //adding NEW to File
        fileMenu.add(newItem);

        //Adding Save Item to File
        JMenuItem saveItem = new JMenuItem("SAVE");
        fileMenu.add(saveItem);
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveExpenseList();
                saveExpenseTypes();

                JOptionPane.showMessageDialog(null, "Data Saved", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //It will save the data and exit the program
        JMenuItem exitItem = new JMenuItem("EXIT");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveExpenseTypes();
                saveExpenseList();
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        //Report Menu with WEEKLY and MONTHLY Report option
        JMenu expenseMenu = new JMenu("Report");
        JMenuItem weeklyItem = new JMenuItem("WEEKLY REPORT");
        expenseMenu.add(weeklyItem);
        weeklyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //It will create weekly data and show it
                createReportData(WEEKLY);
                createReportPanel();

                homePanel.setVisible(false);
                addPanel.setVisible(false);
                reportPanel.setVisible(true);

            }
        });


        JMenuItem monthlyItem = new JMenuItem("MONTHLY REPORT");
        expenseMenu.add(monthlyItem);
        monthlyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //It will create monthly data and show it
                createReportData(MONTHLY);
                createReportPanel();
                homePanel.setVisible(false);
                addPanel.setVisible(false);
                reportPanel.setVisible(true);

            }
        });

        menuBar.add(expenseMenu);

        JMenu aboutMenu = new JMenu("About");
        aboutMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Budget Management System", "About", JOptionPane.INFORMATION_MESSAGE);

            }
        });

        menuBar.add(aboutMenu);

        //it will return the menu bar
        return menuBar;

    }

    //It will create the add Expense panel to add new Expenses
    public void createAddExpensePanel() {
        addPanel = new JPanel(new GridLayout(3, 1));
        JPanel addExpensePanel = new JPanel(new GridLayout(4, 2));
        addPanel.setVisible(false);

        JLabel expenseTypeLabel = new JLabel("Expense Type");
        JComboBox<ExpenseType> expenseType = new JComboBox<ExpenseType>();


        for (ExpenseType exp : expenseTypes.getExpenseTypes())
            expenseType.addItem(exp);


        JLabel expenseDetailLabel = new JLabel("Expense Details");
        JTextField expenseDetailText = new JTextField(10);
        JLabel amountLabel = new JLabel("Amount");
        JTextField amountText = new JTextField(10);
        amountText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && (c != KeyEvent.VK_BACK_SPACE))
                    amountText.setEditable(false);
                else
                    amountText.setEditable(true);
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        JButton submitButton = new JButton("OK");

        addExpensePanel.add(expenseTypeLabel);
        addExpensePanel.add(expenseType);
        addExpensePanel.add(expenseDetailLabel);
        addExpensePanel.add(expenseDetailText);
        addExpensePanel.add(amountLabel);
        addExpensePanel.add(amountText);
        addExpensePanel.add(new JLabel(), 6);
        addExpensePanel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (expenseDetailText.getText().compareTo("") == 0 || amountText.getText().compareTo("") == 0) {
                    JOptionPane.showMessageDialog(null, "Please fill the values");
                    return;
                }

//                Expense expense = new Expense(new ExpenseType((String)expenseType.getSelectedItem()), expenseDetailText.getText(), Double.parseDouble(amountText.getText()));
                expenseList.addExpense(new Expense(0, (ExpenseType) expenseType.getSelectedItem(),expenseDetailText.getText(), Double.parseDouble(amountText.getText())));
                expenseType.setSelectedIndex(0);
                expenseDetailText.setText("");
                amountText.setText("");
            }
        });


        JPanel addExpenseTypePanel = new JPanel(new GridLayout(3, 2));
        JLabel addExpenseTypeLabel = new JLabel("Expense Type");
        JTextField addExpenseTypeText = new JTextField(10);
        JButton addExpenseTypeSubmit = new JButton("OK");

        addExpenseTypeSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addExpenseTypeText.getText() != "") {
//                    expenseTypes.add(new ExpenseType(expenseTypes.size(),addExpenseTypeText.getText()));
                    ExpenseType exp=new ExpenseType(expenseTypes.getSize(),addExpenseTypeText.getText());
                    expenseTypes.addExpenseType(exp);

                    expenseType.addItem(exp);
                    addExpenseTypeText.setText("");
                    JOptionPane.showMessageDialog(null, "Expense Type added Successfully", "Success Message", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        addExpenseTypePanel.add(addExpenseTypeLabel);
        addExpenseTypePanel.add(addExpenseTypeText);
        addExpenseTypePanel.add(new JLabel());
        addExpenseTypePanel.add(addExpenseTypeSubmit);

        addPanel.add(addExpensePanel);
        addPanel.add(new JLabel("Add New Expense Type")).setFont(new Font("TimesRoman", Font.BOLD, 16));

        addPanel.add(addExpenseTypePanel);


    }


    //It will create home panel
    public void createHomePanel() {
        homePanel = new JPanel();
        JLabel welcomeLabel = new JLabel("Welcome to Expense Management System");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setFont(new Font("TimesRoman", Font.BOLD, 16));
        homePanel.add(welcomeLabel);
    }

    //It will create the reports data either for WEEKLY or MONTHLY
    public void createReportData(int reportType) {
        //Filtering the expenses
        ArrayList<Expense> expenses;

        if (reportType == WEEKLY)
            expenses = expenseList.getWeeklyExpenses();
        else if (reportType == MONTHLY)
            expenses = expenseList.getMonthlyExpenses();
        else {
            return;
        }

        expenseData = new String[expenses.size()][5];
        for (int i = 0; i < expenses.size(); i++) {
            expenseData[i][0] = Integer.toString(i + 1);
            expenseData[i][1] = String.valueOf(expenses.get(i).getType());
            expenseData[i][2] = expenses.get(i).getDetails();
            expenseData[i][3] = Double.toString(expenses.get(i).getAmount());
            expenseData[i][4] = expenses.get(i).getFormattedDate();
        }


    }

    //It will create the report panel to show data in JTable
    public void createReportPanel() {

        reportPanel.removeAll();
        JPanel tablePanel = new JPanel();
        JPanel summaryPanel = new JPanel(new GridLayout(1, 2));
        JTable expenseTable;
        String[] columnName = {"SR", "TYPE", "DETAIL", "AMOUNT", "DATE"};
        expenseTable = new JTable(expenseData, columnName);
        tablePanel.add(expenseTable);

        JScrollPane scrollPane = new JScrollPane(expenseTable);
        tablePanel.add(scrollPane);

        double totalExpense = 0;
        for (int i = 0; i < expenseData.length; i++) {
            totalExpense += Double.parseDouble(expenseData[i][3]);
        }
        JLabel summaryLabel = new JLabel("Summary" + totalExpense);
        JTextArea summary = new JTextArea();

        summary.setColumns(12);
        summary.setRows(1);
        summary.setText(Double.toString(totalExpense));
        summaryPanel.add(summaryLabel);
        summaryPanel.add(summary);

//        tablePanel.add(summaryPanel);

        reportPanel.add(tablePanel);
        reportPanel.add(summary);
        reportPanel.setVisible(false);

    }


    //to save Expense types and Expense List using Serialization

    public void saveExpenseTypes() {
//        FileOutputStream fileOutputStream = null;
//        ObjectOutputStream outputStream = null;
//        try {
//            fileOutputStream = new FileOutputStream("expenseTypes.dat");
//            outputStream = new ObjectOutputStream(fileOutputStream);
//            outputStream.writeObject(expenseTypes);
//            System.out.println("Types Saved");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                outputStream.close();
//                fileOutputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        for (ExpenseType exp : expenseTypes.getExpenseTypes()) {
            dbConnect.addExpenseType(exp.getId(), exp.getName());
        }
    }

    public void readExpenseTypes() {
        expenseTypes.removeAll();
        expenseTypes.readExpenseType(dbConnect);
    }

    public void saveExpenseList() {

        for(Expense exp:expenseList.getExpenses()) {
            dbConnect.addExpense(exp.getId(),exp.getAmount(),exp.getType(),exp.getDetails(),exp.getFormattedDate());
        }
    }

    public void readExpenseList() {
//        FileInputStream fileInputStream = null;
//        ObjectInputStream inputStream = null;
//        try {
//            fileInputStream = new FileInputStream("expenseList.dat");
//            inputStream = new ObjectInputStream(fileInputStream);
//            expenseList = (ExpenseList) inputStream.readObject();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                inputStream.close();
//                fileInputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        expenseList.removeAll();
        expenseList.readExpenseType(dbConnect, expenseTypes);

    }

    }



