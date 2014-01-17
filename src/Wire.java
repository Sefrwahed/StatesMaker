/**
 * @author A.Aly
 * Implements an internal of the circuit which connects gates, outputs, inputs, flipflops
 */
public class Wire
{
	// Variables
	private Gate sourceGate;                     // The wire takes it value from the source gate
	private Gate destinationGate;          // the destination gate gets its value from the wire
	public String name;
	
	// Setters and getters
	public Gate getSourceGate()
	{
		return sourceGate;
	}
	public void setSourceGate(Gate sourceGate)
	{
		this.sourceGate = sourceGate;
	}
	public Gate getDestinationGate()
	{
		return destinationGate;
	}
	public void setDestinationGate(Gate destinationGate)
	{
		this.destinationGate = destinationGate;
	}
	public boolean getValue()
	{
	
		try
		{
			return this.sourceGate.getValue();
		} catch (Exception e)
		{
			System.out.print("Error, Wire " + this.name + " failed to calculate its value. Please check its connections\n");
			return false;
		}
	}
	
	// Constructors
	public Wire()
	{
		this.sourceGate = null;
		this.destinationGate = null;
		this.name = "";
	}
	public Wire(String name)
	{
		this.sourceGate = null;
		this.destinationGate = null;
		this.name = name;
	}

	
	
	}
	

