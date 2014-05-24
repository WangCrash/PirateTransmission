package Utils;

public abstract class MultipleArgumentsRunnableObject implements Runnable {

	private Object[] arguments;
	
	public MultipleArgumentsRunnableObject(Object[] arguments){
		this.setArguments(arguments);
	}
	
	public Object[]getArguments() {
		return arguments;
	}

	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}
	
	public Object getArgumentAtIndex(int index){
		if (index < 0 || index >= arguments.length) {
			return null;
		}
		
		return arguments[index];
	}

}
