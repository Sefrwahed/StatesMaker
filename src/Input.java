
/**
 * @author A.Aly
 *
 */
public class Input extends Gate
{
	// Variables
	private boolean value;

	// Setters and getters
	public boolean getValue()
	{
		return value;
	}
	public void setValue(boolean value)
	{
		this.value = value;
	}
	
	// Constructors
	public Input(String name)
	{
		super();
		this.name = name;
	}

}
