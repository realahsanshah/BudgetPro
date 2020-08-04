package budgetpro.businesslogic;

import java.util.UUID;

public class ExpenseType {
    private String id;
    private String name;

    public ExpenseType(String name){
        id= UUID.randomUUID().toString();
        this.name=name;
    }

    public ExpenseType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ExpenseType(ExpenseType expenseType) {
        this.id = expenseType.getId();
        this.name = expenseType.getName();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return name;
    }
}
