public class ListCommand implements Command{
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try{
            ui.showMessage(tasks.toString());
        }catch (BobDateTimeException | BobInvalidFormatException e){
            ui.showMessage(e.getMessage());
        }
    }
    @Override
    public boolean isExit() {
        return false;
    }
}
