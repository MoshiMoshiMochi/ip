public interface Command {
    void execute(TaskList taskList, Ui ui, Storage storage);
    boolean isExit();
}
