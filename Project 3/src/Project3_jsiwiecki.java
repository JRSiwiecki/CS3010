/**
 * @author Joseph Siwiecki
 *         Assignment: Programming Project 2
 *         Class: CS 3010.01
 *         Date: 11/19/22
 */
public class Project3_jsiwiecki 
{
    /**
     * This program uses various root finding methods to find the root
     * in provided equations.
     * 
     * @param MAX_ITERATIONS The maximum number of iterations to perform.
     * @param error The error value we would like to reach, 1%.
     * @param counter Keeps track of number of iterations
     * @param c The final value we achieved through calculations
     * @param previousC The value we use to track each iteration's final value.
     * @param functionA Tracks if we are using functionA.
     * @param functionB Tracks if we are using functionB
     */
    private static final int MAX_ITERATIONS = 100;
    private static double error = 0.01;
    private static int counter = 0;
    private static double c = 0.0;
    private static double previousC = 0.0;
    private static boolean functionA = true;
    private static boolean functionB = false;
    
    /**
     * Runs tests of each root finding method.
     * @param args
     */
    public static void main(String[] args)
    {
        // bisectionTests();
        newtonRaphsonTests();
        // secantTests();
        // falsePositionTests();
    }

    /**
     * Bisection method tests.
     */
    private static void bisectionTests()
    {
        bisectionMethod(0.0, 1.0, 0.365, functionA);
        bisectionMethod(3.0, 4.0, 3.563, functionA);
        bisectionMethod(1.0, 3.0, 1.922, functionA);

        bisectionMethod(120.0, 130.0, 126.632, functionB);
        bisectionMethod(122.5, 127.5, 126.632, functionB);
        bisectionMethod(125.0, 130.0, 126.632, functionB);
    }

    /**
     * Newton-Raphson method tests.
     */
    private static void newtonRaphsonTests() 
    {
        newtonRaphsonMethod(0.0, 0.365, functionA);
        newtonRaphsonMethod(2.0, 1.922, functionA);
        newtonRaphsonMethod(3.0, 3.563, functionA);

        newtonRaphsonMethod(120.0, 126.632, functionB);
        newtonRaphsonMethod(125.0, 126.632, functionB);
        newtonRaphsonMethod(130.0, 126.632, functionB);
    }
    
    /**
     * Secant method tests.
     */
    private static void secantTests() 
    {
        secantMethod(0.0, 1.0, 0.365, functionA);
        secantMethod(3.0, 4.0, 3.563, functionA);
        secantMethod(1.0, 3.0, 1.922, functionA);

        secantMethod(120.0, 130.0, 126.632, functionB);
        secantMethod(122.5, 127.5, 126.632, functionB);
        secantMethod(125.0, 130.0, 126.632, functionB);
    }
    
    /**
     * False Position method tests.
     */
    private static void falsePositionTests() 
    {
        falsePositionMethod(0.0, 1.0, 0.365, functionA);
        falsePositionMethod(3.0, 4.0, 3.563, functionA);
        falsePositionMethod(1.0, 3.0, 1.922, functionA);

        falsePositionMethod(120.0, 130.0, 126.632, functionB);
        falsePositionMethod(122.5, 127.5, 126.632, functionB);
        falsePositionMethod(125.0, 130.0, 126.632, functionB);
    }
    
    /**
     * Quick calculation for Function A
     * Function A: 2x^3 - 11.7x^2 + 17.7x - 5
     * 
     * @param x Value to be plugged in for the function.
     * @return The input value for functionA
     */
    private static double functionA(double x)
    {
        return ((2.0 * Math.pow(x, 3)) - (11.7 * Math.pow(x, 2)) + (17.7 * x) - 5);
    }

    /**
     * Quick calculation for Function B
     * Function B: x + 10 - xcosh(50 / x)
     * 
     * @param x Value to be plugged in for the function.
     * @return The input value for functionB
     */
    private static double functionB(double x)
    {
        return (x + 10 - (x * Math.cosh((50.0 / x))));
    }

    /**
     * Quick calculation for the derivative of Function A
     * Function A Derivative: 6x^2 - 23.4x + 17.7
     * 
     * @param x Value to be plugged in for the function.
     * @return The input value for functionA
     */
    private static double functionAPrime(double x)
    {
        return ((6.0 * Math.pow(x, 2)) - (23.4 * (x)) + 17.7);
    }

    /**
     * Quick calculation for the derivative of Function B
     * Function B Derivative: ((50 * sinh(50 / x)) / x) - (cosh(50 / x) + 1)
     * 
     * @param x Value to be plugged in for the function.
     * @return The input value for functionB
     */
    private static double functionBPrime(double x)
    {
        return ((50.0 * (Math.sinh(50.0 / x))) / x) - Math.cosh(50.0 / x) + 1.0;
    }

