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
    private static double[][] dividedDifferenceTable;
    private static ArrayList<Double> coefficientsList;
    
    public static void main(String[] args) throws IOException 
    {
        String fileName = "";

        System.out.print("Enter the file name (with extension): ");
        Scanner input = new Scanner(System.in);
        fileName = input.nextLine();
        
        getXValues(fileName);

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

        dividedDifferenceTable = new double[dataLineOne.length][dataLineTwo.length];

        for (int i = 0; i < dataLineOne.length; i++)
        {
            dividedDifferenceTable[i][0] = Double.parseDouble(dataLineOne[i]);
        }

        for (int i = 0; i < dataLineTwo.length; i++)
        {
            dividedDifferenceTable[i][1] = Double.parseDouble(dataLineTwo[i]);
        }

        buffer.close();
    }

    public static void getCoefficients()
    {
        for (int row = 2; row < dividedDifferenceTable[0].length; row++)
        {
            for (int col = 0; col < dividedDifferenceTable[0].length - row; col++)
            {
                dividedDifferenceTable[col][row] = 
                (dividedDifferenceTable[col + 1][row - 1] - dividedDifferenceTable[col][row - 1]) 
                / 
                (dividedDifferenceTable[col + (row - 1)][0] - dividedDifferenceTable[col][0]);
            }
        }

        for (int i = 0; i < dividedDifferenceTable[0].length; i++)
        {
            coefficientsList.add(dividedDifferenceTable[0][i]);
        }
    }
}