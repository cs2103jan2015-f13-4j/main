import java.util.ArrayList;
import java.util.Date;


public class UpdateHandler {

	public static String executeUpdate(String[] commandInput, ArrayList<Task> listTask){
		
		if(commandInput.length == 1){
			return String.format(MessageList.MESSAGE_INVALID_ARGUMENT, "Delete");
		}
		
		//dummy
		return "";
	}
}
