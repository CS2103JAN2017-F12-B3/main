package seedu.taskmanager.logic.commands;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.logic.commands.exceptions.CommandException;

// @@author A0140032E
/**
 * Finds and lists all tasks in task manager which fall in the date(s) range
 * requirements.
 */
public class FindDateCommand extends Command {

    private boolean isRange;
    private Date startDateRange, endDateRange;

    private static final SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yyyy");

    public static final String COMMAND_WORD = "findbydate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all non-floating tasks which is within "
            + "the specified date or date range and displays them as a list with index numbers.\n"
            + "Parameters: DATE [to DATE]\n" + "Example: " + COMMAND_WORD + " 12/03/2017\n" + COMMAND_WORD
            + "10/03/2017 to 15/03/2017";
    public static final String MESSAGE_INVALID_RANGE = "Invalid date range. "
            + "Either provide a single date or a starting date to ending date";
    public static final String MESSAGE_SUCCESS = "Finding tasks ";

    public FindDateCommand(String args) throws IllegalValueException {
        Parser parser = new Parser();
        List<DateGroup> dateGroups = parser.parse(args);

        if (dateGroups.isEmpty()) {
            throw new IllegalValueException(MESSAGE_INVALID_RANGE);
        }

        switch (dateGroups.get(0).getDates().size()) {

        case 1:
            isRange = false;
            startDateRange = dateGroups.get(0).getDates().get(0);
            break;

        case 2:
            isRange = true;
            startDateRange = dateGroups.get(0).getDates().get(0);
            endDateRange = dateGroups.get(0).getDates().get(1);
            if (startDateRange.after(endDateRange)) {
                Date temp = startDateRange;
                startDateRange = endDateRange;
                endDateRange = temp;
            }
            break;

        default:
            throw new IllegalValueException(MESSAGE_INVALID_RANGE);
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        StringBuilder sb = new StringBuilder(MESSAGE_SUCCESS);
        if (isRange) {
            model.updateFilteredTaskList(startDateRange, endDateRange);
            sb.append(sdfOutput.format(startDateRange));
            sb.append(" to ");
            sb.append(sdfOutput.format(endDateRange));
        } else {
            model.updateFilteredTaskList(startDateRange);
            sb.append("on ");
            sb.append(sdfOutput.format(startDateRange));
        }
        sb.append("\n");
        return new CommandResult(sb.toString() + getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }
}
// @@author
