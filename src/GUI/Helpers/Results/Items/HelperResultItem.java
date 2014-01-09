package GUI.Helpers.Results.Items;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GUI.Helpers.Results.HelperResultsSection;
import Model.HelperItem;

public abstract class HelperResultItem extends JPanel{

	private static final long serialVersionUID = 6271389072211106369L;
	
	protected JFrame mainFrame;
	protected HelperResultsSection parentView;
	protected HelperItem helperItem;
	
	public HelperResultItem(JFrame mainFrame, HelperResultsSection parentView, HelperItem helperItem){
		this.mainFrame = mainFrame;
		this.parentView = parentView;
		this.setHelperItem(helperItem);
	}
	
	public HelperItem getHelperItem(){
		return helperItem;
	}
	
	public void setHelperItem(HelperItem helperItem){
		this.helperItem = helperItem;
	}
	
	public abstract void searchItemTorrent();
	
	public abstract void rateItem();
}
