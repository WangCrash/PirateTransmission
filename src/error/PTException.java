package error;

@SuppressWarnings("serial")
public class PTException extends Exception {		
	private int code;
	
	public PTException(int code){
		this.code = code;
	}
	
	public int getCode(){
		return this.code;
	}
	
	@Override
	public String getMessage(){
		return new ErrorDescription().descriptionByCode(this.code);
	}
}
