package GUI.PirateBay;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Cursor;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

import GUI.Helpers.Searcher.SearcherView;
import GUI.Panel.PanelProperties;
import Managers.PirateBayBot;
import Model.ArchivoTorrent;
import Utils.UtilTools;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import GUI.SearchOptions.SearchOptionsView;;

public class PiratebaySection extends JPanel implements Runnable, SearcherView{
	
	private static final int Category_All = 1;
	private static final int Category_Video = 2;
	private static final int Category_Music = 3;
	
	private static final long serialVersionUID = -7052443585281459283L;
	
	private ArchivoTorrent[] torrents;
	private JFrame mainFrame;
	private JTextField searchField;
	private JPanel resultsPanel;
	private JButton btnNewButton;
	private int searchOption;

	/**
	 * Create the panel.
	 */
	public PiratebaySection(JFrame mainFrame){
		setBackground(new Color(255, 255, 255));
		this.mainFrame = mainFrame;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setBackground(PanelProperties.BACKGROUND);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBorder(PanelProperties.BORDER);
		searchPanel.setBackground(PanelProperties.BACKGROUND);
		
		JLabel lblBuscarTorrent = new JLabel("Buscar Torrent");
		lblBuscarTorrent.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscarTorrent.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBuscarTorrent.setBackground(Color.LIGHT_GRAY);
		
		searchField = new JTextField();
		searchField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTextField textField = (JTextField)arg0.getSource();
				searchTorrent(textField.getText(), translateToPirateBayCategory(searchOption));
			}
		});
		searchField.setColumns(10);
		searchField.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		resultsPanel = new JPanel();
		resultsPanel.setBorder(PanelProperties.BORDER);
		resultsPanel.setBackground(PanelProperties.BACKGROUND);
		scrollPane.setViewportView(resultsPanel);
		resultsPanel.setLayout(new GridLayout(0, 1, 1, 2));
		
		btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showConfigSearchView();
			}
		});
		btnNewButton.setIcon(new ImageIcon(PiratebaySection.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		GroupLayout gl_searchPanel = new GroupLayout(searchPanel);
		gl_searchPanel.setHorizontalGroup(
			gl_searchPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_searchPanel.createSequentialGroup()
					.addGroup(gl_searchPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_searchPanel.createSequentialGroup()
							.addGap(7)
							.addComponent(lblBuscarTorrent, GroupLayout.PREFERRED_SIZE, 398, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_searchPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(searchField, GroupLayout.PREFERRED_SIZE, 374, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(93, Short.MAX_VALUE))
		);
		gl_searchPanel.setVerticalGroup(
			gl_searchPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_searchPanel.createSequentialGroup()
					.addGap(3)
					.addComponent(lblBuscarTorrent, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(gl_searchPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(searchField, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		searchPanel.setLayout(gl_searchPanel);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(4)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(searchPanel, GroupLayout.PREFERRED_SIZE, 423, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 423, GroupLayout.PREFERRED_SIZE)))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(searchPanel, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 574, GroupLayout.PREFERRED_SIZE))
		);
		setLayout(groupLayout);
		
		searchOption = translatePirateBayCategory(PirateBayBot.CATEGORY_ALL);
	}
	
	public void searchTorrent(String search, int category){
		search = search.trim();
		searchField.setText(search);
		searchField.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		searchOption = translatePirateBayCategory(category);
		run();
	}
	
	private void requestSearch(String search, int category){
		mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		if(!PirateBayBot.getInstance().isInitialized()){
			if(!PirateBayBot.getInstance().initManager()){
				new UtilTools().showWarningDialog(mainFrame, "Error", "No ha sido posible conectar con PirateBay");
			}
		}
		torrents = PirateBayBot.getInstance().searchTorrent(search, category, PirateBayBot.ORDERBY_SEEDERS);
		if(torrents == null){
			new UtilTools().showWarningDialog(mainFrame, "Error", "Puede que no est�s conectado");
			setDefaultCursor();
			return;
		}
		drawTorrentsList();
	}
	
	private void drawTorrentsList(){
		if(torrents.length == 0){
			new UtilTools().showInfoOKDialog(mainFrame, "", "No hay resultados");
		}else{
			if(resultsPanel.getComponentCount() > 0){
				resultsPanel.removeAll();
			}
			for(int i = 0; i < torrents.length; i++){
				TorrentCell cell = new TorrentCell(torrents[i]);
				cell.setMainFrame(mainFrame);
				/*if(i % 2 == 0){
					//amarillo suave
					cell.setBackground(new Color(255.0f/255.0f, 247.0f/255.0f, 213.0f/255.0f));
				}else{
					//azul suave
					//[r=149,g=201,b=250]
					cell.setBackground(new Color(149.0f/255.0f, 201.0f/255.0f, 250.0f/255.0f));
				}*/
				resultsPanel.add(cell);
			}
			resultsPanel.revalidate();
		}

		setDefaultCursor();
	}
	
	private void setDefaultCursor(){
		searchField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
	
	private void showConfigSearchView() {
		Map<String, Integer> optionsIndexes = new HashMap<String, Integer>();
		optionsIndexes.put("Todo", 1);
		optionsIndexes.put("V�deo", 2);
		optionsIndexes.put("M�sica", 3);
		
		SearchOptionsView searchOptionsView = new SearchOptionsView(mainFrame, this, "Opciones de b�squeda", optionsIndexes, searchOption);
		searchOptionsView.setVisible(true);
	}

	@Override
	public void run() {
		requestSearch(searchField.getText(), translateToPirateBayCategory(searchOption));
	}

	@Override
	public void setSearchOption(int searchOption) {
		this.searchOption = searchOption;
	}
	public JButton getConfigSearchButton() {
		return btnNewButton;
	}
	
	private int translatePirateBayCategory(int category){
		int result = 1;
		switch (category) {
		case PirateBayBot.CATEGORY_ALL:	
			result = 1;
			break;
			
		case PirateBayBot.CATEGORY_VIDEO:
			result = 2;
			break;
			
		case PirateBayBot.CATEGORY_MUSIC:
			result = 3;
			break;

		default:
			break;
		}
		return result;
	}
	
	private int translateToPirateBayCategory(int category){
		int result = 1;
		switch (category) {
		case Category_All:	
			result = PirateBayBot.CATEGORY_ALL;
			break;
			
		case Category_Video:
			result = PirateBayBot.CATEGORY_VIDEO;
			break;
			
		case Category_Music:
			result = PirateBayBot.CATEGORY_MUSIC;
			break;

		default:
			break;
		}
		return result;
	}
}
