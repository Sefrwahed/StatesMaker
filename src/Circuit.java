import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The circuit which contains the flipflops, internals, inputs, outputs and internals
 * 
 * @author A.Aly
 */
public class Circuit
{
	// Variables
	ArrayList<Input> inputs;
	ArrayList<Output> outputs;
	ArrayList<FlipFlop> flipflops;
	ArrayList<Gate> gates;
	HashMap<String, Input> stringToInput;
	HashMap<String, Output> stringToOutput;
	HashMap<String, Wire> stringToWire;

	
	// Constructor
	public Circuit()
	{
		inputs = new ArrayList<>();
		outputs= new ArrayList<>();
		flipflops = new ArrayList<>();
		gates = new ArrayList<>();
		stringToInput = new HashMap<>();
		stringToOutput = new HashMap<>();
		stringToWire = new HashMap<>();
	}
	
	//  Methods
	/**
	 * Creates a sample circuit 
	 */
	public void createRandomCircuit()
	{
		// Create the wires
		Wire wireA = new Wire("A");
		Wire wireB = new Wire("B");
		Wire wireC = new Wire("C");
		Wire wireD = new Wire("D");
		Wire wireE = new Wire("E");

		// Create the inputs and outputs
		Input input1 = new Input("input 1");
		Input input2 = new Input("input 2");
		Output output1 = new Output("Output 1");
		inputs.add(input1);
		inputs.add(input2);
		outputs.add(output1);
		
		// Create the flipflops
		JKFlipFlop flipflop = new JKFlipFlop();
		flipflops.add(flipflop);
		
		// Create the or gate
		OrGate or = new OrGate();
		
		// Connect the wires
		input1.connectOutput(wireA);
		or.connectInput(wireA);
			
		or.connectOutput(wireC);
		flipflop.connectInput(wireC);
		
		input2.connectOutput(wireB);
		flipflop.connectInput(wireB);
		
		flipflop.connectOutput(wireD);
		output1.connectInput(wireD);
		
		or.connectInput(wireE);
		output1.connectOutput(wireE);
		
		
	}

	/**
	 * Tries all the possible combination of flipflops states and inputs to get the state diagram
	 * @throws Exception 
	 */
	public String generateAllOutputs() throws Exception
	{
		// The first lines about the number of states and their names
		String result = "STATES_NO:" +(int)Math.pow(2, flipflops.size()) + "\n";
		result += "STATES: ";
		for (int i = 0; i < Math.pow(2, flipflops.size()); i++)
			result += "S" + i + " ";
		result += "\n";
		result += "STATE_DIAGRAM:\n";
		
		// Try all the possible states
		ArrayList<ArrayList<Boolean> >  allStates = CircuitHelpers.binaryCombination(flipflops.size());
		for (int state = 0; state < allStates.size(); state++)
		{
			result += "S" + state + ":\n";
			
			// assign the flip flops states to this configuration
			CircuitHelpers.mapFlipFlopsCurrentStates(flipflops, allStates.get(state));
			
			// Generate and try all the possible combinations of the input
			ArrayList<ArrayList<Boolean> > allInputsCombinations = CircuitHelpers.binaryCombination(inputs.size());
			for(int inputCombination = 0; inputCombination < allInputsCombinations.size(); inputCombination++)
			{
				// Assign the input values to the current combination
				CircuitHelpers.mapInputValues(inputs, allInputsCombinations.get(inputCombination));
				for (Input input : inputs)
					result += input.name + "=" + CircuitHelpers.boolToInteger(input.getValue()) + " ";
				
				// Find the next state
				int nextState = CircuitHelpers.nextStatesToDecimal(flipflops);
				result += "-> S" + nextState;
				
				// Find the outputs
				for (Output output : outputs)
					result += "," +  output.name + "=" + CircuitHelpers.boolToInteger(output.getValue()) + " ";
				result += "\n";
			}
		}
		return result;
	}

