public class UnMarkCommand implements Command{
    private final int index;

    public UnMarkCommand(int index){
        this.index = index;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        Task task = taskList.getTask(index);
        task.markUnDone();
        ui.showMessage(
                " You need to BOB mark! BOB for Viltrum!",
                "    " + task
        );
    }

    @Override
    public boolean isExit(){
        return false;
    }
}
