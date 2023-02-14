package planner;
import java.util.*;


public interface Problem {
	public List<Predicate> initialState();
	public List<Predicate> goalState();
	public List<Operator> operators();

}
