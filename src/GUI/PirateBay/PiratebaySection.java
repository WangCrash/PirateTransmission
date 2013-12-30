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
import java.awt.FlowLayout;

public class PiratebaySection extends JPanel {
	
	private ArchivoTorrent[] torrents;
	private JFrame mainFrame;
	private JTextField searchField;
	private JPanel resultsPanel;

	/**
	 * Create the panel.
	 */
	public PiratebaySection(JFrame parentFrame) {
		this.mainFrame = parentFrame;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		searchPanel.setBackground(SystemColor.activeCaption);
		
		JLabel label = new JLabel("Buscar: ");
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label.setBackground(Color.LIGHT_GRAY);
		
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
		resultsPanel.setBackground(SystemColor.activeCaption);
		scrollPane.setViewportView(resultsPanel);
		resultsPanel.setLayout(new GridLayout(0, 1, 0, 0));
		GroupLayout gl_searchPanel = new GroupLayout(searchPanel);
		gl_searchPanel.setHorizontalGroup(
			gl_searchPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_searchPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(searchField, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)
					.addGap(14))
		);
		gl_searchPanel.setVerticalGroup(
			gl_searchPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_searchPanel.createSequentialGroup()
					.addGap(17)
					.addGroup(gl_searchPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(searchField, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)))
		);
		searchPanel.setLayout(gl_searchPanel);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(4)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(scrollPane, Alignment.LEADING)
						.addComponent(searchPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(searchPanel, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
	
	public void searchTorrent(String search){
		search = search.trim();
		searchField.setText(search);
		searchField.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		requestSearch(search);
	}
	
	private void requestSearch(String search){
		mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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
				if(i % 2 == 0){
					cell.setBackground(new Color(196.0f/255.0f, 196.0f/255.0f, 236.0f/255.0f));
				}else{
					cell.setBackground(new Color(176.0f/255.0f, 196.0f/255.0f, 222.0f/255.0f));//light steel blue
				}
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
}
