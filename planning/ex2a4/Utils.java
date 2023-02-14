package planner;
import static java.util.stream.Collectors.*;
import java.util.*;

public class Utils {
    static int count = 0;

    // initialize (argment -> List)
    public static List<Predicate> list(List<String> texts)
    {
        return texts.stream().map(Predicate::new).collect(toList());
    }

    
    // initialize (argment -> String...)
    // ... variable length parameter
    public static List<Predicate> list(String... texts)
    {
        return list(Arrays.asList(texts));
    }
    
    
    // Return True when does not exist in the list (p.isGround)
    public static boolean isGround(List<Predicate> preds)
    {
        return preds.stream().filter(p -> !p.isGround()).findFirst().isEmpty();
    }
    
    
    // Return ZS <- XS + YS
    public static <T> List<T> concat(List<T> xs, List<T> ys)
    {
        var zs = new ArrayList<T>(xs);
        zs.addAll(ys);
        return zs;
    }
    

    // Return ZS <- XS - YS
    public static <T> List<T> subtract(List<T> xs, List<T> ys)
    {
        var zs = new ArrayList<T>(xs);
        zs.removeAll(ys);
        return zs;
    }

    
    // Conversion String... -> ArrayList
    public static List<String> _if(String... args)
    {
        return Arrays.asList(args);
    }

    
    // Conversion String... -> ArrayList
    public static List<String> add(String... args)
    {
        return Arrays.asList(args);
    }

    
    // Conversion String... -> ArrayList
    public static List<String> del(String... args)
    {
        return Arrays.asList(args);
    }

    
    //  Output ToString
    public static void println(Object o) {
        System.out.printf("%d| %s\n", ++count, o.toString());
    }

}