import java.util.ArrayList;


public class InputStringParser {
	
	
	public static CommandType.Command_Types processString(String input, ArrayList<KeyParamPair> keyParam){
		if(input == null || input.equals("")){
			return CommandType.Command_Types.INVALID;
		}
		
		String[] inputCmd = input.trim().split(" ");
		
		CommandType.Command_Types command = CommandType.getType(inputCmd);
		if(command == CommandType.Command_Types.INVALID){
			return CommandType.Command_Types.INVALID;
		}
		
		generateKeyParamPair(keyParam, inputCmd);
		
		return command;
	}

	private static void generateKeyParamPair(ArrayList<KeyParamPair> keyParam,
			String[] inputCmd) {
		String key = inputCmd[0];
		String eachWord = new String();
		String tempParam = new String();
		
		for(int i = 1; i < inputCmd.length; i++){
			eachWord = inputCmd[i];
			if(KeywordType.contains(eachWord)){
				keyParam.add(new KeyParamPair(key, tempParam.trim()));
				key = eachWord;
				tempParam = "";
			}
			else{
				tempParam += eachWord + " ";
			}
		}
		
		keyParam.add(new KeyParamPair(key, tempParam.trim()));
	}
}
