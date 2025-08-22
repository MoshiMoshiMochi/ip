package bob.task;

public class ToDoTask extends Task {
    public ToDoTask(String description) {
        super(description, TaskType.TODO);
    }

    @Override
    public String toSaveFormat() {
        return TaskType.TODO.getSymbol() + " | " +
                (this.isDone ? "1" : "0") + " | " +
                this.description + " | " + " | ";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
