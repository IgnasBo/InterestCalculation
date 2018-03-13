package lt.swedbank.interestcalculator;

import java.util.Arrays;
import java.util.Scanner;

public class CompoundInterestCalculator {
    //Scan could be final, since you're initializing it right away
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        //Very good. You are not declaring local variable at a start of them "main" method (this is a good thing :) ).
        double amount = readAmount();
        double[] interestRate = readInterestRate();
        int periodLength = readPeriodLength();
        String frequency = readCompoundFrequency();

        // Tasks 1-3
/*      double[] yearlyInterests = calculateYearlyInterests(periodLength, amount, periodsPerYear, interestRate);
        printYearlyInterests(yearlyInterests);
        printIntermediateInterests(intermediateInterests);
        System.out.printf("Total amount: %.2f", (amount + yearlyInterests[yearlyInterests.length - 1]));
*/

        double[][] intermediateInterestMatrix = new double[interestRate.length][];
        int periodsPerYear = returnPeriodsPerYear(frequency);

        for (int i = 0; i < intermediateInterestMatrix.length; i++) {
            intermediateInterestMatrix[i] = calculateIntermediateInterests(periodLength, periodsPerYear, amount, interestRate[i]);
        }
        printIntermediateInterestMatrix(intermediateInterestMatrix);

    }

    private static void printIntermediateInterestMatrix(double[][] intermediateInterestMatrix) {
        for (double[] row : intermediateInterestMatrix) {
            for (double interest : row) {
                System.out.printf(" %.2f", interest);
            }
            System.out.println();
        }
    }

    private static double[] calculateIntermediateInterests(int periodLength, int periodsPerYear, double amount, double interestRate) {
        int totalPeriods = periodLength * periodsPerYear;
        double[] intermediateInterests = new double[totalPeriods];
        double intermediateAmount = amount;
        for (int i = 1; i <= intermediateInterests.length; i++) {
            intermediateInterests[i - 1] = Math.pow(interestRate / periodsPerYear + 1, i) * amount - intermediateAmount;
            intermediateAmount += intermediateInterests[i - 1];
        }
        return intermediateInterests;
    }

    private static int returnPeriodsPerYear(String frequency) {
        switch (frequency) {
            case "D":
                return 365;
            case "W":
                return 52;
            case "M":
                return 12;
            case "Q":
                return 4;
            case "H":
                return 2;
            default:
                return 1;
        }
    }

    private static double readAmount() {
        do {
            try {
                System.out.print("Amount: ");
                double amount = scan.nextDouble();
                if (isAmountValid(amount)) {
                    return amount;
                } else {
                    printInputError();
                }
            } catch (Exception e) {
                printInputError();
                scan.nextLine();
            }
        } while (true);

    }

    private static double[] readInterestRate() {
        double[] interestRates = new double[0];
        double tempInterestRate;
        do {
            while (true) {
                try {
                    System.out.print("Interest rate (%): ");
                    tempInterestRate = scan.nextDouble();
                    if (isInterestRateValid(tempInterestRate)) {
                        break;
                    } else {
                        printInputError();
                    }
                } catch (Exception e) {
                    printInputError();
                    scan.nextLine();
                }
            }
            if (tempInterestRate == 0) {
                break;
            }
            //Good. So far nobody used "Arrays.copyOf(...)". You are saving 1 additional variable declaration this way :)
            interestRates = Arrays.copyOf(interestRates, interestRates.length + 1);
            interestRates[interestRates.length - 1] = tempInterestRate / 100;

        } while (true);
        return interestRates;
    }

    private static int readPeriodLength() {
        while (true) {
            try {
                System.out.print("Period length (years): ");
                int periodLength = scan.nextInt();
                if (isPeriodLengthValid(periodLength)) {
                    return periodLength;
                } else {
                    printInputError();
                }
            } catch (Exception e) {
                printInputError();
                scan.nextLine();
            }
        }
    }

    private static String readCompoundFrequency() {
        while (true) {
            try {
                System.out.print("Compound frequency: ");
                String frequency = scan.next();
                if (isCompoundFrequencyValid(frequency)) {
                    return frequency;
                } else {
                    printInputError();
                }
            } catch (Exception e) {
                printInputError();
            }
        }
    }

    private static boolean isAmountValid(double amount) {
        //You can simplify it:
        //return !(amount <= 0);
        if (amount <= 0) {
            return false;
        }
        return true;
    }

    private static boolean isInterestRateValid(double interestRate) {
        //You can simplify it:
        //return !(interestRate > 100) && !(interestRate < 0);
        if (interestRate > 100 || interestRate < 0) {
            return false;
        }
        return true;
    }

    private static boolean isPeriodLengthValid(int periodLength) {
        //You can simplify it:
        //return periodLength >= 1;
        if (periodLength < 1) {
            return false;
        }
        return true;
    }

    private static boolean isCompoundFrequencyValid(String frequency) {
        //Make this a constant
        String testInput = "DWMQHY";
        //You can simplify it:
        //return testInput.contains(frequency);
        //This check won't work correctly if "frequency" length > 1 and "frequency" String is a subset of "testInput" (ex. "DW", "MQH")
        if (!testInput.contains(frequency)) {
            return false;
        }
        return true;
    }

    private static void printInputError() {
        System.out.println("Invalid input! Try again!");
    }

    private static double[] calculateYearlyInterests(int periodLength, double amount, int periodsPerYear, double interestRate) {
        double[] yearlyInterests = new double[periodLength];
        for (int i = 1; i <= periodLength; i++) {
            yearlyInterests[i - 1] = Math.pow(interestRate / periodsPerYear + 1, i * periodsPerYear) * amount - amount;
        }
        return yearlyInterests;
    }

    private static void printYearlyInterests(double[] yearlyInterests) {
        for (int i = 0; i < yearlyInterests.length; i++) {
            //printf should take 2 arguments: 1 - format mask, 2 - vararg of values being formatted. So it should look like this instead:
            //System.out.printf("Interest amount after year %d: %.2f\n", i + 1, yearlyInterests[i]);
            System.out.printf("Interest amount after year " + (i + 1) + ": %.2f\n", yearlyInterests[i]);
        }
    }

    private static void printIntermediateInterests(double[] intermediateInterests) {
        System.out.println("Intermediate interest amounts: " + Arrays.toString(intermediateInterests));

    }
}
