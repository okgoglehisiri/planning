package planner;
import static java.util.stream.Collectors.*;
import java.util.*;

public class Bind implements Cloneable
{
    
    private static Bind UNSATISFIED = new Bind(null);

    Map<String, String> vars = new TreeMap<String, String>();
    
    
    // initialize
    public Bind()
    {

    }

    
    // initialize (argment -> Map)
    private Bind(Map<String, String> vars)
    {
        this.vars = vars;
    }

    
    // Judge maps is null
    public boolean isSatisfied()
    {
        return this.vars != null;
    }

    
    // Make Bind clone
    public Bind clone()
    {
        var b = new Bind();
        b.vars.putAll(this.vars);
        return b;
    }

    
    // Map convert to hashcode
    public int hashCode()
    {
        return Objects.hash(this.vars);
    }

    
    // Judge this and obj are equivalent
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if ((obj instanceof Bind) == false) return false;
        Bind other = (Bind) obj;
        return Objects.equals(this.vars, other.vars);
    }

    
    // ToString return String
    public String toString()
    {
        if (this.isSatisfied()) return this.vars.toString();
        return "failed";
    }

    
    // Return this + other
    public Bind merged(Bind other)
    {
        var b = new Bind();
        b.vars.putAll(this.vars);
        b.vars.putAll(other.vars);
        return b.resolved();
    }

    
    // unify(String, String, Bind)
    public boolean bind(String lhs, String rhs)
    {
        return unify(lhs, rhs, this);
    }

    
    // 
    public Bind unified(Predicate lhs, Predicate rhs)
    {
        if (lhs.size() != rhs.size()) return UNSATISFIED;
        lhs = instantiate(lhs);
        rhs = instantiate(rhs);

        // Return Bind (Clone)　IF lhs = rhs
        if (lhs.equals(rhs)) return clone();

        var b = clone();
        
        // not unify -> return UNSATISFIED
        for (int i = 0; i < lhs.size(); i++)
        {
            if (unify(lhs.terms.get(i), rhs.terms.get(i), b) == false) return UNSATISFIED;
        }

        // unify -> return resolved()
        return b.resolved();
    }

    
    // 
    static boolean unify(String lhs, String rhs, Bind b)
    {
        lhs = b.instantiate(lhs);
        rhs = b.instantiate(rhs);

        if (lhs.equals(rhs)) return true;

        var isVar1 = Predicate.isVar(lhs);
        var isVar2 = Predicate.isVar(rhs);

        if (isVar1 == false && isVar2 == false) return lhs.equals(rhs);

        if (isVar1 == false && isVar2 || isVar1 && isVar2 && lhs.compareTo(rhs) < 0)
            b.vars.put(rhs, lhs);
        else
            b.vars.put(lhs, rhs);

        return true;
    }

    
    // new Operator is Value with o as tye key
    public Operator instantiate(Operator o)
    {
        return new Operator(instantiate(o.name),
            instantiate(o.ifList),
            instantiate(o.addList),
            instantiate(o.delList));
    }

    
    // terms(ArrayList) -> stream ->map-> Apply instantiate(String) -> ArrayList
    // Return List
    public List<Predicate> instantiate(List<Predicate> preds)
    {
        return preds.stream().map(this::instantiate).collect(toList());
    }

    
    // terms(ArrayList) -> stream ->map-> Apply instantiate(String) -> ArrayList
    // Return Predicate(ArrayList)
    public Predicate instantiate(Predicate p)
    {
        var instantiatedTerms = p.terms.stream()
            .map(this::instantiate)
            .collect(toList());
            return new Predicate(instantiatedTerms);
    }
    
    
    // isVar (term -> ?x) -> True
    // Get a value with Term as a key from map
    // Return term (value)
    String instantiate(String term)
    {
        while (Predicate.isVar(term))
        {
            var value = this.vars.get(term);
            if (value == null) break;
            term = value;
        }
        return term;
    }

    
    // keyset() -> get all the keys of the map
    // term = keys(i) 
    // b.bind(key, instantiate(key))
    Bind resolved()
    {
        var b = new Bind();
        this.vars.keySet().forEach(term -> b.bind(term, instantiate(term)));
        return b;
    }
}