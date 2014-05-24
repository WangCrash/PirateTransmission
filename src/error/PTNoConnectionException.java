package error;

@SuppressWarnings("serial")
public class PTNoConnectionException extends PTException {
	public PTNoConnectionException() {
		super(ErrorDescription.NO_INTERNET_CONNECTION);
	}
}
