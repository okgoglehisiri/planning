package planner;
import static planner.Utils.*;
import java.util.*;

public class MonkeyBananaProblem implements Problem{
	public List<Predicate> initialState(){
		return list(
				"Monkey at A",
				"Monkey in Low",
				"Banana at B",
				"Banana in high",
				"Box at C");
	}
	
	public List<Predicate> goalState()
	{
		return list(
				"Monkey has Banana"
				);
	}
	
	public List<Operator> operators()
	{
		return List.of(
				// OPERATOR 1
				new Operator("#1: go to ?y for ?b",
						_if("Monkey at ?x", "Monkey in Low", "?b at ?y"),
						add("Monkey at ?y"),
						del("Monkey at ?x")),
				
				// OPERATOR 2
				new Operator("#2: push Box to ?y",
						_if("Monkey at ?x", "Monkey in Low", "Box at ?x", "?b at ?y"),
						add("Monkey at ?y", "Box at ?y"),
						del("Monkey at ?x", "Box at ?x")),
				
				// OPERATOR 3
				new Operator("#3: climb up Box",
						_if("Monkey at ?x", "Box at ?x", "Monkey in Low"),
						add("Monkey in High"),
						del("Monkey in Low")),
				
				new Operator("#4: grasp Banana",
						_if("Monkey in High", "Monkey at ?x", "Banana at ?x","Banana in high"),
						add("Monkey has Banana"),
						del())
				);
				
	}
}