    /**
     * Approximate error calculation function.
     * 
     * @param current The current value.
     * @param previous The previous error value.
     * @return The approximate error as a double.
     */
    private static double approximateError(double current, double previous)
    {
        double approximateError = (Math.abs(current - previous) / current);
        return approximateError;
    }

    /**
     * Calculates the actual error value based on the root.
     * 
     * @param current The current value.
     * @param root The root value.
     * @return The real error value as a double.
     */
    private static double realError(double current, double root)
    {
        double realError = (Math.abs(root - current) / root);
        return realError;
    }

    /**
     * Resets counter, c, and previousC values to prepare for new calculations.
     */
    private static void resetValues()
    {
        counter = 0;
        c = 0;
        previousC = 0;
    }
    
    /**
     * Uses the bisection method to find the root in an initial given interval.
     * Prints out calculations at each iteration and prints final calculation
     * once the error level is achieved.
     * 
     * @param a Input a.
     * @param b Input b.
     * @param root Real root value used for error calculations.
     * @param isFunctionA Check if we are testing for function A or function B.
     */
    private static void bisectionMethod(double a, double b, double root, boolean isFunctionA)
    {
        double aValue, bValue, cValue;

        c = a;
        
        // Get initial function outputs for a, b, and c based on function to test
        if (isFunctionA)
        {
            System.out.println("\n--- Bisection Method with inputs " + a + " and " + b + " for Function A ---\n");
            aValue = functionA(a);
            bValue = functionA(b);
            cValue = functionA(c);
        }

        else
        {
            System.out.println("\n--- Bisection Method with inputs " + a + " and " + b + " for Function B ---\n");
            aValue = functionB(a);
            bValue = functionB(b);
            cValue = functionB(c);
        }
        
        // If the outputs of a and b from the function are not negative, then a root is not 
        // found in the given interval.
        if (aValue * bValue >= 0)
        {
            System.out.println("No roots found within " + a + " and " + b + " .\n");
            return;
        }

        do
        {
            // Store the previous c value before calculating new one, to
            // calculate error values later.
            previousC = c;
            c = (a + b) / 2;
            
            // Calculate new outputs for each iteration.
            if (isFunctionA)
            {
                aValue = functionA(a);
                bValue = functionA(b);
                cValue = functionA(c);
            }

            else
            {
                aValue = functionB(a);
                bValue = functionB(b);
                cValue = functionB(c);
            }
            
            // if the output of the function using c is 0, then we are at the root.
            if (cValue == 0)
            {
                break;
            }

            // If the outputs of the function with a and c are negative, then we need to 
            // move closer to b.
            else if (cValue * aValue < 0)
            {
                b = c;
            }

            // If the outputs of the function with a and c are negative, then we need to
            // move closer to a.
            else if (cValue * bValue < 0)
            {
                a = c;
            }

            // Calculate error values and increment counter
            double approximation = approximateError(c, previousC) * 100;
            double trueError = realError(c, root) * 100;
            counter++;

            System.out.println("Iteration: " + counter);

            System.out.printf(" x = %4.4f with relative error: %-4.2f with true error: %-6.2f",
                    c, approximation, trueError);
            
            System.out.println("\n");

        } while ((approximateError(c, previousC) >= error && counter < MAX_ITERATIONS));

        System.out.print("As of " + counter + " iterations, ");
        System.out.printf("x = %.4f", c);
        System.out.println("\n------------------");
        resetValues();
    }

    /**
     * Uses the Newton-Raphson method to find the root with an initial starting point.
     * Prints out calculations at each iteration and prints final calculation
     * once the error level is achieved.
     * 
     * @param a Input a
     * @param root Real root value used for error calculations.
     * @param isFunctionA Check if we are testing for function A or function B.
     */
    private static void newtonRaphsonMethod(double a, double root, boolean isFunctionA) 
    {
        double value, valuePrime;
        
        boolean done = false;

        // Get initial function outputs for a based on function and its derivative to test
        if (isFunctionA)
        {
            System.out.println("\n--- Newton-Raphson Method with input " + a + " for Function A ---\n");
            value = functionA(a);
            valuePrime = functionAPrime(a);
        }

        else
        {
            System.out.println("\n--- Newton-Raphson Method with input " + a + " for Function B ---\n");
            value = functionB(a);
            valuePrime = functionBPrime(a);
        }
        
        while (!done && counter < MAX_ITERATIONS)
        {
            // Calculate new c value to test
            c = a - (value / valuePrime);
            
            if (isFunctionA)
            {
                value = functionA(a);
                valuePrime = functionAPrime(a);
            }

            else
            {
                value = functionB(a);
                valuePrime = functionBPrime(a);
            }

            // Calculate error values and increment counter
            double approximation = Math.abs(approximateError(c, a) * 100);
            double trueError = Math.abs(realError(c, root) * 100);
            counter++;

            System.out.println("Iteration: " + counter);

            System.out.printf(" x = %4.4f with relative error: %-4.2f with true error: %-6.2f",
                    c, approximation, trueError);
            
            System.out.println("\n");

            // Next iteration, a will be the new value to test
            a = c;

            if (approximation <= error)
            {
                done = true;
            }
        }

        System.out.print("As of " + counter + " iterations, ");
        System.out.printf("x = %.4f", c);
        System.out.println("\n------------------");
        resetValues();
        
    }

