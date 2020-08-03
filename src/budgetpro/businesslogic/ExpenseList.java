package budgetpro.businesslogic;

import java.util.ArrayList;

public class ExpenseList {
    private ArrayList<Expense> expenses;

    public ExpenseList() {
        this.expenses =new ArrayList<>();
    }

    public void addExpense(Expense expense){
        if(expense==null)
            return;
        expenses.add(expense);
        expense.setId(expenses.size());
    }

    public ArrayList<Expense> getExpenses() {
        ArrayList<Expense> temp=new ArrayList<>();
        for(Expense expense:expenses){
            temp.add(new Expense(expense));
        }
        return temp;
    }
}
