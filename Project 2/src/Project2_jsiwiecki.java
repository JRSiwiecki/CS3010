import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Joseph Siwiecki
 *         Assignment: Programming Project 2
 *         Class: CS 3010.01
 *         Date: 10/19/22
 */
public class Project2_jsiwiecki 
{
    /**
     * This program gets user input for a system of linear equations
     * either through the command line or through a file,
     * and uses either the Jacobi Iterative method or the Gauss Seidel method
     * to solve the system. The intermediate steps are displayed,
     * and the solution is displayed.
     * 
     * 
     * @param input             Used to gather user input.
     * @param numberOfEquations Number of equations in the augmented matrix.
     * @param augmentedMatrix   Augmented matrix that stores user input for
     *                          coefficients.
     * @param desiredError      The desired level of error to be achieved, designated
     *                          by the user.
     * @throws FileNotFoundException If file is not found, an FNFE will occur.
     */
    private static Scanner input = new Scanner(System.in);
    private static int numberOfEquations;
    private static double[][] augmentedMatrix;
    private static double[] startingSolution;
    private static double desiredError;
    
    /** 
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException
    {
        getNumberOfEquations();
        addEquationsToArray();
        chooseSolvingMethod();
        printAnswerArray();
        
        // input.close();
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
     * Gets user input for the coefficients of the linear equations,
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

        // eqInput.close();
        // file.close();
    }

    /**
     * Prints the augmented matrix in a neat format.
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
     * Allows user to choose between solving the augmented matrix using the
     * Jacobi Iterative method, or by using the Gauss Seidel method.
     */
    private static void chooseSolvingMethod()
    {
        Scanner in = new Scanner(System.in);

        boolean valid = false;

        while (valid == false) 
        {
            System.out.print("Enter 1 to use Jacobi Iterative Method, or enter 2 to use Gauss Seidel Method: ");
            int choice = input.nextInt();

            if (choice == 1) 
            {
                getStartingSolution();
                jacobiIterativeMethod();
                valid = true;
            }

            else if (choice == 2)
            {
                getStartingSolution();
                gaussSeidelMethod();
                valid = true;
            }

            else 
            {
                System.out.println("Please enter a valid choice.");
            }
        }
    }

    private static void getStartingSolution()
    {
        Scanner in = new Scanner(System.in);
        
        startingSolution = new double[augmentedMatrix.length];
        
        System.out.println("Enter " + (startingSolution.length) + " values for the starting matrix: ");
        in = new Scanner(System.in);

        String input = in.nextLine();

        String[] stringArray = input.split(" ");

        for (int i = 0; i < (startingSolution.length); i++) 
        {
            startingSolution[i] = Integer.parseInt(stringArray[i]);
        }

        System.out.println();
    }

    /**
     * Solves the supplied augmented matrix using the 
     * Jacobi Iterative method.
     */
    private static void jacobiIterativeMethod()
    {
        Scanner in = new Scanner(System.in); 
        
        System.out.print("Enter the desired level of error: ");
        desiredError = in.nextDouble();
        
        double[] previous = new double[augmentedMatrix.length];

        Arrays.fill(previous, 0);

        int iterations = 1;
        boolean done = false;

        while (!done)
        {
            /*
             * 
             */
            for (int row = 0; row < augmentedMatrix.length; row++)
            {
                double sum = 0.0;

                for (int col = 0; col < augmentedMatrix.length; col++)
                {
                    /*
                     * Calculate sum for each element except those on the main diagonal.
                     */
                    if (row != col)
                    {
                        sum += augmentedMatrix[row][col] * previous[col];
                    }
                }

                /*
                 * 
                 */
                startingSolution[row] = (1.0 / augmentedMatrix[row][row]) * (augmentedMatrix[row][augmentedMatrix.length] - sum);
            }

            System.out.print("\nIteration " + iterations + " = ");

            System.out.print("[");

            /*
             * Print out current x column vector in rows.
             */
            for (int i = 0; i < augmentedMatrix.length; i++) 
            {
                if (i == augmentedMatrix.length - 1) 
                {
                    System.out.print(startingSolution[i]);
                }

                else 
                {
                    System.out.print(startingSolution[i] + " ");
                }
            }

            System.out.print("]");

            System.out.println();

            iterations++;

            boolean desiredErrorReached = false;

            /*
             * Check for desired level of error.
             */ 
            for (int i = 0; i < augmentedMatrix.length && !desiredErrorReached; i++)
            {
                double currentError = (Math.abs(startingSolution[i] - previous[i]) / startingSolution[i]);
                
                if (currentError > desiredError)
                {
                    desiredErrorReached = true;
                }
            }

            /*
             * Either desired error has been reached or too many iterations have occurred,
             * so stop iterating.
             */
            if (!desiredErrorReached || iterations > 50)
            {
                done = true;
            }

            previous = startingSolution.clone();
        }

        System.out.println();
        // in.close();
    }
    
    /**
     * Solves the supplied matrix using the 
     * Gauss Seidel method.
     */
    private static void gaussSeidelMethod()
    {
        Scanner in = new Scanner(System.in);
        
        double[] previous = new double[augmentedMatrix.length];

        System.out.print("Enter the desired level of error: ");
        desiredError = in.nextDouble();

        int iterations = 1;
        boolean done = false;

        while (!done)
        {
            for (int row = 0; row < augmentedMatrix.length; row++)
            {
                double sum = 0.0;
                
                for (int col = 0; col < augmentedMatrix.length; col++)
                {
                    if (row != col)
                    {
                        sum += augmentedMatrix[row][col] * startingSolution[col];
                    }
                }

                startingSolution[row] = (1.0 / augmentedMatrix[row][row]) * (augmentedMatrix[row][augmentedMatrix.length] - sum);
            }

            System.out.print("\nIteration " + iterations + " = ");

            System.out.print("[");
            
            /*
             * Print out current x column vector in rows.
             */
            for (int i = 0; i < augmentedMatrix.length; i++) 
            {
                if (i == augmentedMatrix.length - 1)
                {
                    System.out.print(startingSolution[i]);
                }

                else 
                {
                    System.out.print(startingSolution[i] + " ");
                }
            }

            System.out.print("]");

            System.out.println();

            iterations++;

            boolean desiredErrorReached = false;

            /*
             * Check for desired level of error.
             */
            for (int i = 0; i < augmentedMatrix.length && !desiredErrorReached; i++) 
            {
                double currentError = (Math.abs(startingSolution[i] - previous[i]) / startingSolution[i]);

                if (currentError > desiredError) 
                {
                    desiredErrorReached = true;
                }
            }

            /*
             * Either desired error has been reached or too many iterations have occurred,
             * so stop iterating.
             */
            if (!desiredErrorReached || iterations > 50) 
            {
                done = true;
            }

            previous = startingSolution.clone();
        }

        System.out.println();
        // in.close();
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