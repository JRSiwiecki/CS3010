/**
 * @author Joseph Siwiecki
 *         Assignment: Programming Project 4
 *         Class: CS 3010.01
 *         Date: 12/1/22
 */

import java.io.IOException;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.ArrayList;


public class Project4_jsiwiecki
{
    /**
     * @param xValues Holds the xValues of the input data as strings.
     * @param fValues Holds the f(x) values of the input data as strings.
     * @param rawDividedDifferenceTable Newton's divided difference table as a 2D double array.
     * @param coefficientList Holds the coefficients found when calculating the divided difference table.
     * @param cleanDividedDifferenceTable Newton's divided difference table but as a 2D string array for easy viewing.
     */
    private static String[] xValues;
    private static String[] fValues;
    private static double[][] rawDividedDifferenceTable;
    private static ArrayList<Double> coefficientsList = new ArrayList<Double>();
    private static String[][] cleanDividedDifferenceTable;
    
    
    
    /**
     * Gets file name and opens the file.
     * Retrieves x and f values from file and places them into xValues, fValues,
     * and rawDividedDifference table. Then prints out the table and various
     * polynomials.
     * 
     * @throws IOException In case file can't be found.
     */
    public static void main(String[] args) throws IOException 
    {
        String fileName = "";

        System.out.print("Enter the file name (with extension): ");
        Scanner input = new Scanner(System.in);
        fileName = input.nextLine();
        
        getXValues(fileName);
        solveTableAndGetCoefficients();
        cleanUpDividedDifferenceTable();
        printDividedDifferenceTable();
        printNewtonPolynomial();
        printLagrangePolynomial();
        printSimplifiedPolynomial();

        input.close();
    }

    /**
     * Gets x and f values from input file and and place them in respective arrays and matrices.
     * 
     * @param fileName Name of the file specified by the user and sent from main function.
     * @throws IOException In case file not found.
     */
    private static void getXValues(String fileName) throws IOException
    {
        FileInputStream dataFile = new FileInputStream(fileName);
        DataInputStream dataInput = new DataInputStream(dataFile);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(dataInput));

        String[] dataLineOne = buffer.readLine().split(" ");
        String[] dataLineTwo = buffer.readLine().split(" ");

        xValues = dataLineOne.clone();
        fValues = dataLineTwo.clone();
        
        if (dataLineOne.length != dataLineTwo.length)
        {
            System.out.println("Input data lengths do not match.");
            buffer.close();
            return;
        }

        rawDividedDifferenceTable = new double[dataLineOne.length][dataLineTwo.length + 1];

        for (int i = 0; i < dataLineOne.length; i++)
        {
            rawDividedDifferenceTable[i][0] = Double.parseDouble(dataLineOne[i]);
        }

        for (int i = 0; i < dataLineTwo.length; i++)
        {
            rawDividedDifferenceTable[i][1] = Double.parseDouble(dataLineTwo[i]);
        }

