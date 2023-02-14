package planner;
import java.util.*;


class Node 
{
	static int lastId = 0;
	final int id = lastId++;
	
	Node parent;
	Operator operator;
	Bind bind;
	List<Predicate> goal;
	List<Predicate> state;
	
	
	//initialize
	Node()
	{
		
	}
	
	
	//initialize
	Node(List<Predicate> state, List<Predicate> goal)
	{
		this.state = state;
		this.goal = goal;
		this.bind = new Bind();
	}
	
	
	// toString
	public String toString()
	{
		return String.format("(%d) %s", this.id, this.operator.name);
	}
	
	
	// if parent == null -> depth = 0 
	int depth()
	{
		return this.parent == null ? 0 : this.parent.depth() + 1;
	}
	
	
	// 
	List<Operator> toPlan()
	{
		List<Operator> plan = new ArrayList<>();
		Node node = this;
		while (node != null && node.operator != null)
		{
			var operator = this.bind.instantiate(node.operator);
			plan.add(operator);
			node = node.parent;
		}
		
		Collections.reverse(plan);
		return plan;
	}
}
