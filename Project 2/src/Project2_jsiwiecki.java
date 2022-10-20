import java.io.*;
import java.util.Scanner;

/**
 * @author Joseph Siwiecki
 *         Assignment: Programming Project 1
 *         Class: CS 3010.01
 *         Date: 10/9/22
 */
public class Project2_jsiwiecki 
{
    /**
     * This program gets user input for a system of linear equations
     * either through the command line or through a file,
     * and uses Gaussian Elimination with Scaled Partial Pivoting
     * to solve the system. The intermediate steps are displayed,
     * and the solution is displayed.
     * 
     * Using Gaussian Elimination with Scaled Partial Pivoting will be
     * more accurate and precise than using Naive Gaussian Elimination or even
     * just standard Partial Pivoting.
     * 
     * @param input             Used to gather user input.
     * @param numberOfEquations Number of equations in the augmented matrix.
     * @param augmentedMatrix   Augmented matrix that stores user input for
     *                          coefficients.
     * @throws FileNotFoundException If file is not found, an FNFE will occur.
     */
    private static Scanner input = new Scanner(System.in);
    private static int numberOfEquations;
    private static double[][] augmentedMatrix;
    
    
    /** 
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        getNumberOfEquations();
        addEquationsToArray();
        gaussianEliminationWithScaledPartialPivoting();
        printAnswerArray();
        
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

        /*
         * Create two more Scanners because using the standard input scanner for the whole class
         * does not operate properly. All scanners will be closed to prevent memory leaks.
         */
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
                
                System.out.println("\n------------- STARTING MATRIX -------------\n");
                printAugmentedMatrix();
                System.out.println("---------------------------------------------");
                done = true;

            } 
            
            /*
             * For file input, the entire file path must be specified for the file to read properly.
             * In your IDE, right click on the file to get its file path, and then use right click again 
             * but on the terminal instead when prompted for the file path.
             */
            else if (choice == 2) 
            {
                String filePath = "";
                
                System.out.println("Enter the full path of the file, along with the file extension [Use right click to paste file path]: ");
                eqInput = new Scanner(System.in);
                
                filePath = eqInput.nextLine();
                File inputFile = new File(filePath);
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
                
                System.out.println("\n------------- STARTING MATRIX -------------\n");
                printAugmentedMatrix();
                System.out.println("---------------------------------------------");
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

    /**
     * Uses Gaussian Elimination with Scaled Partial Pivoting to solve augmented matrices
     * holding up to 10 linear equations. Prints intermediate steps during solving process.
     */
    private static void gaussianEliminationWithScaledPartialPivoting()
    {
        /*
         * Find highest pivot value to prepare for swapping a row to work with partial pivoting.
         */
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
            
            /*
             * Swap array rows based on pivot values.
             */
            double[] swapArray = augmentedMatrix[pivot];
            augmentedMatrix[pivot] = augmentedMatrix[maxCoefficient];
            augmentedMatrix[maxCoefficient] = swapArray;

            /*
             * Calculate the scaled value for each array and convert augmentedMatrix' values
             * to be consistent with the scaled value.
             */
            for (int i = (pivot + 1); i < augmentedMatrix.length; i++)
            {
                double scaledCoefficient = augmentedMatrix[i][pivot] / augmentedMatrix[pivot][pivot];

                for (int j = pivot; j < augmentedMatrix[0].length; j++)
                {
                    augmentedMatrix[i][j] = augmentedMatrix[i][j] - (scaledCoefficient * augmentedMatrix[pivot][j]);
                }
                
                System.out.println("\n------------- INTERMEDIATE STEP -------------\n");
                printAugmentedMatrix();
                System.out.println("---------------------------------------------\n");

            }
        }
    }

    /**
     * Creates an array holding the solved variables and prints them.
     */
    private static void printAnswerArray()
    {
        /*
         * Calculate the value of the variables and place them in the answer array.
         */
        double[] answerArray = new double[augmentedMatrix[0].length];

        int minLength = (Math.min(augmentedMatrix[0].length - 1, augmentedMatrix.length - 1));

        for (int i = minLength; i >= 0; i--)
        {
            double sum = 0.0;

            for (int j = (i + 1); j < augmentedMatrix[0].length; j++)
            {
                sum += augmentedMatrix[i][j] * answerArray[j];
            }

            answerArray[i] = (augmentedMatrix[i][augmentedMatrix.length] - sum) / augmentedMatrix[i][i];
        }

        /*
         * Print the answer array with up to 10 variables.
         */
        char[] variables = { 'x', 'y', 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g' };

        System.out.println("Solved Variables: ");

        for (int i = 0; i < augmentedMatrix.length; i++) 
        {
            System.out.print(variables[i] + " = " + answerArray[i] + "\n");
        }

        System.out.println();
    }
}