
import java.util.ArrayList;

/**
 * The super class for and, not, or gates , flip flops, inputs and outputs
 * 
 * @author A.Aly
 */
public class Gate
{
	// Variables
	private int numberOfInputs;
	private int numbeOfOutputs;
	private ArrayList<Wire> inputWires;
	private ArrayList<Wire> outputWires;
	public String name;
	
	// Setters and getters
	public int getNumberOfInputs()
	{
		return numberOfInputs;
	}
	public void setNumberOfInputs(int numberOfInputs)
	{
		this.numberOfInputs = numberOfInputs;
	}
	public int getNumbeOfOutputs()
	{
		return numbeOfOutputs;
	}
	public void setNumbeOfOutputs(int numbeOfOutputs)
	{
		this.numbeOfOutputs = numbeOfOutputs;
	}
	public ArrayList<Wire> getInputWires()
	{
		return inputWires;
	}
	public void setInputWires(ArrayList<Wire> inputWires)
	{
		this.inputWires = inputWires;
	}
	public ArrayList<Wire> getOutputWires()
	{
		return outputWires;
	}
	public void setOutputWires(ArrayList<Wire> outputWires)
	{
		this.outputWires = outputWires;
	}
	
	// Constructors
	public Gate()
	{
		this.numberOfInputs = 0;
		this.numbeOfOutputs = 0;
		this.inputWires = new ArrayList<>();
		this.outputWires = new ArrayList<>();
	}
	public Gate(String name)
	{
		this.numberOfInputs = 0;
		this.numbeOfOutputs = 0;
		this.inputWires = new ArrayList<>();
		this.outputWires = new ArrayList<>();
		this.name = name;
	}
	// Methods
	/**
	 * Adds a wire to the input wires of the gate
	 * @param wire the wire which will be connected to the gate
	 */
	public void connectInput(Wire wire)
	{
		wire.setDestinationGate(this);
		this.inputWires.add(wire);
		this.numberOfInputs ++;
	}
	/**
	 * adds a wire to the output wires of the gate
	 * @param wire the wire which will be connected to the gate
	 */
	public void connectOutput(Wire wire)
	{
		wire.setSourceGate(this);
		this.outputWires.add(wire);
		this.numbeOfOutputs++;
	}
	
	/**
	 * Returns the value of the output of the gate based on the input wires
	 * @return the output of the gate
	 */
   public boolean getValue()
	{
		return true;
	}
	
}