	/**
	 * @return the state diagram
	 * Begins with the initial state of all flipflops = 0 then tries all inputs combinations 
	 * and do the same with the state reached from the initial state
	 */
	public String generateSimplifiedOutput()
	{
		ArrayList<String> resultArr = new ArrayList<String>();
		HashMap<Integer, Boolean> explored = new HashMap<>();
		
		// Start a DFS from the state where all the flip flops are zeroes and mark it as explored
		ArrayList<Boolean> initialState = new ArrayList<Boolean>();
		ArrayList<String> stateNames = new ArrayList<>();
		for (int i = 0; i < flipflops.size();i++)
			initialState.add(false);
		DFS(initialState, explored, resultArr, stateNames);
		
		
		// Form the String
		String result  = "STATES_NO:" + stateNames.size() + "\nStates:";
		Collections.sort(stateNames);
		for (String string : stateNames)
			result += string + " ";
		result += "\nSTATE_DIAGRAM: \n";
		
		// Sort the strings we got from the DFS
		Collections.sort(resultArr);
		for (int i = 0; i < resultArr.size(); i++)
		{
			result += resultArr.get(i) + "\n";
		}
		return result;
	}
	
	
	/**
	 * @param initialState list of binary numbers representing flipflops current state
	 * @param explored hash map containing the states already explored
	 * @param resultArr list of the strings composing the state diagram description
	 * @param stateNames list of names of states discovered so far
	 */
	private void DFS(ArrayList<Boolean> initialState,
			HashMap<Integer, Boolean> explored, ArrayList<String> resultArr,
			ArrayList<String> stateNames)
	{
		int currentStateNumber = CircuitHelpers.binaryToDecimal(initialState);
		// Check if the state has already been explored or not
		if (explored.containsKey(currentStateNumber))
			return;
		else
		{
			String result = "S" + currentStateNumber + ":\n";
			stateNames.add("S" + currentStateNumber);
			ArrayList<Integer> nextStates = new ArrayList<Integer>();
			
			// Mark the state as explored
			explored.put(currentStateNumber, true);
			
			// assign the flip flops states to this configuration
			CircuitHelpers.mapFlipFlopsCurrentStates(flipflops,initialState);
			
			// Try all the inputs and DFS the next States they bring
			ArrayList<ArrayList<Boolean> > allInputsCombinations = CircuitHelpers.binaryCombination(inputs.size());
			for(int inputCombination = 0; inputCombination < allInputsCombinations.size(); inputCombination++)
			{
				// Assign the input values to the current combination
				CircuitHelpers.mapInputValues(inputs, allInputsCombinations.get(inputCombination));
				for (Input input : inputs)
					result += input.name + "=" + CircuitHelpers.boolToInteger(input.getValue()) + " ";
				
				// Find the next state
				int nextState = CircuitHelpers.nextStatesToDecimal(flipflops);
				result += "-> S" + nextState ;
				if(! nextStates.contains(nextState))
					nextStates.add(nextState);
				
				// Find the outputs
				for (Output output : outputs)
					result +="," +  output.name + "=" + CircuitHelpers.boolToInteger(output.getValue()) + " ";
				
				if(inputCombination != allInputsCombinations.size()-1)
					result += "\n";
			}	
			
			// Add This part to the result
			resultArr.add(result);
			
			
			// DFS all the reachable states
			for (int i = 0; i < nextStates.size(); i++)
			{
				ArrayList<Boolean> nextStateCombination = CircuitHelpers.decimalToBinary(nextStates.get(i), flipflops.size());
				DFS(nextStateCombination, explored, resultArr, stateNames);
			}
		}
		
	}

	
	/**
	 * @param reader TextInputReader initialized with the path of the input file containing the circuit description
	 * Connects the components of the Circuit object
	 */
	public void createCicuit(TextInputReader reader)
	{
		// Create the inputs
		for (int i = 0; i < reader.getInput_no(); i++)
		{
			Input input = new Input(reader.getInputs().get(i));
			stringToInput.put(reader.getInputs().get(i), input);
			inputs.add(input);
		}
		
		// Create the outputs
		for (int i = 0; i < reader.getOutput_no(); i++)
		{
			Output output = new Output(reader.getOutputs().get(i));
			stringToOutput.put(reader.getOutputs().get(i), output);
			outputs.add(output);
		}
		
		// Create the wires
		for (int i = 0; i < reader.getInternals_no(); i++)
		{
			Wire wire = new Wire(reader.getInternals().get(i));
			stringToWire.put(reader.getInternals().get(i), wire);
		}

		// Create the connections
		for (ArrayList<String> connection : reader.getDesign())
		{
			makeConnection(connection);
		}
		
//    	flipflops.get(0).setCurrentState(false);
//		flipflops.get(1).setCurrentState(false);
//		inputs.get(0).setValue(true);
//		MainFrame.showError("J 1" + flipflops.get(1).getNextState());

	}
	
