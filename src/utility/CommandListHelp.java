
public class CommandListHelp {
	
		public enum CommandListHelp_Type {
			ADD, DELETE, DISPLAY, CLEAR, EXIT, UPDATE, INVALID, SEARCH, SORT
		}

		public static CommandListHelp_Type getType(String[] commandListHelp_TypeString) {

			if (commandListHelp_TypeString == null) {
				return CommandListHelp_Type.INVALID;
			}
			
			switch (commandListHelp_TypeString[0].toLowerCase()) {
			case "add": 
				return CommandListHelp_Type.ADD;
				
			case "delete": 
				return CommandListHelp_Type.DELETE;
				
			case "display": 
				return CommandListHelp_Type.DISPLAY;
				
			case "update": 
				return CommandListHelp_Type.UPDATE;
				
			case "clear": 
				return CommandListHelp_Type.CLEAR;
				
			case "exit": 
				return CommandListHelp_Type.EXIT;
			
			case "search": 
				return CommandListHelp_Type.SEARCH;
			
			case "sort": 
				return CommandListHelp_Type.SORT;
			
			default: 
				return CommandListHelp_Type.INVALID;
			}
		}

	}

