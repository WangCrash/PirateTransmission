package GUI.Helpers.Results.Items;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;

import Managers.Helpers.FilmAffinityBot;
import Model.FichaPelicula;
import Utils.JTextFieldLimit;
import Utils.UtilTools;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Map;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;

public class RateMovieView extends JDialog {

	private static final long serialVersionUID = 8898559080248268287L;
	
	private JFrame mainFrame;
	private FichaPelicula film;
	private FilmResultItem parentView;
	
	
	private final JPanel contentPanel = new JPanel();
	private JTextField rateField;

	/**
	 * Create the dialog.
	 */
	public RateMovieView(JFrame rootFrame, FilmResultItem parentView, FichaPelicula film) {
		super(rootFrame, true);
		getContentPane().setForeground(Color.ORANGE);
		this.mainFrame = rootFrame;
		this.parentView = parentView;
		this.film = film;
		
		setUndecorated(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("Button.background"));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
					.addContainerGap())
		);
		JLabel titleLabel = new JLabel("T\u00EDtulo");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		rateField = new JTextField();
		rateField.setHorizontalAlignment(SwingConstants.CENTER);
		rateField.setFont(new Font("Tahoma", Font.PLAIN, 50));
		rateField.setColumns(2);
		rateField.setDocument(new JTextFieldLimit(2));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(titleLabel, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(rateField, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE)
							.addGap(89))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(26)
					.addComponent(titleLabel)
					.addGap(27)
					.addComponent(rateField, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(40, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, null, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						okButtonPressed();
					}

				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						close();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		this.setLocationRelativeTo(this.mainFrame);
	}

	private void close() {
		this.setVisible(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private boolean rateMovie(){
		UtilTools tools = new UtilTools();
		String rating = rateField.getText().trim();
		int value;
		try{
			value = Integer.parseInt(rating);
		}catch(NumberFormatException e){
			tools.showInfoOKDialog(mainFrame, "","Sólo números");
			if(!rating.isEmpty()){
				return false;
			}
		}
		if((film.getDataUcd() == null) || film.getDataUcd().isEmpty()){
			film = FilmAffinityBot.getInstance().fillFichaPelicula(film);
			if(film == null){
				tools.showWarningDialog(mainFrame, "Error","Puede que no estés conectado");
				return false;
			}
		}
		if(rating.isEmpty()){
			rating = FilmAffinityBot.FILMAFFINITY_FILM_NOT_WATCHED;
		}
		film.setNotaUsuario(rating);
		if(FilmAffinityBot.getInstance().rateItem(film)){
			tools.showInfoOKDialog(mainFrame, "","Votado!");
			return true;
		}else{
			tools.showWarningDialog(mainFrame, "Error", "La película no ha podido ser votada");
			return false;
		}
	}
	private void okButtonPressed() {
		if(rateMovie()){
			parentView.filmSuccesfullyRated(film);
			close();
		}
	}
}
