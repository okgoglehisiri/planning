package planner;

import static planner.Utils.*;

public class EX1
{
    public static void main(String[] args)
    {
        var p1 = new Predicate("C on B");
        var p2 = new Predicate("clear C");
        var q1 = new Predicate("?x on ?y");
        var q2 = new Predicate("clear ?x");
        var q3 = new Predicate("ontable ?x");
        var b  = new Bind();

        // unification
        var b1 = b.unified(p1, q1);
        println(p1 + " == " + q1 + " |--> " + b1);
        
        var b2 = b1.unified(p2, q2);
        println(p1 + " == " + q2 + " |--> " + b2);

        var b3 = b.unified(p1, q3);
        println(p1 + " == " + q3 + " |--> " + b3);


        // instantiation
        println(q1 + " --> " + b2.instantiate(q1));
        println(q2 + " --> " + b2.instantiate(q2));
    }
}