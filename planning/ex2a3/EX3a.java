package planner;

import static planner.Utils.*;
import static planner.Matcher.*;

public class EX3a {
	public static void main(String args[]) {
		Problem p = new MyTCBWProblem();
		new ForwardPlanner().solve(p);
	}
}
