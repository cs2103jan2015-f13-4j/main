//@A0111935L
package parser;

import java.util.Map;

import utility.CommandType;
import utility.KeywordType;

/**
 * This class allows the command in a line to break into keys and fields.
 * It will returned a command type and also the referenced list of keyfields
 */
public class CommandStringParser {
	
	private static final String RESTRICT_EQUAL_SIGN = "=";
	private static final String RESTRICT_VERTICAL_BAR = "|";
	private static final String A_SPACE = " ";
	private static final String EMPTY_STRING = "";
	/**
	 * This processString will process the commands entered by the user
	 * @param input command in string
	 * @param keyFieldsList a list of keys and fields and will be referenced back to the caller
	 * @return Command Type
	 */
	public static CommandType.Command_Types processString(String input, Map<String, String> keyFieldsList){
		if(keyFieldsList == null){
			assert false : "Map object is null";
		}
		
		if(input == null || input.equals(EMPTY_STRING)){
			return CommandType.Command_Types.INVALID;
		}
		
		if(input.contains(RESTRICT_EQUAL_SIGN) || input.contains(RESTRICT_VERTICAL_BAR)){
			return CommandType.Command_Types.INVALID;
		}
		
		String[] inputCmd = input.trim().split(A_SPACE);
		
		CommandType.Command_Types command = CommandType.getType(inputCmd);
		if(command == CommandType.Command_Types.INVALID){
			return CommandType.Command_Types.INVALID;
		}
		
		generateKeyFieldPair(keyFieldsList, inputCmd, command);
		
		return command;
	}

	/**
	 * This method generate the command which has broken up into words and process it.
	 * The key and fields will be referenced back to the caller
	 * @param keyFieldsList a list of key and fields
	 * @param inputCmd the blocks of word
	 */
	private static void generateKeyFieldPair(Map<String, String> keyFieldsList,
			String[] inputCmd, CommandType.Command_Types command) {
		String key = command.name();
		String eachWord = new String();
		String tempFields = new String();
		
		for(int i = 1; i < inputCmd.length; i++){
			eachWord = inputCmd[i];
			if(KeywordType.contains(eachWord)){
				keyFieldsList.put(key.toUpperCase(), tempFields.trim());
				key = eachWord;
				tempFields = "";
			}
			else{
				tempFields += eachWord + A_SPACE;
			}
		}
		
		keyFieldsList.put(key.toUpperCase(), tempFields.trim());
	}
}
