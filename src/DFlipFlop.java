
/**
 * Implement the D flip flop
 * 
 * @author A.Abd el megiid
 */
public class DFlipFlop extends FlipFlop
{
	// Methods
	/**
	 * calculates the value of the next state based on the current state and the input wires
	 * @return the value of the next state
	 */
	public boolean getNextState()
	{
		boolean D;
		try
		{
			D = getInputWires().get(0).getValue();
		} catch (Exception e)
		{
			System.out.print("Error, D of the flip flop failed to calculate its value. Please check its connections");
			D = false;
		}
		if (D== false) return false ;
		else return true ;
	}
	
}
