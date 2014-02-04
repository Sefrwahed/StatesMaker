import java.util.ArrayList;
import java.util.Collections;

/**
 * Helper functions useful for calculating the output of the circuit
 * 
 * @author A.Aly
 */
public class CircuitHelpers
{
	    private static void fillCombinations(ArrayList<Boolean> current, ArrayList<ArrayList<Boolean>> result, int n)
		{
			// base case
			if (current.size() == n)
			{
				result.add(current);
			}
			else
			{
				// add a zero
				ArrayList<Boolean> addZero= new ArrayList<>(current);
				addZero.add(false);
				fillCombinations(addZero, result, n);
				
				// add a one
				ArrayList<Boolean> addOne = new ArrayList<>(current);
				addOne.add(true);
				fillCombinations(addOne, result, n);
							
			}
		}
		
	    /**
		  * Returns all the binary combinations of length n
		  * @param n the length of the binary combination(number)
		  * @return Arraylist of arraylists of boolean, each one representing a combination
		  */
		 public static ArrayList<ArrayList<Boolean>>  binaryCombination(int n)
		{
			ArrayList<ArrayList<Boolean>> result = new ArrayList<>();
			fillCombinations(new ArrayList<Boolean>(), result, n);
			return result;
		}


		 /**
		  * Assigns the value of flipflop[i] to be combination[i]
		  * @param flipflops Arraylist of flipflops whose currentState will be assigned
		  * @param combination ArrayList of boolean to be the flipflops currentState 
		  */
		 public static void mapFlipFlopsCurrentStates(
				ArrayList<FlipFlop> flipflops, ArrayList<Boolean> combination)
		{
			for (int i = 0; i < combination.size(); i++)
			{
					flipflops.get(i).setCurrentState(combination.get(i));
			}
		}

		 /**
		  * Converts the binary number formed by the flipflops currentState to decimal
		  * @param fliplflops ArrayList of the circuit's flipflops
		  * @return an integer which equals the binary combination of the flipflops currentStates
		  */
		public static int  currentStatesToDecimal(ArrayList<FlipFlop> fliplflops)
		{
			int result = 0;
			
			// Add the values of each state, the most significant is in index 0
			Boolean state;
			for (int i = 0	; i < fliplflops.size(); i++)
			{
				state = fliplflops.get(0).getCurrentState();
				if (state == true)
					result += Math.pow(2,  fliplflops.size() - 1 - i);
			}
			
			return result;
		}
		
		/**
		  * Converts the binary number formed by the flipflops nextStates to decimal
		  * @param fliplflops ArrayList of the circuit's flipflops
		  * @return an integer which equals the binary combination of the flipflops nextStates
		 * @throws Exception 
		  */		
		public static int  nextStatesToDecimal(ArrayList<FlipFlop> fliplflops) 
		{
			int result = 0;
			
			// Add the values of each state, the most significant is in index 0
			Boolean state;
			for (int i = 0	; i < fliplflops.size(); i++)
			{
				state = fliplflops.get(i).getNextState();
				if (state == true)
					result += Math.pow(2,  fliplflops.size() - 1 - i);
			}
			
			return result;
		}
		

		
		/**
		 * @param boolArr list of 0s and 1s representing a binary number with the least significant at the end
		 * @return the decimal equivalent of the binary nubmer
		 */
		public static int binaryToDecimal(ArrayList<Boolean> boolArr)
		{
			int result = 0;
			for (int i = 0; i < boolArr.size(); i++)
			{
				if(boolArr.get(i))
					result += Math.pow(2,  boolArr.size() - i - 1);
			}
			return result;
		}
		
		/**
		 * Converts a single binary number (true or false) to (1 or 0)
		 * @param bool a single binary number
		 * @return 1 or 0
		 */
		public static int boolToInteger(Boolean bool)
		{
			if(bool)
				return 1;
			else
				return 0;
		}
	
		/**
		 * Assign the values of the inputs to a given combination
		 * @param inputs the input whose values willbe assigned
		 * @param combination an ArrayList of boolean values which will be assigned to theinputs
		 */
		public static void mapInputValues(ArrayList<Input> inputs,
				ArrayList<Boolean> combination)
		{
			for (int i = 0; i < combination.size(); i++)
			{
					inputs.get(i).setValue(combination.get(i));
			}
			
		}

		
		/**
		 * @param integer The integer to be converted
		 * @param n the number of bits of the output binary
		 * @return Array of binary number where arr(0) is the most significant
		 */
		public static ArrayList<Boolean> decimalToBinary(Integer integer, int n)
		{
			ArrayList<Boolean> result = new ArrayList<>();
			if (integer == 0)
			{
				result.add(false);
				for(int i = 0 ; i < n - 1; i++)
					result.add(false);
				Collections.reverse(result);
				return result;
			}
			while(integer  != 0)
			{
				if(integer % 2 != 0)
					result.add(true);
				else
					result.add(false);
				integer = integer / 2;
			}
			
			// Fill the rest of the bits
			for(int i = 0 ; i <( n - result.size()); i++)
				result.add(false);
			Collections.reverse(result);
			return result;
		}
}
