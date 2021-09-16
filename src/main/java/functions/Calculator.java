package functions;

import config.DriverSetup;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculator {
    private static List<Integer> numbers = new ArrayList(Arrays.asList(
            KeyEvent.VK_0,
            KeyEvent.VK_1,
            KeyEvent.VK_2,
            KeyEvent.VK_3,
            KeyEvent.VK_4,
            KeyEvent.VK_5,
            KeyEvent.VK_6,
            KeyEvent.VK_7,
            KeyEvent.VK_8,
            KeyEvent.VK_9
    ));

    /**
     * Compute the given array of values.
     *
     * @param operator The operator to use for the computation.
     * @param val      The array of values to compute.
     */
    private static Double compute(String operator, String... val) throws Exception {
        List<String> operands = new ArrayList(Arrays.asList(val));
        if (!operands.isEmpty()) {
            // Loop all the given values.
            for (int i = 0; i < operands.size(); i++) {
                // Get the character length of the value (e.g., 10.21 equals 5 characters)
                int charSize = operands.get(i).length();
                for (int j = 0; j < charSize; j++) {
                    // Each character must be individually pressed on the calculator.
                    press(Character.toString(operands.get(i).charAt(j)));
                    DriverSetup.pause(1);
                }
                // Press the operator symbol only if the last value is not entered.
                // The equal sign must be clicked to get the total value.
                if ((i + 1) < operands.size()) {
                    press(operator);
                }
            }
            press("="); // Compute by clicking the equals symbol.
            return getTotal(operator, val);
        }
        return null;
    }

    /**
     * Add all the given values
     *
     * @param val The values to add.
     * @throws AWTException
     */
    public static Double add(String... val) throws Exception {
        return compute("+", val);
    }

    /**
     * Subtract all the given values
     *
     * @param val The values to subtract.
     * @throws AWTException
     */
    public static Double subtract(String... val) throws Exception {
        return compute("-", val);
    }

    /**
     * Returns the integer code of the clear action.
     */
    public static void clear() throws AWTException {
        press("C");
    }

    /**
     * Presses the given command on the calculator.
     *
     * @param s Accepts 0-9, +, -, /, *, C, ., and =.
     * @return The corresponding integer code.
     */
    public static void press(String s) throws AWTException {
        Robot robot = new Robot();
        Integer event = 0;

        if (isNumeric(s)) {
            event = numbers.get(Integer.parseInt(s));
        } else {
            // Common operators and options
            switch (s.toUpperCase()) {
                case "/":
                    event = KeyEvent.VK_DIVIDE;
                    break;
                case "*":
                    event = KeyEvent.VK_ASTERISK;
                    break;
                case "+":
                    event = KeyEvent.VK_ADD;
                    break;
                case "-":
                    event = KeyEvent.VK_MINUS;
                    break;
                case "=":
                    event = KeyEvent.VK_EQUALS;
                    break;
                case ".":
                    event = KeyEvent.VK_PERIOD;
                    break;
                case "C":
                    event = KeyEvent.VK_C;
                    break;
            }
        }
        // If invalid, should throw an exception.
        robot.keyPress(event);
    }

    private static Double getTotal(String operator, String... val) {
        List<String> operands = new ArrayList(Arrays.asList(val));
        Double total = 0.0;
        for (int i = 0; i < operands.size(); i++) {
            switch (operator) {
                case "/":
                    total /= Double.parseDouble(operands.get(i));
                    break;
                case "*":
                    total *= Double.parseDouble(operands.get(i));
                    break;
                case "+":
                    total += Double.parseDouble(operands.get(i));
                    break;
                case "-":
                    total -= Double.parseDouble(operands.get(i));
                    break;
            }
        }
        return total;
    }

    private static boolean isNumeric(String s) {
        return s.matches("^[0-9]*$");
    }

}
