package budgetpro.businesslogic;

import java.util.ArrayList;

public class ExpenseTypeList {
    ArrayList<ExpenseType> expenseTypes;

    public ExpenseTypeList() {
        this.expenseTypes =new ArrayList<>();
    }

    public void addExpenseType(ExpenseType expenseType){
        expenseTypes.add(expenseType);
        expenseType.setId(expenseTypes.size());
    }

    public String getExpenseTypeName(int id) {
        for (ExpenseType expenseType : expenseTypes) {
            if (expenseType.getId() == id)
                return expenseType.getName();
        }
        return null;
    }

    public ArrayList<ExpenseType> getExpenseTypes() {
        ArrayList<ExpenseType> temp=new ArrayList<>();
        for(ExpenseType expenseType:expenseTypes){
            temp.add(new ExpenseType(expenseType));
        }
        return temp;
    }
}
