package budgetpro.businesslogic;

public class ExpenseType {
    private int id;
    private String name;

    public ExpenseType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ExpenseType(ExpenseType expenseType) {
        this.id = expenseType.getId();
        this.name = expenseType.getName();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
