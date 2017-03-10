package seedu.taskmanager.model.task;

import seedu.taskmanager.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's start date in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartDate(String)}
 */
public class StartDate {

    public static final String MESSAGE_STARTDATE_CONSTRAINTS = "Person start date should only contain numbers";
    public static final String STARTDATE_VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Validates given start date.
     *
     * @throws IllegalValueException if given phone string is invalid.
     */
    public StartDate(String startDate) throws IllegalValueException {
        assert startDate != null;
        String trimmedPhone = startDate.trim();
        if (!isValidStartDate(trimmedPhone)) {
            throw new IllegalValueException(MESSAGE_STARTDATE_CONSTRAINTS);
        }
        this.value = trimmedPhone;
    }

    /**
     * Returns true if a given string is a valid task start date.
     */
    public static boolean isValidStartDate(String test) {
        return test.matches(STARTDATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                && this.value.equals(((StartDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
