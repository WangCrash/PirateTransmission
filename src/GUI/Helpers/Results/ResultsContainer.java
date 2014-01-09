package GUI.Helpers.Results;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.HelperItem;

public abstract class ResultsContainer extends JPanel{
	private static final long serialVersionUID = -1930445475344304331L;
	
	protected JFrame mainFrame;
	protected HelperItem[] items;
	
	public ResultsContainer(JFrame mainFrame, HelperItem[] items){
		this.mainFrame = mainFrame;
		this.setResults(items);
	}
	
	public void setResults(HelperItem[] items){
		this.items = items;
	}
	protected abstract void showResults();	
}
