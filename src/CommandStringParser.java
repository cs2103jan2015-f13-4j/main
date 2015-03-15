import java.util.ArrayList;

/**
 * This class allows the command in a line to break into keys and fields.
 * It will returned a command type and also the referenced list of keyfields
 * @author A0111935L
 *
 */
public class CommandStringParser {
	
	/**
	 * This processString will process the commands entered by the user
	 * @param input command in string
	 * @param keyFieldsList a list of keys and fields and will be referenced back to the caller
	 * @return Command Type
	 */
	public static CommandType.Command_Types processString(String input, ArrayList<KeyFieldPair> keyFieldsList){
		if(input == null || input.equals("")){
			return CommandType.Command_Types.INVALID;
		}
		
		String[] inputCmd = input.trim().split(" ");
		
		CommandType.Command_Types command = CommandType.getType(inputCmd);
		if(command == CommandType.Command_Types.INVALID){
			return CommandType.Command_Types.INVALID;
		}
		
		generateKeykeyFieldPair(keyFieldsList, inputCmd);
		
		return command;
	}

	/**
	 * This method generate the command which has broken up into words and process it.
	 * The key and fields will be referened back to the caller
	 * @param keyFieldsList a list of key and fields
	 * @param inputCmd the blocks of word
	 */
	private static void generateKeykeyFieldPair(ArrayList<KeyFieldPair> keyFieldsList,
			String[] inputCmd) {
		String key = inputCmd[0];
		String eachWord = new String();
		String tempFields = new String();
		
		for(int i = 1; i < inputCmd.length; i++){
			eachWord = inputCmd[i];
			if(KeywordType.contains(eachWord)){
				keyFieldsList.add(new KeyFieldPair(key, tempFields.trim()));
				key = eachWord;
				tempFields = "";
			}
			else{
				tempFields += eachWord + " ";
			}
		}
		
		keyFieldsList.add(new KeyFieldPair(key, tempFields.trim()));
	}
}