    /**
     * Uses the Secant method to find the root with an initial starting interval.
     * Prints out calculations at each iteration and prints final calculation
     * once the error level is achieved.
     * 
     * @param a Input a
     * @param b Input b
     * @param root Real root value used for error calculations.
     * @param isFunctionA Check if we are testing for function A or function B.
     */
    private static void secantMethod(double a, double b, double root, boolean isFunctionA) 
    {
        double aValue, bValue;
        
        // Calculate initial a and b values based on function to test
        if (isFunctionA)
        {
            System.out.println("\n--- Secant Method with input " + a + " and " + b + "  for Function A ---\n");
            aValue = functionA(a);
            bValue = functionA(b);
        }

        else 
        {
            System.out.println("\n--- Secant Method with input " + a + " and " + b + "  for Function B ---\n");
            aValue = functionB(a);
            bValue = functionB(b);
        }
        
        boolean done = false;

        while (!done && counter < MAX_ITERATIONS)
        {
            if (isFunctionA) 
            {
                aValue = functionA(a);
                bValue = functionA(b);
            }

            else
            {
                aValue = functionB(a);
                bValue = functionB(b);
            }

            // Calculate new c value
            c = b - ((bValue) * (b - a)) / (bValue - aValue);

            // Calculate error values and increment counter
            double approximation = Math.abs(approximateError(c, a) * 100);
            double trueError = Math.abs(realError(c, root) * 100);
            counter++;

            a = b;
            b = c;

            System.out.println("Iteration: " + counter);

            System.out.printf(" x = %4.4f with relative error: %-4.2f with true error: %-6.2f",
                    c, approximation, trueError);

            System.out.println("\n");

            if (error > approximation)
            {
                done = true;
            }
        }

        System.out.print("As of " + counter + " iterations, ");
        System.out.printf("x = %.4f", c);
        System.out.println("\n------------------");
        resetValues();
    }

    /**
     * Uses the False Position method to find the root with an initial starting interval.
     * Prints out calculations at each iteration and prints final calculation
     * once the error level is achieved.
     * 
     * @param a Input a
     * @param b Input b
     * @param root Real root value used for error calculations.
     * @param isFunctionA Check if we are testing for function A or function B.
     */
    private static void falsePositionMethod(double a, double b, double root, boolean isFunctionA) 
    {
        double aValue, bValue, cValue;

        c = a;

        if (isFunctionA) 
        {
            System.out.println("\n--- False Position Method with input " + a + " and " + b + "  for Function A ---\n");
            aValue = functionA(a);
            bValue = functionA(b);
            cValue = functionA(c);
        }

        else 
        {
            System.out.println("\n--- False Position Method with input " + a + " and " + b + "  for Function B ---\n");
            aValue = functionB(a);
            bValue = functionB(b);
            cValue = functionB(c);
        }

        do 
        {
            previousC = c;
            c = a - ((aValue * (b - a)) / (bValue - aValue));
            
            if (isFunctionA)
            {
                aValue = functionA(a);
                bValue = functionA(b);
                cValue = functionA(c);
            }

            else
            {
                aValue = functionB(a);
                bValue = functionB(b);
                cValue = functionB(c);
            }

            if (cValue == 0)
            {
                break;
            }

            else if (cValue * aValue < 0)
            {
                b = c;
            } 

            else if (cValue * bValue < 0)
            {
                a = c;
            }

            double approximation = Math.abs(approximateError(c, previousC) * 100);
            double trueError = Math.abs(realError(c, root) * 100);
            counter++;

            System.out.println("Iteration: " + counter);

            System.out.printf(" x = %4.4f with relative error: %-4.2f with true error: %-6.2f",
                    c, approximation, trueError);

            System.out.println("\n");
        } while (approximateError(c, previousC) >= error && counter < MAX_ITERATIONS);

        System.out.print("As of " + counter + " iterations, ");
        System.out.printf("x = %.4f", c);
        System.out.println("\n------------------");
        resetValues();
    }
}
