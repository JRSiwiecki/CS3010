
/**
 * @author Joseph Siwiecki
 *         Assignment: Programming Project 2
 *         Class: CS 3010.01
 *         Date: 11/19/22
 */
public class Project3_jsiwiecki 
{
    private static final int MAX_ITERATIONS = 100;
    private static double error = 0.01;
    private static int counter = 0;
    private static double c = 0.0;
    private static double previousC = 0.0;
    private static boolean functionA = true;
    private static boolean functionB = false;
    
    public static void main(String[] args)
    {
        // bisectionTests();
        // newtonRaphsonTests();
        secantTests();
        // falsePositionTests();
    }

    private static void bisectionTests()
    {
        bisectionMethod(0.0, 4.0, 0.365, functionA);
        bisectionMethod(0.0, 3.0, 1.922, functionA);
        bisectionMethod(1.0, 3.0, 3.563, functionA);

        bisectionMethod(120.0, 130.0, 126.632, functionB);
        bisectionMethod(122.5, 127.5, 126.632, functionB);
        bisectionMethod(125.0, 130.0, 126.632, functionB);
    }

    private static void newtonRaphsonTests() 
    {
        newtonRaphsonMethod(1.0, 0.365, functionA);
        newtonRaphsonMethod(2.0, 1.922, functionA);
        newtonRaphsonMethod(3.0, 3.563, functionA);

        newtonRaphsonMethod(120.0, 126.632, functionB);
        newtonRaphsonMethod(125.0, 126.632, functionB);
        newtonRaphsonMethod(130.0, 126.632, functionB);
    }
    
    private static void secantTests() 
    {
        secantMethod(0.0, 4.0, 0.365, functionA);
        secantMethod(0.0, 3.0, 1.922, functionA);
        secantMethod(1.0, 3.0, 3.563, functionA);

        secantMethod(120.0, 130.0, 126.632, functionB);
        secantMethod(122.5, 127.5, 126.632, functionB);
        secantMethod(125.0, 130.0, 126.632, functionB);
    }
    
    private static void falsePositionTests() 
    {

    }
    
    /**
     * Quick calculation for Function A
     * Function A: 2x^3 - 11.7x^2 + 17.7x - 5
     * @param x Value to be plugged in for the function.
     * @return The input value for functionA
     */
    private static double functionA(double x)
    {
        return ((2.0 * Math.pow(x, 3)) - (11.7 * Math.pow(x, 2)) + (17.7 * x) - 5);
    }

    private static double functionB(double x)
    {
        return (x + 10 - (x * Math.cosh((50.0 / x))));
    }

    private static double functionAPrime(double x)
    {
        return ((6.0 * Math.pow(x, 2)) - (23.4 * (x)) + 17.7);
    }

    private static double functionBPrime(double x)
    {
        return ((50.0 * (Math.sinh(50.0 / x))) / x) - Math.cosh(50.0 / x) + 1.0;
    }

    private static double approximateError(double current, double previous)
    {
        double approximateError = (Math.abs(current - previous) / current);
        return approximateError;
    }

    private static double realError(double current, double root)
    {
        double realError = (Math.abs(root - current) / root);
        return realError;
    }

    private static void resetValues()
    {
        counter = 0;
        c = 0;
        previousC = 0;
    }
    
    private static void bisectionMethod(double a, double b, double root, boolean isFunctionA)
    {
        double aValue, bValue, cValue;

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
        
        if (aValue * bValue >= 0 || (aValue * bValue == Double.NaN))
        {
            System.out.println("No roots found within " + a + " and " + b + " .\n");
            return;
        }

        c = a;

        do
        {
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

            previousC = c;
            c = (a + b) / 2;

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

            counter++;

            System.out.println("Iteration: " + counter);

            System.out.printf(" x = %4.4f with relative error: %-4.2f with true error: %-6.2f",
                    c, (approximateError(c, previousC) * 100), (realError(c, root) * 100));
            
            System.out.println("\n");

        } while ((approximateError(c, previousC) >= error && counter < MAX_ITERATIONS));

        System.out.print("As of " + counter + " iterations, ");
        System.out.printf("x = %.4f", c);
        System.out.println("\n------------------");
        resetValues();
    }

    private static void newtonRaphsonMethod(double a, double root, boolean isFunctionA) 
    {
        double value, valuePrime;
        
        boolean done = false;

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
            if (isFunctionA)
            {
                value = functionA(a);
                valuePrime = functionAPrime(a);
            }

            else
            {
                value = functionB(a);
                valuePrime = functionBPrime(a);

                if (value == Double.NaN || valuePrime == Double.NaN) 
                {
                    return;
                }
            }
            
            c = a - (value / valuePrime);

            double approximation = Math.abs(approximateError(c, a) * 100);
            double trueError = Math.abs(realError(c, root) * 100);
            counter++;

            System.out.println("Iteration: " + counter);

            System.out.printf(" x = %4.4f with relative error: %-4.2f with true error: %-6.2f",
                    c, approximation, trueError);
            
            System.out.println("\n");

            a = c;

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

    private static void secantMethod(double a, double b, double root, boolean isFunctionA) 
    {
        double aValue, bValue;
        
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

            c = b - ((bValue) * (b - a)) / (bValue - aValue);

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

    private static void falsePositionMethod() 
    {

    }
}
