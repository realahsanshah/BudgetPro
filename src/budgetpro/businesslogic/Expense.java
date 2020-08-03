package budgetpro.businesslogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Expense {
    private int id;
    private double amount;
    private ExpenseType type;
    private String details;
    private Date expenseDate;
    private SimpleDateFormat formatter;     //to Format date

    public Expense(int id,ExpenseType type,String details,double amount) {
        //Date Formatter
        formatter=new SimpleDateFormat("dd/MM/yyyy");
        this.id=id;
        this.amount = amount;
        this.details = details;
        this.type=type;
        expenseDate=new Date();
    }
    public Expense(int id,ExpenseType type,String details,double amount,String date) {
        //Date Formatter
        formatter=new SimpleDateFormat("dd/MM/yyyy");
        this.id=id;
        this.amount = amount;
        this.details = details;
        this.type=type;
        try {
            this.expenseDate=formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public ExpenseType getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }
}
