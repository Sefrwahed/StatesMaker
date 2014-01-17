/**
 * @author A.Alfy
 * Implements the OR gate
 */
public class OrGate extends Gate
{
	// Methods
	/**
	 * returns the value on the input wires ORed
	 */
	public boolean getValue()
	{
		return getInputWires().get(0).getValue() || getInputWires().get(1).getValue() ;
	}
	
	
}
