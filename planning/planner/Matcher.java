package planner;
import static planner.Utils.*;
import java.util.*;

public class Matcher {
    
    static List<Bind> satisfy(List<Predicate> ps, List<Predicate> qs)
    {
        var unifiers = new LinkedHashSet<Bind>();
        match(false, 0, 0, ps, qs, new Bind(), unifiers);
        return new ArrayList<Bind>(unifiers);
    }

    static List<Bind> satisfyPartially(List<Predicate> ps, List<Predicate> qs)
    {
        var unifiers = new LinkedHashSet<Bind>();
        match(true, 0, 0, ps, qs, new Bind(), unifiers);
        return new ArrayList<Bind>(unifiers);
    }

    static void match(boolean allowPartial, int i, int count, List<Predicate> ps, List<Predicate> qs, Bind b, Collection<Bind> unifiers)
    {
        if (i >= ps.size())
        {
            if (isGround(ps) && count > 0) unifiers.add(b);
            
            else if (count > 0 && b.vars.size() > 0) unifiers.add(b);
        } else
        {
            var pf = b.instantiate(ps.get(i));
            for (var pg: qs)
            {
                var b1 = b.unified(pf, pg);
                if (b1.isSatisfied() == false) continue;
                match(allowPartial, i + 1, count + 1, ps, qs, b1, unifiers);
            }

            if (allowPartial) match(allowPartial, i + 1, count, ps, qs, b, unifiers);
        }
    }
}
