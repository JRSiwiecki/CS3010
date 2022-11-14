
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
    
    public static void main(String[] args)
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
    
    private static void bisectionMethod()
    {
        
    }

    private static void newtonRaphsonMethod() 
    {

    }

    private static void secantMethod() 
    {

    }

    private static void falsePositionMethod() 
    {

    }
}
