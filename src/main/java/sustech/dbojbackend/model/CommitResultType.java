package sustech.dbojbackend.model;

public enum CommitResultType {
    AC(0), // pass
    WA(1), // wrong answer
    TLE(2), // time too long
    MLE(3), // cost too much memory
    RE(4); // Run Time Error

    private int order;

    private CommitResultType(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}