/**
 * Implements the flip flop
 * 
 * @author A.Aly
 */
public class FlipFlop extends Gate
{
	// Variables
	private boolean currentState;

	// Setters and getters
	/**
	 * @return the current info stored at the flip flop this function is for the
	 *         FlipFlop only not in the Gate class
	 */
	public boolean getCurrentState()
	{
		return currentState;
	}

	public void setCurrentState(boolean currentState)
	{
		this.currentState = currentState;
	}

	// Constructors
	public FlipFlop()
	{
		currentState = false;
	}

	// Methods
	/**
	 * calculates the value of the next state based on the current state and the
	 * input wires
	 * 
	 * @return the value of the next state
	 * @throws Exception
	 */
	public boolean getNextState()
	{
		return false;
	}

	/**
	 * returns the value of the current state
	 */
	public boolean getValue()
	{
		return currentState;
	}

}
