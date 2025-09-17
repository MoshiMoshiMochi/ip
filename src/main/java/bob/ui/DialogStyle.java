package bob.ui;

/**
 * Enum representing different styles for dialog boxes in the UI.
 * Each style is associated with a specific label used for styling purposes.
 */
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

    /**
     * Constructs a DialogStyle with the specified label.
     *
     * @param label The label associated with the dialog style.
     */
    DialogStyle(String label) {
        this.label = label;
    }

    /**
     * Returns the label associated with this dialog style.
     *
     * @return The label as a string.
     */
    public String getLabel() {
        return label;
    }
}
