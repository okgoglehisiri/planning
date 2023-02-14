package planner;
import static planner.Utils.*;
import static planner.Matcher.*;
import java.util.*;

public class ForwardPlanner extends Planner{
	int visitingNode = 0;
	
	public int getVisitingNode() {
		return visitingNode;
	}
	// solve
	public List<Operator> solve(Problem problem)
	{
		this.operators = problem.operators();
		this.init = problem.initialState();
		this.goal = problem.goalState();
		Node root = new Node(this.init, this.goal);
		
		System.out.println("***** start forward search*****");
		System.out.println("init: " + this.init);
		System.out.println("goal: " + this.goal);
		
		Node goal = plan(root);
		if (goal == null)
		{
			System.out.println("**** failed ****");
			return null;
		}
		
		System.out.println("***** This is a plan! *****");
		var plan = goal.toPlan();
		for (var action: plan) System.out.println(action.name);
		System.out.println("FinalVisitingNodeNumber:"+visitingNode);
		return plan;
		
	}
	
	
	// planning
	Node plan(Node root)
	{
		final int maxDepthLimit = 10;
		int depthLimit = 4;
		while (depthLimit < maxDepthLimit)
		{
			System.out.println("============ " + depthLimit);
			Node goal = search(root, depthLimit);
			if (goal != null) return goal;
			depthLimit += 1;
		}
		return null;
	}
	
	
	// search
	Node search(Node root, int depthLimit)
	{
		List<Node> openList = new ArrayList<>();
		openList.add(root);
		
		while (openList.size() > 0)
		{
			Node s = openList.remove(0);
			System.out.println("-------------------");
			System.out.printf("visit (%d) %s\n", s.id, s.state);
			visitingNode += 1;
			if (isGoal(s)) return s;
			if (s.depth() < depthLimit)
			{
				System.out.println("->");
				var children = expand(s);
				openList = concat(openList, children);
			}
		}
		return null;
	}
	
	
	
	boolean isGoal(Node node)
	{
		var unifiers = satisfy(this.goal, node.state);
		if (unifiers == null) return false;
		for (var b : unifiers)
		{
			var g = b.instantiate(this.goal);
			if (isGround(g)) return true;
		}
		return false;
	}
	
	List<Node> expand(Node node)
	{
		var children = new ArrayList<Node>();
		for (Operator op : this.operators)
		{
			op = op.renamed();
			var unifiers = satisfy(op.ifList, node.state);
			expand(node, op, unifiers, children);
		}
		return children;
	}

	void expand(Node node, Operator operator, List<Bind> unifiers, List<Node> children)
	{
		for (Bind b : unifiers)
		{
			Node child = new Node();
			b = node.bind.merged(b);
			child.bind = b;
			child.operator = b.instantiate(operator);
			child.state = child.operator.appplyForward(b.instantiate(node.state));
			child.parent = node;
			children.add(child);
			System.out.println(child);
		}
	}
}
