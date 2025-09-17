package bob.personality;

/**
 * Enum representing different personality messages used in the application.
 */
public enum Personality {
    // Exception messages
    BOBEXCEPTION(" What the BOB!!![BobException]\n"),
    BOBDATETIMEEXCEPTION(" What the Bob!!![BobDateTimeException]\n"),
    BOBINVALIDFORMATEXCEPTION(
            " What the Bob!!![BobInvalidFormatException]\n Invalid Command Format! Expected: "
    ),
    BOBINVALIDINDEXEXCEPTION(" What the Bob!!![BobInvalidIndexException]\n"),
    INVALID_INDEX_MESSAGE(" Invalid Task number!"),
    INVALID_COMMAND_MESSAGE("  Invalid Command!"),
    INDEX_OUT_OF_RANGE_MESSAGE1(" Task number "),
    INDEX_OUT_OF_RANGE_MESSAGE2(" does not exist!"),

    // Command messages
    // AddCommand
    ADDINTRO(" Aite. I've bobbed it into the list:"),

    // DeleteCommand
    REMOVEINTRO("  BOB!!! I've removed the task:"),

    // MarkCommand
    MARKINTRO(" I'm Marking it. I'm Marking it so good!"),

    // UnMarkCommand
    UNMARKINTRO(" You need to BOB mark! BOB for Viltrum!"),

    // FindCommand
    FINDINTRO_SUCCESS_1(" BOB YEA! Here is/are the "),
    FINDINTRO_SUCCESS_2(" task(s)\n based on the given description: "),
    FINDOUTRO_SUCCESS("\n I've been a good BOB"),
    FINDINTRO_FAILURE(" NOOO BOB! No Bobbing tasks within the list"
            + "\n matches the description for: "),
    FINDOUTRO_FAILURE("\n Maybe trying another Bobbing description!"),

    // ListCommand
    LISTINTRO(" The list do be Bobbing my dude!"),

    // ByeCommand
    BYE(" Bye have a great time!"),

    // UpdateCommand
    UPDATEINTRO1(" BOBBIDY BOB BOB! I've up(bob)ed the provided task."),
    UPDATEINTRO2(" Old Task: "),
    UPDATEINTRO3(" Updated Task: "),

    // General Use
    TAB("    "),
    ADDDELETEOUTRO_1(" Bobbing heck! You now have "),
    ADDDELETEOUTRO_2(" tasks in the list."),
    GREETING1(" Hows it bobbing dude?! I'm Bob"),
    GREETING2(" What can I do for you?"),
    LINE("____________________________________________________________"),;

    private final String message;

    /**
     * Constructs a Personality enum with the specified message.
     *
     * @param message The message associated with the personality.
     */
    Personality(String message) {
        this.message = message;
    }

    /**
     * Returns the message associated with the personality.
     *
     * @return The personality message.
     */
    public String getMessage() {
        return message;
    }
}
