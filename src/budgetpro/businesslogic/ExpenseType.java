package budgetpro.businesslogic;

public class ExpenseType {
    private int id;
    private String name;

    public ExpenseType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
