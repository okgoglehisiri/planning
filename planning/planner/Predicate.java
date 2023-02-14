package planner;
import static java.util.stream.Collectors.*;
import java.util.*;

public class Predicate implements Cloneable
{
    List<String> terms = new ArrayList<>();

    // initialize
    // Split a sentence into words
    // ex)_Ontable ?x -> <Ontable,?x>
    public Predicate(String text)
    {
        var terms = text.split(" ");
        this.terms = Arrays.asList(terms);
    }

    // initialize
    // Already split
    public Predicate(List<String> terms)
    {
        this.terms.addAll(terms);
    }

    // Make a copy of the Predicate Instance
    public Predicate clone()
    {
        return new Predicate(this.terms);
    }

    // Return a hash value
    // Mainly used to take equivalence

    public int hashCode()
    {
        return Objects.hash(this.terms);
    }

    // If this and obj are Identical or Equivalence, returns True
    // If obj type is not Predicate, returns False
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if ((obj instanceof Predicate) == false) return false;
        Predicate other = (Predicate) obj;
        return Objects.equals(this.terms, other.terms);
    }

    // String Conjunction, and Insert " " in between
    public String toString()
    {
        return this.terms.stream().collect(joining(" "));
    }

    // Return ArrayList Length
    public int size()
    {
        return this.terms.size();
    }

    // If term begins with "?", Returns True
    public static boolean isVar(String term)
    {
        return term.startsWith("?");
    }

    // It returns True when ↓
    // `stream().filter(t->isVar(t))` is Number of `t` satisfying `isVar`
    // `.findFirst().isEmpty()` means that True is returned when there is nothing to satisfy
    public boolean isGround()
    {
        return this.terms.stream().filter(t -> isVar(t)).findFirst().isEmpty();
    }
}
