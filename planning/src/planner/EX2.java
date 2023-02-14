package planner;
import static planner.Utils.*;
import static planner.Matcher.*;

public class EX2 {
    public static void main(String[] args)
    {
        var state = list(
            "B on A",
            "C on B",
            "clear C",
            "ontable A",
            "handEmpty");
        
        println(state);

        var o = new Operator("#2: remove ?x from on top ?y", _if("?x on ?y", "clear ?x", "handEmpty"), add("clear ?y", "holding ?x"), del("?x on ?y", "clear ?x", "handEmpty"));
        println(o);

        o = o.renamed();
        println(o);


        var unifiers1 = satisfy(o.ifList, state);
        println(unifiers1);

        for (var b: unifiers1)
        {
            var o1 = b.instantiate(o);
            println(o1.name);
            println(o1.appplyForward(state));
        }

        var unifiers2 = satisfy(o.addList, state);
        println(unifiers2);

        var unifiers3 = satisfyPartially(o.addList, state);
        println(unifiers3);
    }
}

