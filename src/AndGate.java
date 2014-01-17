


/**
 * @author  A.Alfy
 * The AND gate
 */
public class AndGate extends Gate
{
	
	// Methods
	/**
	 * returns the value on the input wires anded
	 */
	public boolean getValue()
	{
		return getInputWires().get(0).getValue() && getInputWires().get(1).getValue() ;
	}
}
