import java.io.*;
import java.util.Scanner;

/**
 * @author Joseph Siwiecki
 *         Assignment: Programming Project 1
 *         Class: CS 3010.01
 *         Date: 10/7/22
 */
public class Project1_jsiwiecki 
{
    /**
     * This program gets user input for a system of linear equations
     * and uses Gaussian Elimination with Scaled Partial Pivoting
     * to solve the system. The intermediate steps are displayed,
     * and the solution is
     * @param input             Used to gather user input.
     * @param numberOfEquations Number of equations in the augmented matrix.
     * @param augmentedMatrix   Augmented matrix that stores user input for
     *                          coefficients.
     * @throws FileNotFoundException If file is not found, an FNFE will occur.
     */
    private static Scanner input = new Scanner(System.in);
    private static int numberOfEquations;
    private static double[][] augmentedMatrix;
    
    public static void main(String[] args) throws FileNotFoundException
    {
        getNumberOfEquations();
        addEquationsToArray();
        gaussianEliminationWithScaledPartialPivoting();
        input.close();
    }

    /**
     * Retrieves number of equations from user and stores the input
     * into numberOfEquations.
     */
    private static void getNumberOfEquations() 
    {
        boolean valid = false;

        while (valid == false) 
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

    /**
     * This method gets user input for the coefficients of the linear equations,
     * and inputs them into the augmentedMatrix. The user can input the 
     * coefficients from the command line, or from a file.
     * @throws FileNotFoundException If file is not found, an FNFE will occur.
     */
    private static void addEquationsToArray() throws FileNotFoundException
    {
        augmentedMatrix = new double[numberOfEquations][numberOfEquations + 1];
        
        int choice = 0, numsEntered = 0;
        boolean done = false;
        
        System.out.print("Enter 1 to input values using CLI, enter 2 to input values using file input: ");

        Scanner eqInput = new Scanner(System.in);
        Scanner file = new Scanner(System.in);

        while (done == false) 
        {
            choice = input.nextInt();

            if (choice == 1) 
            {
                while (augmentedMatrix.length > numsEntered) 
                {
                    System.out.println("Enter " + (augmentedMatrix[0].length) + " values for row " + (numsEntered + 1) + ": ");

                    eqInput = new Scanner(System.in);
                    String input = eqInput.nextLine();
                    String[] stringArray = input.split(" ");

                    for (int i = 0; i < (augmentedMatrix[0].length); i++) 
                    {
                        augmentedMatrix[numsEntered][i] = Integer.parseInt(stringArray[i]);
                    }
                    
                    numsEntered++;
                }
                
                printAugmentedMatrix();
                done = true;
            } 
            
            else if (choice == 2) 
            {
                String fileName = "";
                System.out.println("Enter name of file [with extension]: ");
                eqInput = new Scanner(System.in);
                
                fileName = eqInput.nextLine();
                File inputFile = new File(fileName);
                file = new Scanner(inputFile);

                while (augmentedMatrix.length > numsEntered && file.hasNextLine()) 
                {
                    String input = file.nextLine();
                    String[] stringArray = input.split(" ");

                    for (int i = 0; i < (augmentedMatrix[0].length); i++) 
                    {
                        augmentedMatrix[numsEntered][i] = Integer.parseInt(stringArray[i]);
                    }
                    numsEntered++;
                }
                
                printAugmentedMatrix();
                done = true;

            } 
            
            else 
            {
                System.out.print("Invalid choice, please enter 1 or 2: ");
            }

        } 

        eqInput.close();
        file.close();
    }

    /**
     * This function prints the augmented matrix in a neat format.
     */
    private static void printAugmentedMatrix()
    {
        for (double[] linearEquation : augmentedMatrix) 
        {
            for (double coefficient : linearEquation) 
            {
                System.out.print("[" + coefficient +"] ");
            }
            System.out.println();  
        }
        System.out.println();
    }

    private static void gaussianEliminationWithScaledPartialPivoting()
    {
        int maxLength = (Math.min(augmentedMatrix.length, augmentedMatrix[0].length));
        for (int pivot = 0; pivot < maxLength; pivot++)
        {
            int maxCoefficient = pivot;

            for (int i = (pivot + 1); i < augmentedMatrix.length; i++)
            {
                if (Math.abs(augmentedMatrix[i][pivot]) > Math.abs(augmentedMatrix[maxCoefficient][pivot]))
                {
                    maxCoefficient = i;
                }
            }
            
            double[] swapArray = augmentedMatrix[pivot];
            augmentedMatrix[pivot] = augmentedMatrix[maxCoefficient];
            augmentedMatrix[maxCoefficient] = swapArray;

            for (int i = (pivot + 1); i < augmentedMatrix.length; i++)
            {
                double scaledCoefficient = augmentedMatrix[i][pivot] / augmentedMatrix[pivot][pivot];

                for (int j = pivot; j < augmentedMatrix[0].length; j++)
                {
                    augmentedMatrix[i][j] = augmentedMatrix[i][j] - (scaledCoefficient * augmentedMatrix[pivot][j]);
                }
                printAugmentedMatrix();
            }
        }
    }
}