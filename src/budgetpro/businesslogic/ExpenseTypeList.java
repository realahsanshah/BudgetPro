package budgetpro.businesslogic;

import budgetpro.db.DBConnect;

import java.util.ArrayList;

public class ExpenseTypeList {
    private ArrayList<ExpenseType> expenseTypes;

    public ExpenseTypeList() {
        this.expenseTypes =new ArrayList<>();
    }

    public void addExpenseType(ExpenseType expenseType){
        if(expenseType==null)
            return;
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

    public int getSize(){
        return expenseTypes.size();
    }

    public void removeAll(){
        expenseTypes.removeAll(expenseTypes);
    }

    public void readExpenseType(DBConnect dbConnect){
        dbConnect.readExpenseType(expenseTypes);
    }

    public ArrayList<ExpenseType> getExpenseTypes() {
        ArrayList<ExpenseType> temp=new ArrayList<>();
        for(ExpenseType expenseType:expenseTypes){
            temp.add(new ExpenseType(expenseType));
        }
        return temp;
    }
}
