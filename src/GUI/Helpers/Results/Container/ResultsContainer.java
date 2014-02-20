package GUI.Helpers.Results.Container;

import java.awt.Rectangle;

import javax.swing.JPanel;

import GUI.MainWindow;
import GUI.Helpers.Results.HelperResultsSection;
import Model.HelperItem;

public abstract class ResultsContainer extends JPanel{
	private static final long serialVersionUID = -1930445475344304331L;
	
	protected MainWindow mainFrame;
	protected HelperResultsSection parentView;
	protected HelperItem[] items;
	
	public ResultsContainer(MainWindow mainFrame, HelperItem[] items){
		this.mainFrame = mainFrame;
		this.setResults(items);
	}
	
	public void setResults(HelperItem[] items){
		this.items = items;
	}
	protected abstract void showResults();
	
	public abstract void setScrollPosition(int verticalValue);
	public abstract int getScrollValue();
	
	public void repaintAt(Rectangle rectangle){
		this.repaint(rectangle);
	}
}
