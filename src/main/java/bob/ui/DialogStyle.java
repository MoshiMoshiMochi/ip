package bob.ui;

public enum DialogStyle {
    DEFAULT("label"),
    ADD("add-label"),
    DELETE("delete-label"),
    MARK("mark-label"),
    UNMARK("unmark-label"),
    UPDATE("update-label"),
    ERROR("error-label"),
    REPLY("reply-label"),
    USER("user-label");

    private final String label;

    DialogStyle(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
