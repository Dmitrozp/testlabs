package ua.com.foxminded.labs.task4.division;

public class Division {

    public String getDivision(int dividend, int divisor) {
        validate(dividend, divisor);

        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);

        if (dividend < divisor) {
            return "" + dividend + "/" + divisor + "=0";
        }
        if (dividend == divisor) {
            return "" + dividend + "/" + divisor + "=1";
        }

        StringBuilder calcutationOfDivision = getBody(dividend, divisor);

        StringBuilder result = addHeader(calcutationOfDivision, dividend, divisor);
        return result.toString();
    }

    private StringBuilder getBody(int dividend, int divisor) {
        StringBuilder resultOfCalculation = new StringBuilder();
        StringBuilder quotient = new StringBuilder();
        StringBuilder reminder = new StringBuilder();
        String[] digits = String.valueOf(dividend).split("");
        int lengthDivisor = (int) (Math.log10(divisor) + 1);
        int startPointPrint = 0;

        for (int i = 0; i < digits.length; i++) {
            reminder.append(digits[i]);
            Integer reminderNumbers = Integer.parseInt(reminder.toString());
            Integer numberInQuotient;
            Integer firstNumberReminder;
            Integer subtractedFromRemainder;

            if (reminderNumbers >= divisor) {
                numberInQuotient = reminderNumbers / divisor;
                quotient.append(numberInQuotient);
                subtractedFromRemainder = divisor * numberInQuotient;
                firstNumberReminder = reminderNumbers - subtractedFromRemainder;

                int lengthReminder = getLength(reminderNumbers);
                int lengthSubtractedFromRemainder = getLength(subtractedFromRemainder);
                int lengthSubResult = getLength(firstNumberReminder);

                String printReminder = repeatSymbols(startPointPrint, " ") + "_" + reminder;
                resultOfCalculation.append(printReminder);
                resultOfCalculation.append("\n");

                String printSubtractedFromRemainder = repeatSymbols(startPointPrint + 1, " ") + repeatSymbols(lengthReminder - lengthSubtractedFromRemainder, " ") + subtractedFromRemainder;
                resultOfCalculation.append(printSubtractedFromRemainder);
                resultOfCalculation.append("\n");

                String printTab = repeatSymbols(startPointPrint + 1, " ") + repeatSymbols(reminder.length(), "-");
                resultOfCalculation.append(printTab);
                resultOfCalculation.append("\n");

                startPointPrint = i + 1 - lengthSubResult;
                if(firstNumberReminder == 0 ){startPointPrint = i+1;}
                reminder.delete(0, lengthReminder);
                if(firstNumberReminder != 0){ reminder.append(firstNumberReminder);}

            } else if (i >= lengthDivisor) {
                quotient.append(0);
            }

            if (i == digits.length - 1) {
                if(reminder.length() == 0){
                    resultOfCalculation.append(repeatSymbols(startPointPrint, " ") + "0" + "\n");
                } else {
                    resultOfCalculation.append(repeatSymbols(startPointPrint+1, " ") + reminder + "\n");
                }
            }
        }
        return resultOfCalculation;
    }

    private StringBuilder addHeader(StringBuilder calculation, Integer dividend, Integer divisor) {
        StringBuilder resultWithHeader = new StringBuilder();
        int[] index = new int[3];
        int counterSymbols = 0;

        for (int i = 0; i < calculation.length(); i++) {
            if (calculation.charAt(i) == '\n') {
                index[counterSymbols] = i;
                counterSymbols++;
            }
            if (counterSymbols == 3) {
                break;

            }
        }
        int lengthString = getLength(dividend) + 1 - index[0];
        calculation.insert(index[2], repeatSymbols(lengthString, " ") + "│" + dividend / divisor);
        calculation.insert(index[1], repeatSymbols(lengthString, " ") + "│" + repeatSymbols(getLength(dividend / divisor), "-"));
        calculation.insert(index[0], "│" + divisor);
        calculation.replace(1, index[0], dividend.toString());

        return resultWithHeader.append(calculation);
    }

    private void validate(int dividend, int divisor) {
        if (divisor == 0) {
            throw new IllegalArgumentException("Divisor cannot be 0, division by zero");
        }
        if (dividend == Integer.MIN_VALUE || divisor == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Dividend or division cannot be Min Int, enter number < -2147483648");
        }
    }

    private String repeatSymbols(int length, String characters) {
        StringBuilder printTabs = new StringBuilder();

        for (int i = 0; i < length; i++) {
            printTabs.append(characters);
        }
        return printTabs.toString();
    }

    private int getLength(int amount) {
        return (int) (Math.log10(amount) + 1);
    }
}