	private void makeConnection(ArrayList<String> connection)
	{
		// Check the type of gate and call the appropriate function
		if (connection.get(0).equals("INVERTER"))
			createInverter(connection.get(1), connection.get(2));
		else if (connection.get(0).equals("AND"))
			createAnd(connection.get(1), connection.get(2),connection.get(3));
		else if (connection.get(0).equals("OR"))
			createOr(connection.get(1), connection.get(2),connection.get(3));
		else if (connection.get(0).equals("JK"))
			createJK(connection.get(1), connection.get(2),connection.get(3));
		else if (connection.get(0).equals("SR"))
			createSR(connection.get(1), connection.get(2),connection.get(3));
		else if (connection.get(0).equals("T"))
			createT(connection.get(1), connection.get(2));
		else if (connection.get(0).equals("D"))
			createD(connection.get(1), connection.get(2));
	}
	
	private void createJK(String in1, String in2, String out)
	{
		JKFlipFlop jk = new JKFlipFlop();
		
		// Connect the first input
		// Check if the in wire is an input
		if(stringToInput.containsKey(in1))
		{
			Wire inWire = new Wire();
			stringToInput.get(in1).connectOutput(inWire);
			jk.connectInput(inWire);
		}
		// Check if the in wire is an output
		else if (stringToOutput.containsKey(in1))
		{
			Wire inWire = new Wire();
			stringToOutput.get(in1).connectOutput(inWire);
			jk.connectInput(inWire);
		}
		// Check if the in wire is just a wire already known to the circuit
		else if (stringToWire.containsKey(in1))
			jk.connectInput(stringToWire.get(in1));
		else
			MainFrame.showError("Error, the wire " + in1 + " isn't in the circuit");
	
		// Connect the Second input
		// Check if the in2 wire is an input
		if(stringToInput.containsKey(in2))
		{
			Wire inWire = new Wire();
			stringToInput.get(in2).connectOutput(inWire);
			jk.connectInput(inWire);
		}
		// Check if the in wire is an output
		else if (stringToOutput.containsKey(in2))
		{
			Wire inWire = new Wire();
			stringToOutput.get(in2).connectOutput(inWire);
			jk.connectInput(inWire);
		}
		// Check if the in wire is just a wire already known to the circuit
		else if (stringToWire.containsKey(in2))
			jk.connectInput(stringToWire.get(in2));
		else
			MainFrame.showError("Error, the wire " + in2 + " isn't in the circuit");
		
		// Connect the output
		// Check if the in wire is an output
		if (stringToOutput.containsKey(out))
		{
			Wire outWire = new Wire();
			stringToOutput.get(out).connectInput(outWire);
			jk.connectOutput(outWire);
		}
		// Check if the in wire is just a wire already known to the circuit
		else if (stringToWire.containsKey(out))
			jk.connectOutput(stringToWire.get(out));
		else
			MainFrame.showError("Error, the wire " + out + " isn't in the circuit");
	
		flipflops.add(jk);
	}
	private void createSR(String in1, String in2, String out)
	{
		SRFlipFlop sr = new SRFlipFlop();
		
		// Connect the first input
		// Check if the in wire is an input
		if(stringToInput.containsKey(in1))
		{
			Wire inWire = new Wire();
			stringToInput.get(in1).connectOutput(inWire);
			sr.connectInput(inWire);
		}
		// Check if the in wire is an output
		else if (stringToOutput.containsKey(in1))
		{
			Wire inWire = new Wire();
			stringToOutput.get(in1).connectOutput(inWire);
			sr.connectInput(inWire);
		}
		// Check if the in wire is just a wire already known to the circuit
		else if (stringToWire.containsKey(in1))
			sr.connectInput(stringToWire.get(in1));
		else
			MainFrame.showError("Error, the wire " + in1 + " isn't in the circuit");
	
		// Connect the Second input
		// Check if the in2 wire is an input
		if(stringToInput.containsKey(in2))
		{
			Wire inWire = new Wire();
			stringToInput.get(in2).connectOutput(inWire);
			sr.connectInput(inWire);
		}
		// Check if the in wire is an output
		else if (stringToOutput.containsKey(in2))
		{
			Wire inWire = new Wire();
			stringToOutput.get(in2).connectOutput(inWire);
			sr.connectInput(inWire);
		}
		// Check if the in wire is just a wire already known to the circuit
		else if (stringToWire.containsKey(in2))
			sr.connectInput(stringToWire.get(in2));
		else
			MainFrame.showError("Error, the wire " + in2 + " isn't in the circuit");
		
		// Connect the output
		// Check if the in wire is an output
		if (stringToOutput.containsKey(out))
		{
			Wire outWire = new Wire();
			stringToOutput.get(out).connectInput(outWire);
			sr.connectOutput(outWire);
		}
		// Check if the in wire is just a wire already known to the circuit
		else if (stringToWire.containsKey(out))
			sr.connectOutput(stringToWire.get(out));
		else
			MainFrame.showError("Error, the wire " + out + " isn't in the circuit");
	
		flipflops.add(sr);
	}
	private void createD(String in1, String out)
	{
		DFlipFlop d= new DFlipFlop();
		
		// Connect the first input
		// Check if the in wire is an input
		if(stringToInput.containsKey(in1))
		{
			Wire inWire = new Wire();
			stringToInput.get(in1).connectOutput(inWire);
			d.connectInput(inWire);
		}
		// Check if the in wire is an output
		else if (stringToOutput.containsKey(in1))
		{
			Wire inWire = new Wire();
			stringToOutput.get(in1).connectOutput(inWire);
			d.connectInput(inWire);
		}
		// Check if the in wire is just a wire already known to the circuit
		else if (stringToWire.containsKey(in1))
			d.connectInput(stringToWire.get(in1));
		else
			MainFrame.showError("Error, the wire " + in1 + " isn't in the circuit");
	
		
		// Connect the output
		// Check if the in wire is an output
		if (stringToOutput.containsKey(out))
		{
			Wire outWire = new Wire();
			stringToOutput.get(out).connectInput(outWire);
			d.connectOutput(outWire);
		}
		// Check if the in wire is just a wire already known to the circuit
		else if (stringToWire.containsKey(out))
			d.connectOutput(stringToWire.get(out));
		else
			MainFrame.showError("Error, the wire " + out + " isn't in the circuit");
	
		flipflops.add(d);
	}
	private void createT(String in1, String out)
	{
		TFlipFlop t = new TFlipFlop();
		
		// Connect the first input
		// Check if the in wire is an input
		if(stringToInput.containsKey(in1))
		{
			Wire inWire = new Wire();
			stringToInput.get(in1).connectOutput(inWire);
			t.connectInput(inWire);
		}
		// Check if the in wire is an output
		else if (stringToOutput.containsKey(in1))
		{
			Wire inWire = new Wire();
			stringToOutput.get(in1).connectOutput(inWire);
			t.connectInput(inWire);
		}
		// Check if the in wire is just a wire already known to the circuit
		else if (stringToWire.containsKey(in1))
			t.connectInput(stringToWire.get(in1));
		else
			MainFrame.showError("Error, the wire " + in1 + " isn't in the circuit");
	
		
		// Connect the output
		// Check if the in wire is an output
		if (stringToOutput.containsKey(out))
		{
			Wire outWire = new Wire();
			stringToOutput.get(out).connectInput(outWire);
			t.connectOutput(outWire);
		}
		// Check if the in wire is just a wire already known to the circuit
		else if (stringToWire.containsKey(out))
			t.connectOutput(stringToWire.get(out));
		else
			MainFrame.showError("Error, the wire " + out + " isn't in the circuit");
	
		flipflops.add(t);
	}

