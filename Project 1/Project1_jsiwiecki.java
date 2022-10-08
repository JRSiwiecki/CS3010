/*
 * Name: Joseph Siwiecki
 * Assignment: Programming Project 1
 * Class: CS 3010.01
 * Date: 10/7/22
 */

import java.util.InputMismatchException;
import java.util.Scanner;

 public class Project1_jsiwiecki 
{
    private static Scanner input = new Scanner(System.in);
    private static int numberOfEquations;
    private static double[][] augmentedMatrix;
    
    public static void main(String[] args)
    {
        getNumberOfEquations();
    }

    private static void getNumberOfEquations() 
    {
        boolean valid = false;

        while (!valid) 
        {
            System.out.print("# of linear equations to solve: ");
            int tempNum = input.nextInt();

            if (tempNum > 0 && tempNum <= 10) 
            {
                numberOfEquations = tempNum;
                valid = true;
            }

            else
            {
                System.out.println("Please enter a valid integer. [0 < numberOfEquations <= 10]");
            }  
        }
    }

    private static void addEquationsToArray() 
    {

    }
}