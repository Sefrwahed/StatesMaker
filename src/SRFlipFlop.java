/**
 * SR FlipFlop Class
 * 
 * @author A.abd el megiid
 */
public class SRFlipFlop extends FlipFlop
{
	/**
	 * calculates the value of the next state based on the current state and the
	 * inpt wires
	 * 
	 * @return the value of the next state
	 */
	public boolean getNextState()
	{
		boolean S;
		try
		{
			S = getInputWires().get(0).getValue();
		} catch (Exception e)
		{
			System.out
					.println("Error, S of the flip flop failed to calculate its value. Please check its connections");
			S = false;
		}

		boolean R;
		try
		{
			R = getInputWires().get(1).getValue();
		} catch (Exception e)
		{
			System.out
					.print("Error, R of the flip flop failed to calculate its value. Please check its connections");
			R = false;
		}

		if (S == false && R == false)
			return getCurrentState();
		if (S == true && R == false)
			return true;
		if (S == false && R == true)
			return false;
		else
		{
			System.out
					.print("Error, S and R are both 1. It's a forbidden state... \"Another glitch in the matrix\"");
			return false;
		}
	}
}