        buffer.close();
    }

    /**
     * Solves the divided difference table in rawDividedDifferenceTable 
     * as well as retrieves the coefficients and places
     * them into coefficientsList.
     */
    private static void solveTableAndGetCoefficients()
    {
        for (int row = 2; row < rawDividedDifferenceTable[0].length; row++)
        {
            for (int col = 0; col < rawDividedDifferenceTable[0].length - row; col++)
            {
                rawDividedDifferenceTable[col][row] = 
                (rawDividedDifferenceTable[col + 1][row - 1] - rawDividedDifferenceTable[col][row - 1]) 
                / 
                (rawDividedDifferenceTable[col + (row - 1)][0] - rawDividedDifferenceTable[col][0]);
            }
        }

        // Coefficients are the top value in each column except for the first column.
        for (int i = 1; i < rawDividedDifferenceTable[0].length; i++)
        {
            coefficientsList.add(rawDividedDifferenceTable[0][i]);
        }
    }

    /**
     * Prints the divided difference table in a much neater way by creating
     * new cleanUpDividedDifferenceTable, which instead uses a 
     * 2D string matrix.
     */
    private static void cleanUpDividedDifferenceTable()
    {
        int tableLengthWithSpaces = 2 * rawDividedDifferenceTable.length;
        int originalTableLength = rawDividedDifferenceTable[0].length;
        cleanDividedDifferenceTable = new String[tableLengthWithSpaces][originalTableLength];
        
        for (int i = 0; i < cleanDividedDifferenceTable.length; i++) 
        {
            for (int j = 0; j < cleanDividedDifferenceTable[i].length; j++) 
            {
                cleanDividedDifferenceTable[i][j] = String.format("%6s", " ");
            }
        }
        
        int spaceLength = 0;

        for (int i = 0; i < rawDividedDifferenceTable.length; i++) 
        {
            cleanDividedDifferenceTable[spaceLength][0] = String.format("%6.2f", rawDividedDifferenceTable[i][0]);
            spaceLength += 2;
        }
        
        spaceLength = 0;

        for (int i = 0; i < rawDividedDifferenceTable.length; i++) 
        {
            cleanDividedDifferenceTable[spaceLength][1] = String.format("%6.2f", rawDividedDifferenceTable[i][1]);
            spaceLength += 2;
        }
        
        for (int i = 2; i < originalTableLength; i++) 
        {
            spaceLength = (i - 1);
            for (int j = 0; j < originalTableLength - i; j++) {
                cleanDividedDifferenceTable[spaceLength][i] = String.format("%6.2f", rawDividedDifferenceTable[j][i]);
                spaceLength += 2;
            }
        }
    }

    /**
     * Prints the cleanDividedDifferenceTable.
     */
    private static void printDividedDifferenceTable() 
    {
        System.out.println("----- Divided Difference Table -----\n");

        for (int row = 0; row < cleanDividedDifferenceTable.length; row++) 
        {
            for (int col = 0; col < cleanDividedDifferenceTable[0].length; col++) 
            {
                System.out.print(cleanDividedDifferenceTable[row][col] + " ");
            }

            System.out.println();
        }
    }
    
    /**
     * Prints the interpolated polynomial in Newton's form.
     */
    private static void printNewtonPolynomial()
    {
        System.out.println("----- Newton's Polynomial -----\n");

        ArrayList<String> xList = new ArrayList<String>();
        String polynomial = "";

        for (int i = 0; i < rawDividedDifferenceTable.length - 1; i++)
        {
            double currentValue = rawDividedDifferenceTable[i][0];

            // Formatting checks
            if (currentValue < 0)
            {
                polynomial = " + ";
            }

            else if (currentValue > 0)
            {
                polynomial = " - ";
            }

            double roundedValue = Math.round(currentValue * 1000) / 1000;
            
            if (roundedValue == 0) 
            {
                xList.add("(x)");
            }

            else
            {
                xList.add(String.format("(x%s%.2f)", polynomial, currentValue));
            }
        }

        String fullPolynomial = String.format("%.2f", coefficientsList.get(0));

        for (int i = 1; i < xList.size() + 1; i++)
        {
            double currentValue = coefficientsList.get(i);

            if (currentValue != 0)
            {
                polynomial = "+";
            }

            else
            {
                polynomial = "-";
            }

            String value = "";

            for (int j = 0; j < i; j++)
            {
                value += xList.get(j);
            }

            fullPolynomial += String.format(" %s %.2f%s", polynomial, Math.abs(currentValue), value);
        }

        System.out.println(fullPolynomial + "\n");
    }

    /**
     * Prints interpolated polynomial in Lagrange's form.
     */
    private static void printLagrangePolynomial()
    {
        System.out.println("----- Lagrange's Polynomial -----\n");

        for (int i = 0; i < fValues.length; i++)
        {
            // print coefficient
            System.out.print(fValues[i] + " * (");
            
            // print numerator
            for (int j = 0; j < xValues.length; j++)
            {
                if (i == j)
                {
                    continue;
                }
                
                else if (Double.parseDouble(xValues[j]) < 0.0)
                {
                    System.out.print("(x + " + Math.abs(Double.parseDouble(xValues[j])) + ")");
                }

                else
                {
                    System.out.print("(x - " + xValues[j] + ")");
                }
            }
            
            System.out.print(" / ");

            // print denominator
            for (int j = 0; j < fValues.length; j++)
            {
                if (i == j)
                {
                    continue;
                }
                
                else if (Double.parseDouble(xValues[j]) < 0.0)
                {
                    System.out.print("(" + xValues[i] + " + " + Math.abs(Double.parseDouble(xValues[j])) + ")");
                }

                else
                {
                    System.out.print("(" + xValues[i] + " - " + xValues[j] + ")");
                }
                
               
            }
            
            
            System.out.print(") ");
            
            // prevent off by one error through short-circuiting
            if (i < fValues.length - 1 && Double.parseDouble(fValues[i + 1]) > 0.0)
            {
                System.out.print("+");
            }

            System.out.print(" ");
        }

        System.out.println("\n");
    }
    
    /**
     * Prints interpolated polynomial in simplified form. 
     */
    private static void printSimplifiedPolynomial()
    {
        System.out.println("----- Simplified Polynomial -----\n");

        Polynomial polynomial = new Polynomial();

        ArrayList<Double> polynomialCoefficients = new ArrayList<Double>();
        ArrayList<ArrayList<Double>> matrix = new ArrayList<ArrayList<Double>>();

        for (int i = 0; i < rawDividedDifferenceTable[0].length - 1; i++)
        {
            polynomialCoefficients.add(0.0);
        }

        polynomialCoefficients.add(0, coefficientsList.get(0));
        matrix.add(polynomialCoefficients);

        for (int i = 1; i < coefficientsList.size(); i++)
        {
            polynomialCoefficients = new ArrayList<Double>();
            double currentCoefficient = coefficientsList.get(i);

            for (int j = 0; j < i; j++)
            {
                polynomialCoefficients.add(rawDividedDifferenceTable[j][0]);
            }
            
            matrix.add(polynomial.expandPolynomial(currentCoefficient, polynomialCoefficients, rawDividedDifferenceTable[0].length));
        }

        polynomialCoefficients = polynomial.simplifyCoefficientList(matrix);
        System.out.println(convertPolynomialCoefficientsToString(polynomialCoefficients));
    }

    /**
     * Converts a given list of coefficients to a string that is easier to digest.
     * 
     * @param list List of coefficients.
     * @return Coefficients as a simple string.
     */
    private static String convertPolynomialCoefficientsToString(ArrayList<Double> list)
    {
        String formattedString = "";
        String power = "";

        for (int i = 0; i < list.size(); i++)
        {
            Double currentCoefficient = list.get(i);
            power = String.format("x^%d", i);

            if (currentCoefficient != 0)
            {
                if (currentCoefficient == 0)
                {
                    formattedString += String.format(" %+.2f", currentCoefficient);
                }

                else
                {
                    formattedString += String.format(" %+.2f%s", currentCoefficient, power);
                }
            }
        }

        return formattedString;
    }
}