	private void createOr(String in1, String in2, String out)
	{
		OrGate or = new OrGate();
		
		// Connect the first input
		// Check if the in wire is an input
		if(stringToInput.containsKey(in1))
		{
			Wire inWire = new Wire();
			stringToInput.get(in1).connectOutput(inWire);
			or.connectInput(inWire);
		}
		// Check if the in wire is an output
		else if (stringToOutput.containsKey(in1))
		{
			Wire inWire = new Wire();
			stringToOutput.get(in1).connectOutput(inWire);
			or.connectInput(inWire);
		}
		// Check if the in wire is just a wire already known to the circuit
		else if (stringToWire.containsKey(in1))
			or.connectInput(stringToWire.get(in1));
		else
			MainFrame.showError("Error, the wire " + in1 + " isn't in the circuit");
	
		// Connect the Second input
		// Check if the in2 wire is an input
		if(stringToInput.containsKey(in2))
		{
			Wire inWire = new Wire();
			stringToInput.get(in2).connectOutput(inWire);
			or.connectInput(inWire);
		}
		// Check if the in wire is an output
		else if (stringToOutput.containsKey(in2))
		{
			Wire inWire = new Wire();
			stringToOutput.get(in2).connectOutput(inWire);
			or.connectInput(inWire);
		}
		// Check if the in wire is just a wire already known to the circuit
		else if (stringToWire.containsKey(in2))
			or.connectInput(stringToWire.get(in2));
		else
			MainFrame.showError("Error, the wire " + in2 + " isn't in the circuit");
		
		// Connect the output
		// Check if the in wire is an output
		if (stringToOutput.containsKey(out))
		{
			Wire outWire = new Wire();
			stringToOutput.get(out).connectInput(outWire);
			or.connectOutput(outWire);
		}
		// Check if the in wire is just a wire already known to the circuit
		else if (stringToWire.containsKey(out))
			or.connectOutput(stringToWire.get(out));
		else
			MainFrame.showError("Error, the wire " + out + " isn't in the circuit");
				
		
		gates.add(or);
	}

