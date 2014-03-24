package GUI.Configuration;

import java.util.HashMap;
import java.util.Map;

import Codification.Base64;
import GUI.Panel.PanelProperties;
import Managers.Manager;
import Managers.Helpers.HelperManager;
import Utils.UtilTools;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

@SuppressWarnings("serial")
public class SimpleSectionConfig extends ConfigurationSection {
	
	private JTextField userField;
	private JPasswordField passwordField;
	
	private String initialUser;
	private String initialPassword;
	private JButton registerButton;

	/**
	 * Create the panel.
	 */
	public SimpleSectionConfig(ConfigView parentView, String initialUser, String initialPassword) {
		super(parentView);
		this.initialUser = (initialUser == null)?"":initialUser;
		this.initialPassword = (initialPassword == null)?"":initialPassword;
		
		setBackground(PanelProperties.BACKGROUND);
		JLabel lblNewLabel = new JLabel("Usuario");
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		
		userField = new JTextField();
		userField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				userField.selectAll();
			}
		});
		userField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				SimpleSectionConfig.this.parentView.keyReleased(e);
			}
		});
		userField.setColumns(10);
		userField.setText(initialUser);
		
		passwordField = new JPasswordField();
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				passwordField.selectAll();
			}
		});
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				SimpleSectionConfig.this.parentView.keyReleased(e);
			}
		});
		passwordField.setText(initialPassword);
		
		registerButton = new JButton("Registrarse");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendToSignUpPage();
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(58)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(passwordField)
						.addComponent(userField, GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
					.addContainerGap(89, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(186, Short.MAX_VALUE)
					.addComponent(registerButton)
					.addGap(177))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(55)
					.addComponent(registerButton)
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(userField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(41)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(112, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}

	@Override
	public Map<String, String> getChangedValues() {
		HelperManager helperManager = (HelperManager)this.manager;
		Map<String, String> result = new HashMap<String, String>();
		if(!initialUser.equals(userField.getText().trim())){
			result.put(helperManager.getUserConfigKey(), userField.getText().trim());
		}
		String password = new String(passwordField.getPassword());
		if(!initialPassword.equals(password)){
			if(!password.isEmpty()){
				password = Base64.encodeBytes(password.getBytes());
			}
			result.put(helperManager.getPasswordConfigKey(), password);
		}
		return result;
	}

	@Override
	public Manager getManager() {
		return manager;
	}

	@Override
	public boolean isValidPassLength() {
		if(!userField.getText().isEmpty()){
			String password = new String(passwordField.getPassword());
			return super.isValidPassLength(password);
		}
		return true;
	}
	
	private void sendToSignUpPage() {
		new UtilTools().openURLInNavigator(((HelperManager)manager).getSignUpURL());
	}
}
