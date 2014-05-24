package GUI.SearchOptions;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import GUI.MainWindow;
import GUI.Helpers.Searcher.HelperSearcherSection;
import GUI.Helpers.Searcher.OptionListComparator;
import GUI.Helpers.Searcher.SearcherView;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SearchOptionsView extends JDialog implements ActionListener{
	private SearcherView parentView;
	private int selectedOption;
	
	private Map<String, Integer> options; 
	private JRadioButton[] optionButtons;
	private ButtonGroup optionButtonsGroup;

	private final JPanel contentPanel = new JPanel();
	private JPanel radioButtonsPanel;
	private JButton okButton;
	private JButton cancelButton;
	private JLabel titleLabel;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Map<String, Integer> options = new HashMap<String, Integer>();
					options.put("Todo", 1);
					options.put("vídeo", 2);
					options.put("música", 3);
					JFrame mainFrame = new JFrame();
					SearchOptionsView dialog = new SearchOptionsView(new JFrame(), new SearcherView() {
						
						@Override
						public void setSearchOption(int searchOption) {
							System.out.println("DONE");
						}
						
						@Override
						public JButton getConfigSearchButton() {
							return new JButton("button");
						}
					}, "Opciones búsqueda de películas", options, 1);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public SearchOptionsView(JFrame mainFrame, SearcherView parentView, String title, Map<String, Integer> options, int selectedButton) {
		super(mainFrame, true);
		this.parentView = parentView;
		this.options = options;		
		setUndecorated(true);		
		setBounds(100, 100, 238, 214);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			radioButtonsPanel = new JPanel();
			radioButtonsPanel.setLayout(new GridLayout(0, 1, 100, 0));
		}
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(51)
					.addComponent(radioButtonsPanel, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(radioButtonsPanel, GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		titleLabel = new JLabel(title);
		panel.add(titleLabel);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new LineBorder(new Color(0, 0, 0)));
			buttonPane.setBackground(Color.WHITE);
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						okButtonPressed();
					}
				});
				okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						close();
					}
				});
				cancelButton.setActionCommand("Cancel");
			}
			GroupLayout gl_buttonPane = new GroupLayout(buttonPane);
			gl_buttonPane.setHorizontalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createSequentialGroup()
						.addGap(39)
						.addComponent(okButton, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
						.addGap(36))
			);
			gl_buttonPane.setVerticalGroup(
				gl_buttonPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_buttonPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(okButton)
						.addComponent(cancelButton))
			);
			buttonPane.setLayout(gl_buttonPane);
		}
		
		makeButtonsList(selectedButton);
		
		//this.setLocationRelativeTo(mainFrame);
		this.setLocationRelativeTo(parentView.getConfigSearchButton());
	}

	private void makeButtonsList(int selectedButton) {
		selectedOption = selectedButton;
		optionButtonsGroup = new ButtonGroup();
		int i = 0;
		boolean someButtonSelected = false;
		optionButtons = new JRadioButton[options.size()];
		
		Map<Integer, String> optionsOrder = new HashMap<Integer, String>();
		for (Map.Entry<String, Integer> entry : options.entrySet()){
			optionsOrder.put(entry.getValue(), entry.getKey());
		}
		
		List<Integer> order = new ArrayList<Integer>(options.values());
		
		Collections.sort(order, new OptionListComparator());
		
		for (Integer index : order) {
			JRadioButton radioButton = new JRadioButton(optionsOrder.get(index));
			radioButton.addActionListener(this);
			radioButton.setSelected(index.intValue() == selectedButton);
			optionButtons[i] = radioButton;
			optionButtonsGroup.add(radioButton);
			radioButtonsPanel.add(radioButton);
			if(!someButtonSelected){
				someButtonSelected = (index == selectedButton);
			}
			i++;
		}
		if(!someButtonSelected){
			optionButtons[0].setSelected(true);
			optionButtons[0].doClick();
		}
	}
	
	private void close() {
		this.setVisible(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void okButtonPressed() {
		parentView.setSearchOption(selectedOption);
		close();  
	}
	
	public void actionPerformed(ActionEvent e) {
		selectedOption = options.get(e.getActionCommand());
		System.out.println(e.getActionCommand() + " option: " + selectedOption);
	}
}
