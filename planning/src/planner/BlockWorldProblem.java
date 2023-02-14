package planner;
import static planner.Utils.*;
import java.util.*;

public class BlockWorldProblem implements Problem{

	public List<Predicate> initialState(){
		return list(
				"B on A",
				"C on B",
				"clear C",
				"ontable A",
				"handEmpty");
	}
	
	public List<Predicate> goalState()
	{
		return list(
				"B on C",
				"A on B");
	}
	
	public List<Operator> operators()
	{
		return List.of(
				// OPERATOR 1
				new Operator("#1: place ?x on ?y",
						_if("clear ?y", "holding ?x"),
						add("?x on ?y", "clear ?x", "handEmpty"),
						del("clear ?y", "holding ?x")),
				
				// OPERATOR 2
				new Operator("#2: remove ?x from on top ?y",
						_if("?x on ?y", "clear ?x", "handEmpty"),
						add("clear ?y", "holding ?x"),
						del("?x on ?y", "clear ?x", "handEmpty")),
				
				// OPERATOR 3
				new Operator("#3: pick up ?x from the table",
						_if("ontable ?x", "clear ?x", "handEmpty"),
						add("holding ?x"),
						del("ontable ?x", "clear ?x", "handEmpty")),
				
				new Operator("#4: put down ?x on the table",
						_if("holding ?x"),
						add("ontable ?x", "clear ?x", "handEmpty"),
						del("holding ?x"))
				);
				
	}
}