	private void createInverter(String in, String out)
	{
		Inverter inverter= new Inverter();
		
		// Connect the input
		// Check if the in wire is an input
		if(stringToInput.containsKey(in))
		{
			Wire inWire = new Wire();
			stringToInput.get(in).connectOutput(inWire);
			inverter.connectInput(inWire);
		}
		// Check if the in wire is an output
		else if (stringToOutput.containsKey(in))
		{
			Wire inWire = new Wire();
			stringToOutput.get(in).connectOutput(inWire);
			inverter.connectInput(inWire);
		}
		// Check if the in wire is just a wire already known to the circuit
		else if (stringToWire.containsKey(in))
			inverter.connectInput(stringToWire.get(in));
		else
			MainFrame.showError("Error, the wire " + in + " isn't in the circuit");
		
		// Connect the output
		// Check if the in wire is an output
		if (stringToOutput.containsKey(out))
		{
			Wire outWire = new Wire();
			stringToOutput.get(out).connectInput(outWire);
			inverter.connectOutput(outWire);
		}
		// Check if the in wire is just a wire already known to the circuit
		else if (stringToWire.containsKey(out))
			inverter.connectOutput(stringToWire.get(out));
		else
			MainFrame.showError("Error, the wire " + out + " isn't in the circuit");
		
		gates.add(inverter);
	}

