package Utils;

public abstract class OneArgumentRunnableObject implements Runnable {
	private Object argument;

	public OneArgumentRunnableObject(Object argument){
		this.setArgument(argument);
	}
	
	public Object getArgument() {
		return argument;
	}

	public void setArgument(Object argument) {
		this.argument = argument;
	}
}
