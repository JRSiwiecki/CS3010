/**
 * @author Joseph Siwiecki
 *         Assignment: Programming Project 4
 *         Class: CS 3010.01
 *         Date: 12/4/22
 */


import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Project4_jsiwiecki
{
    private static double[][] dividedDifferenceTable;
    
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

        System.out.println(Arrays.toString(dataLineOne));
        System.out.println(Arrays.toString(dataLineTwo));

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
}