	  private void createAnd(String in1, String in2, String out)
		{
			AndGate and = new AndGate();
			
			// Connect the first input
			// Check if the in wire is an input
			if(stringToInput.containsKey(in1))
			{
				Wire inWire = new Wire();
				stringToInput.get(in1).connectOutput(inWire);
				and.connectInput(inWire);
			}
			// Check if the in wire is an output
			else if (stringToOutput.containsKey(in1))
			{
				Wire inWire = new Wire();
				stringToOutput.get(in1).connectOutput(inWire);
				and.connectInput(inWire);
			}
			// Check if the in wire is just a wire already known to the circuit
			else if (stringToWire.containsKey(in1))
				and.connectInput(stringToWire.get(in1));
			else
				MainFrame.showError("Error, the wire " + in1 + " isn't in the circuit");
		
			// Connect the Second input
			// Check if the in2 wire is an input
			if(stringToInput.containsKey(in2))
			{
				Wire inWire = new Wire();
				stringToInput.get(in2).connectOutput(inWire);
				and.connectInput(inWire);
			}
			// Check if the in wire is an output
			else if (stringToOutput.containsKey(in2))
			{
				Wire inWire = new Wire();
				stringToOutput.get(in2).connectOutput(inWire);
				and.connectInput(inWire);
			}
			// Check if the in wire is just a wire already known to the circuit
			else if (stringToWire.containsKey(in2))
				and.connectInput(stringToWire.get(in2));
			else
				MainFrame.showError("Error, the wire " + in2 + " isn't in the circuit");
			
			// Connect the output
			// Check if the in wire is an output
			if (stringToOutput.containsKey(out))
			{
				Wire outWire = new Wire();
				stringToOutput.get(out).connectInput(outWire);
				and.connectOutput(outWire);
			}
			// Check if the in wire is just a wire already known to the circuit
			else if (stringToWire.containsKey(out))
				and.connectOutput(stringToWire.get(out));
			else
				MainFrame.showError("Error, the wire " + out + " isn't in the circuit");
					
			gates.add(and);
		}
	  
	  public void drawCircuit(String output, String output_file) {
		  GraphViz gv = new GraphViz();
	      gv.addln(gv.start_graph());
	      gv.addln("rankdir=LR;");
	      String[] lines = output.split("\n");
	      int max_lines = lines.length;
	      Matcher matcher = Pattern.compile("STATES_NO:([0-9]+)").matcher(lines[0]);
	      matcher.find();
	      String num_states = matcher.group(1);
	      gv.addln("size=\"" + num_states + "\";");
	      gv.addln("node [shape = circle];");
	      matcher = Pattern.compile("(.+):").matcher(lines[3]);
    	  matcher.find();
	      for (int i = 3; i < max_lines; ) {
	    	  String beginning = matcher.group(1);
	    	  i++;
	    	  while (i < max_lines) {
	    		  matcher = Pattern.compile("(.+):").matcher(lines[i]);
		    	  if (matcher.find())
		    		  break;
		    	  String[] splitted_line = lines[i].split("->");
		    	  // splitted_line[0] has inputs, splitted_line[1] has outputs and next state.
		    	  String label = "";
		    	  Pattern p = Pattern.compile("([^=]+)=(0|1)");
		    	  matcher = p.matcher(splitted_line[0]);
		    	  while (matcher.find()) {
		    		  label += matcher.group(2);
		    	  }
		    	  matcher = p.matcher(splitted_line[1]);
		    	  if (matcher.find()) {
		    		  label += "/";
		    		  label += matcher.group(2);
		    		  while (matcher.find()) {
		    			  label += matcher.group(2);
		    		  }
		    	  }
		    	  String state_movement = beginning + " -> ";
		    	  matcher = Pattern.compile("(S[0-9]+)").matcher(splitted_line[1]);
		    	  matcher.find();
		    	  state_movement += matcher.group(1);
		    	  gv.addln(state_movement + " [ label = \"" + label + "\" ]");
		    	  i++;
	    	  }
	      }
	      gv.addln(gv.end_graph());
	      File out = new File(output_file);
	      String dotSource = gv.getDotSource();
	      byte[] image = gv.getGraph(dotSource, "pdf");
	      gv.writeGraphToFile( image, out );
	  }
}
