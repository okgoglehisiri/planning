package planner;
import static java.util.stream.Collectors.*;
import static planner.Utils.*;
import java.util.*;

public class Operator {
    
    static int lastID = 0;
    Predicate name;
    List<Predicate> ifList;
    List<Predicate> addList;
    List<Predicate> delList;
    

    // initialize List<String>
    public Operator(String name, List<String> ifList, List<String> addList, List<String> delList)
    {
        this.name = new Predicate(name);
        this.ifList = list(ifList);
        this.addList = list(addList);
        this.delList = list(delList);
    }
    

    // initialize List<Predicate>
    public Operator(Predicate name, List<Predicate> ifList, List<Predicate> addList, List<Predicate> delList)
    {
        this.name = name.clone();
        this.ifList = new ArrayList<>(ifList);
        this.addList = new ArrayList<>(addList);
        this.delList = new ArrayList<>(delList);
    }
    
    
    // toString
    public String toString()
    {
        return "NAME: " + this.name + "\n" + 
            "IF: " + this.ifList + "\n" +
            "ADD: " + this.addList + "\n" + 
            "DEL: " + this.delList;
    }

    
    // flow to the rename method
    public Operator renamed()
    {
        Map<String, String> renamedVars = new HashMap<>();
        long id = ++Operator.lastID;
        return new Operator(rename(this.name, renamedVars, id),
                        rename(this.ifList, renamedVars, id),
                        rename(this.addList, renamedVars, id),
                        rename(this.delList, renamedVars, id));
    }

    
    // rename List<Predicate>
    public List<Predicate> rename(List<Predicate> preds, Map<String, String> renamedVars, long index)
    {
        return preds.stream()
            .map(p -> rename(p, renamedVars, index))
            .collect(toList());
    }

    
    // rename Predicate
    Predicate rename(Predicate p, Map<String, String> renamedVars, long index)
    {
        var renamedTerms = p.terms.stream()
            .map(term -> rename(term, renamedVars, index))
            .collect(toList());
        return new Predicate(renamedTerms);
    }

     
    // rename String
    String rename(String term, Map<String, String> renamedVars,long index)
    {
        if (Predicate.isVar(term) == false) return term;

        var newName = renamedVars.get(term);
        if (newName == null)
        {
            newName = String.format("%s_%d", term, index);
            renamedVars.put(term, newName);
        }
        return newName;
    }

    
    // applyForward 
    public List<Predicate> appplyForward(List<Predicate> state)
    {
        state = subtract(state, this.delList);
        state = concat(state, this.addList);
        // distinct -> deleting duplicates
        return state.stream().distinct().collect(toList());
    }

    
    // applyBackward
    public List<Predicate> applyBackward(List<Predicate> goal)
    {
        goal = subtract(goal, this.addList);
        goal = concat(goal, this.ifList);
        // distinct -> deleting duplicates
        return goal.stream().distinct().collect(toList());
    }
}