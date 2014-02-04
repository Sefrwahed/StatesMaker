

/**
 * The output gate
 * 
 * @author A.Aly
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
			MainFrame.showError("Error, Output " + this.name + " failed to calculate its value, please check its connections\n");
			return false;
		}
	}
}
