package planner;

import static planner.Utils.*;
import static planner.Matcher.*;

public class EX5 {
	public static void main(String args[]) {
		Problem p = new MonkeyBananaProblem();
		new BackwardPlanner().solve(p);
	}
}
