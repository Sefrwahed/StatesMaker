

/**
 * Implements the JK flip flop
 * 
 * @author A.Abd el megid
 */
public class JKFlipFlop extends FlipFlop
{
	/**
	 * calculates the value of the next state based on the current state and the inpt wires
	 * @return the value of the next state
	 */
	public boolean getNextState()
	{
		// let j be the first input, k the second
		boolean j;
		try
		{
			j = getInputWires().get(0).getValue();
		} catch (Exception e)
		{
			System.out.print("Error, J of te flip flop failed to calculate its value. Please check its connections");
			j = false;
		}
		
		boolean k;
		try
		{
			k = getInputWires().get(1).getValue();
		} catch (Exception e)
		{
			System.out.print("Error, K of te flip flop failed to calculate its value. Please check its connections");
			k = false;
		}
		
		if (!j && !k)
			return getCurrentState();
		else if (j && !k)
			return true;
		else if (!j && k)
			return false;
		else
			return !getCurrentState();
	}
}
