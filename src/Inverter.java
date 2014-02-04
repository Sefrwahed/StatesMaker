

/**
 * implements a NOT gate
 * 
 * @author A.Alfy
 */
public class Inverter extends Gate
{
	// Methods
	/**
	 * returns the value on the input wire inverted
	 */
	public boolean getValue()
	{
		return !getInputWires().get(0).getValue();
	}
}
