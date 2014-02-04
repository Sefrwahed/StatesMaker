import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The class that parses the inputs and stores it as strings to be used to build
 * the circuit
 * 
 * @author M.Anwer
 */
public class TextInputReader
{
	private String full_input;
	private String ff_type;
	private int ff_no;
	private int input_no;
	private int output_no;
	private int internals_no;
	private ArrayList<String> inputs = new ArrayList<>();
	private ArrayList<String> outputs = new ArrayList<>();
	private ArrayList<String> internals = new ArrayList<>();
	private ArrayList<ArrayList<String>> design = new ArrayList<>();

	/**
	 * @return All the file
	 */
	public String getFull_input()
	{
		return full_input;
	}

	/**
	 * @return the type of the flip flop
	 */
	public String getFF_type()
	{
		return ff_type;
	}

	/**
	 * @return the number of flipflops
	 */
	public int getFF_no()
	{
		return ff_no;
	}

	/**
	 * @return The number of inputs
	 */
	public int getInput_no()
	{
		return input_no;
	}

	/**
	 * @return The number of outputs
	 */
	public int getOutput_no()
	{
		return output_no;
	}

	/**
	 * @return The number of internals
	 */
	public int getInternals_no()
	{
		return internals_no;
	}

	/**
	 * @return a list of the inputs
	 */
	public ArrayList<String> getInputs()
	{
		return inputs;
	}

	/**
	 * @return a list of the outputs
	 */
	public ArrayList<String> getOutputs()
	{
		return outputs;
	}

	/**
	 * @return a list of the wires(internals)
	 */
	public ArrayList<String> getInternals()
	{
		return internals;
	}

	/**
	 * @return a list containing the connections of the gates
	 */
	public ArrayList<ArrayList<String>> getDesign()
	{
		return design;
	}

	/**
	 * @param path
	 *            the location of the input txt file
	 */
	public TextInputReader(String path)
	{
		try
		{
			this.full_input = readFile(path);
		} catch (IOException e)
		{
			System.out.println("ERROR FILE NOT FOUND");
		}

		analyse(this.full_input);
	}

	/**
	 * @param file
	 *            The location of the inputs txt file
	 * @return Reads the input file and returns a string just like the original
	 *         txt file
	 * @throws IOException
	 */
	private String readFile(String file) throws IOException
	{
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		while ((line = reader.readLine()) != null)
		{
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}

		return stringBuilder.toString();
	}

	private ArrayList<String> designTrimmer(String chip, String ele)
	{
		ArrayList<String> inv = new ArrayList<>();
		inv.add(chip);
		String ios = new String();
		ios = ele.substring(ele.indexOf('(') + 1, ele.indexOf(')'));
		while (true)
		{
			if (ios.indexOf(",") != -1)
			{
				inv.add(ios.substring(0, ios.indexOf(",")));
				ios = ios.substring(ios.indexOf(",") + 1);
			} else
			{
				inv.add(ios);
				// System.out.println(inv);
				return inv;
			}
		}
	}

	private void analyse(String fullInput)
	{
		this.ff_type = (fullInput.substring(fullInput.indexOf("FF_TYPE:") + 8,
				fullInput.indexOf("FF_NO:") - 1)).trim();
		this.ff_no = Integer.parseInt(((fullInput.substring(
				fullInput.indexOf("FF_NO:") + 6,
				fullInput.indexOf("INPUT_NO:") - 1)).trim()));
		this.input_no = Integer.parseInt(((fullInput.substring(
				fullInput.indexOf("INPUT_NO:") + 9,
				fullInput.indexOf("OUTPUT_NO:") - 1)).trim()));
		this.output_no = Integer.parseInt(((fullInput.substring(
				fullInput.indexOf("OUTPUT_NO:") + 10,
				fullInput.indexOf("INTERNAL_NO:") - 1)).trim()));
		this.internals_no = Integer.parseInt(((fullInput.substring(
				fullInput.indexOf("INTERNAL_NO:") + 12,
				fullInput.indexOf("INPUTS:") - 1)).trim()));
		String all_inputs = new String((fullInput.substring(
				fullInput.indexOf("INPUTS:") + 7,
				fullInput.indexOf("OUTPUTS:") - 1)).trim());
		String all_outputs = new String((fullInput.substring(
				fullInput.indexOf("OUTPUTS:") + 8,
				fullInput.indexOf("INTERNALS:") - 1)).trim());
		String all_internals = new String((fullInput.substring(
				fullInput.indexOf("INTERNALS:") + 10,
				fullInput.indexOf("DESIGN:") - 1)).trim());
		String all_design = new String((fullInput.substring(fullInput
				.indexOf("DESIGN:") + 7)).replaceAll(" ", ""));

		all_design = all_design.replaceAll("GATE:", "");
		all_design = all_design.replaceAll("FF:", "");
		// System.out.println(all_design);

		while (true)
		{
			if (all_inputs.indexOf(",") != -1)
			{
				this.inputs.add(all_inputs
						.substring(0, all_inputs.indexOf(",")).trim());
				all_inputs = all_inputs.substring(all_inputs.indexOf(",") + 1);
			} else
			{
				this.inputs.add(all_inputs.trim());
				// System.out.println(this.inputs);
				break;
			}
		}

		while (true)
		{
			if (all_outputs.indexOf(",") != -1)
			{
				this.outputs.add(all_outputs.substring(0,
						all_outputs.indexOf(",")).trim());
				all_outputs = all_outputs
						.substring(all_outputs.indexOf(",") + 1);
			} else
			{
				this.outputs.add(all_outputs.trim());
				// System.out.println(this.outputs);
				break;
			}
		}

		while (true)
		{
			if (all_internals.indexOf(",") != -1)
			{
				this.internals.add(all_internals.substring(0,
						all_internals.indexOf(",")).trim());
				all_internals = all_internals.substring(all_internals
						.indexOf(",") + 1);
			} else
			{
				this.internals.add(all_internals.trim());
				// System.out.println(this.internals);
				break;
			}
		}

		String[] array = all_design.split("\n");

		for (String ele : array)
		{
			if (ele.startsWith("INVERTER"))
			{
				this.design.add(designTrimmer("INVERTER", ele));
			} else if (ele.startsWith("JK"))
			{
				this.design.add(designTrimmer("JK", ele));
			} else if (ele.startsWith("D"))
			{
				this.design.add(designTrimmer("D", ele));
			} else if (ele.startsWith("SR"))
			{
				this.design.add(designTrimmer("SR", ele));
			} else if (ele.startsWith("T"))
			{
				this.design.add(designTrimmer("T", ele));
			} else if (ele.startsWith("AND"))
			{
				this.design.add(designTrimmer("AND", ele));
			} else if (ele.startsWith("OR"))
			{
				this.design.add(designTrimmer("OR", ele));
			}

		}

		// System.out.println(this.design);
	}

}