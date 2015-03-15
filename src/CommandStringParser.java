import java.util.ArrayList;


public class CommandStringParser {
	
	
	public static CommandType.Command_Types processString(String input, ArrayList<KeyFieldPair> keyFields){
		if(input == null || input.equals("")){
			return CommandType.Command_Types.INVALID;
		}
		
		String[] inputCmd = input.trim().split(" ");
		
		CommandType.Command_Types command = CommandType.getType(inputCmd);
		if(command == CommandType.Command_Types.INVALID){
			return CommandType.Command_Types.INVALID;
		}
		
		generateKeykeyFieldPair(keyFields, inputCmd);
		
		return command;
	}

	private static void generateKeykeyFieldPair(ArrayList<KeyFieldPair> keyFields,
			String[] inputCmd) {
		String key = inputCmd[0];
		String tempFields = new String();
		
		for(String eachWord : inputCmd){
			if(KeywordType.contains(eachWord)){
				keyFields.add(new KeyFieldPair(key, tempFields.trim()));
				key = eachWord;
				tempFields = "";
			}
			else{
				tempFields += eachWord + " ";
			}
		}
		
		keyFields.add(new KeyFieldPair(key, tempFields.trim()));
	}
}
