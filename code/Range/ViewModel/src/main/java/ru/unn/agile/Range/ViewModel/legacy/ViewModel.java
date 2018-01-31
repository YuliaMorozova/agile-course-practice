package ru.unn.agile.Range.ViewModel.legacy;

import ru.unn.agile.Range.Model.Boundary;
import ru.unn.agile.Range.Model.Range;

import java.util.List;

public class ViewModel {
    public ViewModel(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
        inputRange = "";
        inputArgument = "";
        operation = Operation.GET_ALL_POINTS;
        result = "";
        messageText = Message.DEFAULT_RANGE;
        range = null;
        argumentRange = null;
        argumentSetOfNumbers = null;
        calculateButtonEnabled = false;
        inputArgumentTextFieldEnabled = false;
    }

    public List<String> getLog() {
        return logger.getLog();
    }

    public String editingFinishedLogMessage() {
        return LogMessages.EDITING_FINISHED
                + "Input range is "
                + ("".equals(inputRange) ? "empty" : inputRange) + "; "
                + "input argument is "
                + ("".equals(inputArgument) ? "empty" : inputArgument) + "; "
                + "operation is "
                + operation.name + ".";
    }

    public String calculateLogMessage() {
        String inputArgumentIs = "";
        if (operation == Operation.CONTAINS_POINTS) {
            inputArgumentIs = "input set of numbers is "
                    + inputArgument + "; ";
        } else if (operation == Operation.ARE_EQUAL_RANGES || operation == Operation.CONTAINS_RANGE
                || operation == Operation.OVERLAPS_RANGE) {
            inputArgumentIs = "input argument range is "
                    + inputArgument + "; ";
        }
        return LogMessages.CALCULATE_WAS_PRESSED
                + "Input range is "
                + inputRange + "; "
                + inputArgumentIs
                + "operation is "
                + operation.name + ". "
                + "Result is "
                + result + ". ";
    }

    public Boolean isCalculateButtonEnabled() {
        return calculateButtonEnabled;
    }

    public Boolean isInputArgumentTextFieldEnabled() {
        return inputArgumentTextFieldEnabled;
    }

    public void setInputRange(final String inputRange) {
        if (!this.inputRange.equals(inputRange)) {
            this.inputRange = inputRange;
            this.calculateButtonEnabled = canParseAll();
            logger.log(editingFinishedLogMessage());
        }
    }

    public void setInputArgument(final String inputArgument) {
        if (!this.inputArgument.equals(inputArgument)) {
            this.inputArgument = inputArgument;
            this.calculateButtonEnabled = canParseAll();
            logger.log(editingFinishedLogMessage());
        }
    }

    public void setOperation(final Operation operation) {
        if (this.operation != operation) {
            this.operation = operation;
            this.calculateButtonEnabled = canParseAll();
            logger.log(LogMessages.OPERATION_WAS_CHANGED + operation.toString());
        }
    }

    public Boolean canParseAll() {
        try {
            inputArgumentTextFieldEnabled = false;
            if (parseInputRange()) {
                if (operation == Operation.ARE_EQUAL_RANGES || operation == Operation.CONTAINS_RANGE
                        || operation == Operation.OVERLAPS_RANGE) {
                    inputArgumentTextFieldEnabled = true;
                    return parseInputArgumentRange();
                }
                if (operation == Operation.CONTAINS_POINTS) {
                    inputArgumentTextFieldEnabled = true;
                    return parseInputArgumentSetOfNumbers();
                }
                return true;
            } else {
                return false;
            }
        } catch (IllegalArgumentException e) {
            result = "";
            messageText = Message.error(e);
            return false;
        }
    }

    public void calculate() {
        switch (this.operation) {
            case GET_ALL_POINTS:
                result = setOfNumbersToString(range.getAllPoints());
                break;
            case GET_END_POINTS:
                result = setOfNumbersToString(range.getEndPoints());
                break;
            case ARE_EQUAL_RANGES:
                if (range.equals(argumentRange)) {
                    result = "These ranges are equal";
                } else {
                    result = "These ranges are not equal";
                }
                break;
            case CONTAINS_POINTS:
                if (range.containsValues(argumentSetOfNumbers)) {
                    result = "The range contains these values";
                } else {
                    result = "The range doesn't contain these values";
                }
                break;
            case CONTAINS_RANGE:
                if (range.containsRange(argumentRange)) {
                    result = "The range contains this range";
                } else {
                    result = "The range doesn't contain this range";
                }
                break;
            case OVERLAPS_RANGE:
                if (range.overlapsRange(argumentRange)) {
                    result = "These ranges overlap";
                } else {
                    result = "These ranges don't overlap";
                }
                break;
            default:
                break;
        }
        logger.log(calculateLogMessage());
    }

    public String getResult() {
        return result;
    }

    public String getMessageText() {
        return messageText;
    }

    private boolean parseInputRange() {
        if ("".equals(inputRange)) {
            messageText = Message.DEFAULT_RANGE;
            return false;
        }
        if (!inputRange.matches(REGEX_RANGES)) {
            messageText = Message.INCORRECT_INPUT_RANGE;
            return false;
        }
        range = getRangeFromString(inputRange);
        if (range != null) {
            messageText = Message.CORRECT_INPUT_RANGE;
            return true;
        } else {
            return false;
        }
    }

    private boolean parseInputArgumentRange() {
        if ("".equals(inputArgument)) {
            messageText = Message.DEFAULT_ARGUMENT_RANGE;
            return false;
        }
        if (!inputArgument.matches(REGEX_RANGES)) {
            messageText = Message.INCORRECT_INPUT_ARGUMENT_RANGE;
            return false;
        }
        argumentRange = getRangeFromString(inputArgument);
        if (argumentRange != null) {
            messageText = Message.CORRECT_INPUT_ARGUMENT_RANGE;
            return true;
        } else {
            return false;
        }
    }

