
/**
 * @author Joseph Siwiecki
 *         Assignment: Programming Project 2
 *         Class: CS 3010.01
 *         Date: 11/19/22
 */
public class Project3_jsiwiecki 
{
    private static final int MAX_ITERATIONS = 100;
    private static double delta = 0.01;
    private static double error = 0.01;
    private static int counter = 0;
    private static double c = 0.0;
    private static double previousC = 0.0;
    private static boolean functionA = true;
    private static boolean functionB = false;
    
    public static void main(String[] args)
    {
        bisectionTests();
        newtonRaphsonTests();
        secantTests();
        falsePositionTests();
    }

    private static void bisectionTests()
    {
        bisectionMethod(0.0, 1.0, 0.356316, functionA);
        bisectionMethod(1.0, 2.0, 1.92174, functionA);
        bisectionMethod(2.0, 3.0, 3.56316, functionA);
        bisectionMethod(3.0, 4.0, 3.56316, functionA);

        bisectionMethod(0.0, 1.0, 0.356316, functionB);
        bisectionMethod(1.0, 2.0, 1.92174, functionB);
        bisectionMethod(2.0, 3.0, 3.56316, functionB);
        bisectionMethod(3.0, 4.0, 3.56316, functionB);
    }

    private static void newtonRaphsonTests() 
    {

    }
    
    private static void secantTests() 
    {

    }
    
    private static void falsePositionTests() 
    {

    }
    
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
        
        System.out.println("\n--- Bisection Method with inputs " + a + " and " + b + " ---\n");

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
        
        if (aValue * bValue >= 0)
        {
            System.out.println("No roots found within " + a + " and " + b + " .\n");
            return;
        }

        c = a;

        do
        {
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
        System.out.println("\n--- Newton-Raphson Method with input " + a + " ---\n");

        
    }

    private static void secantMethod() 
    {

    }

    private static void falsePositionMethod() 
    {

    }
}
