package budgetpro.businesslogic;

import budgetpro.db.DBConnect;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ExpenseList {
    private final ArrayList<Expense> expenses;

    public ExpenseList() {
        this.expenses =new ArrayList<>();
    }

    public void addExpense(Expense expense){
        if(expense==null)
            return;
        expenses.add(expense);
    }

    public ArrayList<Expense> getExpenses() {
        ArrayList<Expense> temp=new ArrayList<>();
        for(Expense expense:expenses){
            temp.add(new Expense(expense));
        }
        return temp;
    }

    public ArrayList<Expense> getWeeklyExpenses(){
        ArrayList<Expense> weeklyExpenseList=new ArrayList<>();
        Calendar calendar=Calendar.getInstance();
        Date date=new Date();
        calendar.setTime(date);
        int week =calendar.get(Calendar.WEEK_OF_YEAR);

        for(Expense expense:expenses){
            Date expenseDate=expense.getExpenseDate();
            calendar.setTime(expenseDate);
            int expenseWeek=calendar.get(Calendar.WEEK_OF_YEAR);
            if(expenseWeek==week)
                weeklyExpenseList.add(expense);
        }

        return weeklyExpenseList;
    }

    /**
     * it will return the expense of same month
     * @return
     */
    public ArrayList<Expense> getMonthlyExpenses(){
        ArrayList<Expense> monthlyExpenseList=new ArrayList<>();
        Date date=new Date();
        int month = date.getMonth();
        for (Expense expense : expenses) {
            Date expenseDate = expense.getExpenseDate();
            int expenseMonth = date.getMonth();
            if (expenseMonth == month)
                monthlyExpenseList.add(expense);
        }

        return monthlyExpenseList;
    }

    public void removeAll() {
        expenses.removeAll(expenses);
    }

    public void readExpenseType(DBConnect dbConnect, ExpenseTypeList expenseTypeList) {
        dbConnect.readExpenses(expenses, expenseTypeList.getExpenseTypes());
    }

}