/**
 * Helper class meant to perform common polynomial operations.
 * Mainly used by printSimplifiedPolynomial function.
 */
class Polynomial 
{
    
    /**
     * Calculates expanded polynomial given starting value, list of coefficients, and length.
     * 
     * @param startingValue The value to begin the expanded polynomial.
     * @param coefficients The coefficient values.
     * @param length The length of the original list.
     * @return New ArrayList<Double> containing the expanded polynomial values.
     */
    public ArrayList<Double> expandPolynomial(double startingValue, ArrayList<Double> coefficients, int length)
    {
        ArrayList<ArrayList<Double>> matrix = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> newList = new ArrayList<Double>();

        for (int i = 0; i < coefficients.size() + 1; i++)
        {
            newList.add(0.0);
        }
        
        newList.add(0, startingValue);
        
        for (int i = 0; i < coefficients.size(); i++)
        {
            matrix.add(addNewCoefficient(newList));
            matrix.add(multiplyByFactor(newList, -coefficients.get(i)));
            newList = simplifyCoefficientList(matrix);
            matrix.clear();
        }

        int newCoefficientsSize = newList.size();

        for (int i = 0; i < length - newCoefficientsSize; i++)
        {
            newList.add(0.0);
        }

        return newList;
    }

    /**
     * Simplify a matrix comprised of ArrayList<ArrayList<Double>> to be on list of coefficients.
     * 
     * @param originalList The original matrix.
     * @return New ArrayList<Double> containing the coefficients.
     */
    public ArrayList<Double> simplifyCoefficientList(ArrayList<ArrayList<Double>> originalList) 
    {
        ArrayList<Double> newList = new ArrayList<Double>();

        for (int i = 0; i < originalList.get(0).size(); i++) 
        {
            double sumOfList = 0.0;

            for (int j = 0; j < originalList.size(); j++) 
            {
                sumOfList += originalList.get(j).get(i);
            }
            
            newList.add(sumOfList);
        }

        return newList;
    }

    /**
     * Creates a new ArrayList<Double> using the originalList. 
     * Mainly used in expandPolynomial to simplify adding a list of coefficients.
     * 
     * @param originalList The original coefficientList
     * @return The new coefficientList as an ArrayList<Double>
     */
    private ArrayList<Double> addNewCoefficient(ArrayList<Double> originalList)
    {
        ArrayList<Double> newList = new ArrayList<Double>();

        newList.add(0.0);

        for (int i = 0; i < originalList.size() - 1; i++)
        {
            newList.add(originalList.get(i));
        }

        return newList;
    }

    /**
     * Multiplies entire originalList of doubles by a given value and returns modified list.
     * 
     * @param originalList The original list of coefficients.
     * @param factor The value to multiply every element in the list by.
     * @return The modified ArrayList<Double> with updated values.
     */
    private ArrayList<Double> multiplyByFactor(ArrayList<Double> originalList, double factor)
    {
        ArrayList<Double> newList = new ArrayList<Double>();

        for (int i = 0; i < originalList.size(); i++)
        {
            newList.add(originalList.get(i) * factor);
        }

        return newList;
    }
}