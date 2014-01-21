package GUI.Helpers.Results.Items.Music;

import javax.swing.JFrame;

import GUI.Helpers.Results.HelperResultsSection;
import GUI.Helpers.Results.Items.HelperResultItem;
import Managers.Helpers.FilmAffinityBot;
import Model.FichaPelicula;
import Model.HelperItem;
import Utils.UtilTools;

@SuppressWarnings("serial")
public abstract class MusicResultItem extends HelperResultItem{

	public MusicResultItem(JFrame mainFrame, HelperResultsSection parentView,	HelperItem helperItem) {
		super(mainFrame, parentView, helperItem);
	}
	
	protected abstract void getItemTags();
}
