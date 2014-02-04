/**
 * TFlipFlop Class
 * 
 * @author A.abd el megiid
 */
public class TFlipFlop extends FlipFlop
{
	/**
	 * calculates the value of the next state based on the current state and the
	 * inpt wires
	 * 
	 * @return the value of the next state
	 */
	public boolean getNextState()
	{
		boolean T;
		try
		{
			T = getInputWires().get(0).getValue();
		} catch (Exception e)
		{
			System.out
					.print("Error, T of the flip flop failed to calculate its value. Please check its connections");
			T = false;
		}
		if (T == false)
			return getCurrentState();
		return !getCurrentState();

	}
}
