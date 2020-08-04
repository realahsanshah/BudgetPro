package budgetpro.businesslogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Expense {
    private String id;
    private double amount;
    private ExpenseType type;
    private String details;
    private Date expenseDate;
    private SimpleDateFormat formatter;     //to Format date

    public Expense(ExpenseType type,String details,double amount) {
        //Date Formatter
        formatter=new SimpleDateFormat("dd/MM/yyyy");
        this.id= UUID.randomUUID().toString();
        this.amount = amount;
        this.details = details;
        this.type=type;
        expenseDate=new Date();
    }
    public Expense(String id,ExpenseType type,String details,double amount,String date) {
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
    public Expense(Expense expense) {
        if(expense==null)
            return;
        //Date Formatter
        formatter=new SimpleDateFormat("dd/MM/yyyy");
        this.id=expense.getId();
        this.amount = expense.getAmount();
        this.details = expense.getDetails();
        this.type=expense.getType();
        this.expenseDate=expense.getExpenseDate();
    }

    public String getId() {
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

    public void setId(String id) {
        this.id = id;
    }

    public String getFormattedDate(){
        return formatter.format(expenseDate);
    }


}
