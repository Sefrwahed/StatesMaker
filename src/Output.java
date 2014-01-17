

/**
 * @author A.Aly
 * The output gate
 */
public class Output extends Gate
{
	// Constructor
	public Output(String name)
	{
		super();
		this.name = name;
	}
	
	// Methods


	public boolean getValue()
	{
		try
		{
			Wire inputWire = this.getInputWires().get(0);
			return inputWire.getValue();
		} catch (Exception e)
		{
			System.out.print("Error, Output " + this.name + " failed to calculate its value, please check its connections\n");
			return false;
		}
	}
}
