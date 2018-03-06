package lt.swedbank.interestcalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CompoundInterestCalculator {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        double amount = readAmount();
        double[] interestRate = readInterestRate();
        int periodLength = readPeriodLength();
        String frequency = readCompoundFrequency();

        double[][] intermediateInterestMatrix = new double[interestRate.length][];
        int periodsPerYear = returnPeriodsPerYear(frequency);

        for (int i = 0; i < intermediateInterestMatrix.length; i++) {
            intermediateInterestMatrix[i] = calculateIntermediateInterests(periodLength, periodsPerYear, amount, interestRate[i]);
        }
        printIntermediateInterestMatrix(intermediateInterestMatrix);

//        double[] yearlyInterests = calculateYearlyInterests(periodLength, amount, periodsPerYear, interestRate);
//        printYearlyInterests(yearlyInterests);
//        printIntermediateInterests(intermediateInterests);
//        System.out.printf("Total amount: %.2f", (amount + yearlyInterests[yearlyInterests.length - 1]));
    }

    private static void printIntermediateInterestMatrix(double[][] intermediateInterestMatrix) {
        for (double[] row : intermediateInterestMatrix) {
            for (double interest : row) {
                System.out.printf(" %.2f", interest);
            }
            System.out.println();
        }
    }


    private static double readAmount() {
        do{
            try{
                System.out.print("Amount: ");
                double amount = scan.nextDouble();
                if (amount <= 0){
                    throw new Exception();
                }
            }  catch (Exception e){
                System.out.println("Invalid input! Try again!");
            }
        }while (true);

    }

    private static double[] readInterestRate() {
        List<Double> interestRates = new ArrayList<>();
        do {
            System.out.print("Interest rate (%): ");
            double tempInterestRate = scan.nextDouble() / 100;
            if (tempInterestRate == 0) {
                break;
            }
            interestRates.add(tempInterestRate);

        } while (true);
        double[] interestRate = new double[interestRates.size()];
        for (int i = 0; i < interestRate.length; i++) {
            interestRate[i] = interestRates.get(i);
        }
        return interestRate;
    }

    private static int readPeriodLength() {
        System.out.print("Period length (years): ");
        return scan.nextInt();
    }

    private static String readCompoundFrequency() {
        System.out.print("Compound frequency: ");
        return scan.next();
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

    private static double[] calculateYearlyInterests(int periodLength, double amount, int periodsPerYear, double interestRate) {
        double[] yearlyInterests = new double[periodLength];
        for (int i = 1; i <= periodLength; i++) {
            yearlyInterests[i - 1] = Math.pow(interestRate / periodsPerYear + 1, i * periodsPerYear) * amount - amount;
        }
        return yearlyInterests;
    }

    private static void printYearlyInterests(double[] yearlyInterests) {
        for (int i = 0; i < yearlyInterests.length; i++) {
            System.out.printf("Interest amount after year " + (i + 1) + ": %.2f\n", yearlyInterests[i]);
        }
    }

    private static void printIntermediateInterests(double[] intermediateInterests) {
        System.out.println("Intermediate interest amounts: " + Arrays.toString(intermediateInterests));

    }
}