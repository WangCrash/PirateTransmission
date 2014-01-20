package GUI.Helpers.Results.Items.Music;

import javax.swing.JFrame;

import GUI.Helpers.Results.HelperResultsSection;
import GUI.Helpers.Results.Items.HelperResultItem;
import Model.HelperItem;

@SuppressWarnings("serial")
public abstract class MusicItemCell extends HelperResultItem{

	public MusicItemCell(JFrame mainFrame, HelperResultsSection parentView,	HelperItem helperItem) {
		super(mainFrame, parentView, helperItem);
	}
	
	protected abstract void getItemTags();
}
