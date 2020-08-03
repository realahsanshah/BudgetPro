package budgetpro.db;

import budgetpro.businesslogic.Expense;
import budgetpro.businesslogic.ExpenseType;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBConnect {

    private String path;
    private Connection connection;
    public DBConnect(String path) {
        this.path = "jdbc:sqlite:"+path;
    }

    public void connect() {
       connection= null;
        try {
            connection = DriverManager.getConnection(path);
            System.out.println("Connection to SQLite has been established.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void addExpenseType(int id,String name){
        String query="INSERT INTO expenseType(id,name) VALUES(?,?);";

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,id);
            pstmt.setString(2,name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addExpense(int id,double amount,ExpenseType expenseType,String detail,String dated){
        String query="INSERT INTO expense(amount,expenseType,detail,dated,id) VALUES(?,?,?,?,?);";

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setDouble(1,amount);
            pstmt.setInt(2,expenseType.getId());
            pstmt.setString(3,detail);
            pstmt.setString(4,dated);
            pstmt.setInt(5,id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void readExpenseType(ArrayList<ExpenseType> expenseTypes){
        if(expenseTypes==null)
            return;
        ExpenseType temp;
        String query="SELECT * FROM expenseTypes";
        try {

            Statement statement = connection.createStatement();

            ResultSet resultSet=statement.executeQuery(query);

            while (resultSet.next()){
                temp=new ExpenseType(resultSet.getInt("id"),resultSet.getString("name"));
                expenseTypes.add(temp);
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }

    public void readExpenses(ArrayList<Expense> expenses,ArrayList<ExpenseType> expenseTypes){
        if(expenses==null)
            return;
        Expense temp;
        ExpenseType expenseType;
        String query="SELECT * FROM expense;";
        int id;
        int expenseTypeId;
        double amount;
        String details;
        String expenseDate;
        try {

            Statement statement = connection.createStatement();

            ResultSet resultSet=statement.executeQuery(query);



            while (resultSet.next()){
                id=resultSet.getInt("id");
                expenseTypeId=resultSet.getInt("expenseType");


                for(ExpenseType type:expenseTypes){

                }
            }

        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
