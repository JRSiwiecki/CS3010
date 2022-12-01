/**
 * @author Joseph Siwiecki
 *         Assignment: Programming Project 4
 *         Class: CS 3010.01
 *         Date: 12/4/22
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
    private static double[][] rawDividedDifferenceTable;
    private static ArrayList<Double> coefficientsList = new ArrayList<Double>();
    private static String[][] cleanDividedDifferenceTable;
    
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
        printInterpolatingPolynomial();

        input.close();
    }

    public static void getXValues(String fileName) throws IOException
    {
        FileInputStream dataFile = new FileInputStream(fileName);
        DataInputStream dataInput = new DataInputStream(dataFile);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(dataInput));

        String[] dataLineOne = buffer.readLine().split(" ");
        String[] dataLineTwo = buffer.readLine().split(" ");

        if (dataLineOne.length != dataLineTwo.length)
        {
            System.out.println("Input data lengths do not match.");
            buffer.close();
            return;
        }

        rawDividedDifferenceTable = new double[dataLineOne.length][dataLineTwo.length];

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

    public static void solveTableAndGetCoefficients()
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

        for (int i = 0; i < rawDividedDifferenceTable[0].length; i++)
        {
            coefficientsList.add(rawDividedDifferenceTable[0][i]);
        }
    }

    public static void cleanUpDividedDifferenceTable()
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

    public static void printDividedDifferenceTable() 
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

    public static double roundNumber(double value)
    {
        return (double) Math.round(value * 1000) / 1000;
    }
    
    public static void printInterpolatingPolynomial()
    {
        System.out.println("Interpolating Polynomial: ");

        ArrayList<String> list = new ArrayList<String>();
        String polynomial = "";

        for (int i = 0; i < rawDividedDifferenceTable.length - 1; i++)
        {
            double currentValue = rawDividedDifferenceTable[i][0];

            if (currentValue < 0)
            {
                polynomial = "+";
            }

            else if (currentValue > 0)
            {
                polynomial = "-";
            }

            if (roundNumber(currentValue) == 0) 
            {
                list.add("(x)");
            }

            else
            {
                list.add(String.format("(x%s%.2f)", polynomial, currentValue));
            }
        }

        String fullPolynomial = String.format("%.2f", coefficientsList.get(0));

        for (int i = 1; i < list.size() + 1; i++)
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
                value += list.get(j);
            }

            fullPolynomial += String.format(" %s %.2f%s", polynomial, Math.abs(currentValue), value);
        }

        System.out.println(fullPolynomial);
    }
}