    private boolean parseInputArgumentSetOfNumbers() {
        if ("".equals(inputArgument)) {
            messageText = Message.DEFAULT_ARGUMENT_SET_OF_NUMBERS;
            return false;
        }
        if (!inputArgument.matches(REGEX_SETS)) {
            messageText = Message.INCORRECT_INPUT_SET_OF_NUMBERS;
            return false;
        }
        argumentSetOfNumbers = getSetOfNumbersFromString(inputArgument);
        messageText = Message.CORRECT_INPUT_SET_OF_NUMBERS;
        return true;
    }

    private boolean isIncludedBoundary(final char bracket) {
        return bracket == '[' || bracket == ']';
    }

    private Range getRangeFromString(final String strRange) {
        int indexOfLastSymbol = strRange.length() - 1;
        int leftValue = 0;
        int rightValue = 0;
        boolean leftIsIncluded = isIncludedBoundary(strRange.charAt(0));
        boolean rightIsIncluded = isIncludedBoundary(strRange.charAt(indexOfLastSymbol));

        String[] strValues = strRange.substring(1, indexOfLastSymbol).split(",");
        leftValue = Integer.parseInt(strValues[0]);
        rightValue = Integer.parseInt(strValues[1]);

        Boundary boundaryLeft = new Boundary(leftValue, leftIsIncluded);
        Boundary boundaryRight = new Boundary(rightValue, rightIsIncluded);
        try {
            return new Range(boundaryLeft, boundaryRight);
        } catch (IllegalArgumentException e) {
            result = "";
            messageText = Message.error(e);
            return null;
        }
    }

    private int[] getSetOfNumbersFromString(final String strSet) {
        int lengthOfSet = strSet.length();
        String[] strValues = strSet.substring(1, lengthOfSet - 1).split(",");
        int[] setOfNumbers = new int[strValues.length];
        for (int i = 0; i < strValues.length; i++) {
            setOfNumbers[i] = Integer.parseInt(strValues[i]);
        }
        return setOfNumbers;
    }

    private String setOfNumbersToString(final int[] setOfNumbers) {
        int lengthOfSet = setOfNumbers.length;
        String strValues = "";
        for (int i = 0; i < lengthOfSet - 1; i++) {
            strValues += setOfNumbers[i] + ", ";
        }
        strValues += setOfNumbers[lengthOfSet - 1];
        return strValues;
    }

    public enum Operation {
        GET_ALL_POINTS("Get all integer points of the range"),
        GET_END_POINTS("Get the boundary points of the range"),
        ARE_EQUAL_RANGES("Check the equivalence of ranges"),
        CONTAINS_POINTS("Check the occurrence of the set of points"),
        CONTAINS_RANGE("Check for range entry"),
        OVERLAPS_RANGE("Check the overlap of ranges");

        Operation(final String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }

        private final String name;
    }

    public static final class Message {
        public static final String DEFAULT_RANGE = "Please enter a range and select an action.\n";
        public static final String DEFAULT_ARGUMENT_RANGE = "Please enter range for calculate.\n";
        public static final String DEFAULT_ARGUMENT_SET_OF_NUMBERS = "Please enter set of numbers"
                + " for calculate.\n";
        public static final String CORRECT_INPUT_RANGE = "You entered a correct range.\n";
        public static final String CORRECT_INPUT_ARGUMENT_RANGE = "You entered a correct "
                + "range for calculate.\n";
        public static final String CORRECT_INPUT_SET_OF_NUMBERS = "You entered a correct"
                + " set of numbers for calculate.\n";
        public static final String INCORRECT_INPUT_RANGE = "Incorrect input range:\n"
                + "the range must begin with a character '(' or '[' and end ')' or ']'.\n"
                + "Inside the parentheses there must be two integers\nseparated by a comma.";
        public static final String INCORRECT_INPUT_ARGUMENT_RANGE = "Incorrect input range for "
                + "calculate:\nthe range must begin with a character '(' or '[' and end ')' or ']'"
                + ".\nInside the parentheses there must be two integers\nseparated by a comma.";
        public static final String INCORRECT_INPUT_SET_OF_NUMBERS = "Incorrect input set of"
                + " numbers for calculate:\nthe set of numbers must be separated by commas"
                + "and enclosed in {}.\n";

        public static String error(final Exception e) {
            return "Error: " + e.getMessage();
        }

        private Message() {
        }
    }

    public final class LogMessages {
        public static final String CALCULATE_WAS_PRESSED = "Calculate. ";
        public static final String OPERATION_WAS_CHANGED = "Operation was changed to ";
        public static final String EDITING_FINISHED = "Updated input. ";

        private LogMessages() {
        }
    }

    private Boolean calculateButtonEnabled = false;
    private Boolean inputArgumentTextFieldEnabled = false;
    private String inputRange = "";
    private String inputArgument = "";
    private Operation operation = Operation.GET_ALL_POINTS;
    private String result = "";
    private String messageText = Message.DEFAULT_RANGE;
    private Range range;
    private Range argumentRange;
    private int[] argumentSetOfNumbers;
    private ILogger logger;
    private static final String REGEX_RANGES = "[\\[\\(]\\-*\\d+,\\-*\\d+[\\]\\)]";
    private static final String REGEX_SETS = "\\{(\\-*\\d+,)*\\-*\\d+\\}";
}
