public abstract class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    public Task(String description, TaskType type){
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    public void markDone(){
        this.isDone = true;
    }

    public void markUnDone(){
        this.isDone = false;
    }

    public abstract String toSaveFormat();

    public static Task fromSaveFormat(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String desc = parts[2];

            switch (type) {
                case "T":
                    ToDoTask toDoTask = new ToDoTask(desc);
                    if (isDone) {
                        toDoTask.markDone();
                    }
                    return toDoTask;
                case "D":
                    DeadlineTask Deadlinetask = new DeadlineTask(desc, parts[3]);
                    if(isDone){
                        Deadlinetask.markDone();
                    }
                    return  Deadlinetask;
                case "E":
                    EventTask EventTask = new EventTask(desc, parts[3], parts[4]);
                    if(isDone){
                        EventTask.markDone();
                    }
                    return EventTask;
                default:
                    return null; // corrupted line
            }
        } catch (Exception e) {
            return null; // corrupted line
        }
    }

    public TaskType getType(){
        return this.type;
    }

    @Override
    public String toString(){
        String check = this.isDone ? "X" : " ";
        return "[" + type.getSymbol() + "]"+ "[" + check + "] " + this.description;
    }
}
