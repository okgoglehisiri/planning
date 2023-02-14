package planner;

import static planner.Utils.*;
import static planner.Matcher.*;

public class EX3 {
	public static void main(String args[]) {
		Problem p = new BlockWorldProblem();
		new ForwardPlanner().solve(p);
	}
}
