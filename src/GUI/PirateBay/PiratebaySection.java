package GUI.PirateBay;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Cursor;
import java.awt.SystemColor;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.LayoutStyle.ComponentPlacement;

import Managers.PirateBayBot;
import Model.ArchivoTorrent;
import Utils.UtilTools;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class PiratebaySection extends JPanel implements Runnable{
	
	private static final long serialVersionUID = -7052443585281459283L;
	
	private ArchivoTorrent[] torrents;
	private JFrame mainFrame;
	private JTextField searchField;
	private JPanel resultsPanel;

	/**
	 * Create the panel.
	 */
	public PiratebaySection(JFrame mainFrame){
		this.mainFrame = mainFrame;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		searchPanel.setBackground(new Color(204, 255, 153));
		
		JLabel lblBuscarTorrent = new JLabel("Buscar Torrent");
		lblBuscarTorrent.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscarTorrent.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblBuscarTorrent.setBackground(Color.LIGHT_GRAY);
		
		searchField = new JTextField();
		searchField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JTextField textField = (JTextField)arg0.getSource();
				searchTorrent(textField.getText());
			}
		});
		searchField.setColumns(10);
		searchField.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		resultsPanel = new JPanel();
		resultsPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, null, null, null));
		resultsPanel.setBackground(new Color(204, 255, 153));
		scrollPane.setViewportView(resultsPanel);
		resultsPanel.setLayout(new GridLayout(0, 1, 1, 2));
		GroupLayout gl_searchPanel = new GroupLayout(searchPanel);
		gl_searchPanel.setHorizontalGroup(
			gl_searchPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_searchPanel.createSequentialGroup()
					.addGroup(gl_searchPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_searchPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(searchField, GroupLayout.PREFERRED_SIZE, 402, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_searchPanel.createSequentialGroup()
							.addGap(7)
							.addComponent(lblBuscarTorrent, GroupLayout.PREFERRED_SIZE, 398, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_searchPanel.setVerticalGroup(
			gl_searchPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_searchPanel.createSequentialGroup()
					.addGap(3)
					.addComponent(lblBuscarTorrent, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(searchField, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		searchPanel.setLayout(gl_searchPanel);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(4)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 423, GroupLayout.PREFERRED_SIZE)
						.addComponent(searchPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(searchPanel, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
					.addGap(5))
		);
		setLayout(groupLayout);

	}
	
	public void searchTorrent(String search){
		search = search.trim();
		searchField.setText(search);
		searchField.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		run();
	}
	
	private void requestSearch(String search){
		mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		if(!PirateBayBot.getInstance().isInitialized()){
			if(!PirateBayBot.getInstance().initManager()){
				new UtilTools().showWarningDialog(mainFrame, "Error", "No ha sido posible conectar con PirateBay");
			}
		}
		torrents = PirateBayBot.getInstance().searchTorrent(search, PirateBayBot.CATEGORY_ALL, PirateBayBot.ORDERBY_SEEDERS);
		if(torrents == null){
			new UtilTools().showWarningDialog(mainFrame, "Error", "Puede que no estés conectado");
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

	@Override
	public void run() {
		requestSearch(searchField.getText());
	}
}
