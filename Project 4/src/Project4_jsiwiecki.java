/**
 * @author Joseph Siwiecki
 *         Assignment: Programming Project 4
 *         Class: CS 3010.01
 *         Date: 12/4/22
 */


import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Project4_jsiwiecki
{
    public static void main(String[] args) throws FileNotFoundException 
    {
        String fileName = "";

        System.out.print("Enter the file name (with extension): ");
        Scanner input = new Scanner(System.in);
        fileName = input.nextLine() + ".txt";

        getXValues(fileName);
    }

    public static void getXValues(String fileName) throws FileNotFoundException
    {
        FileInputStream dataFile = new FileInputStream(fileName);
        DataInputStream dataInput = new DataInputStream(dataFile);
        BufferedReader buffer = new BufferedReader(new InputStreamReader(dataInput));
    }
}