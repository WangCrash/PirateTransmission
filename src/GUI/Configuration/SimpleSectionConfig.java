package GUI.Configuration;

import java.util.HashMap;
import java.util.Map;

import Codification.Base64;
import Managers.Manager;
import Managers.Helpers.FilmAffinityBot;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;

public class SimpleSectionConfig extends ConfigurationSection {

	private static final long serialVersionUID = 1261620105359922279L;
	
	private JTextField userField;
	private JPasswordField passwordField;
	
	private String initialUser;
	private String initialPassword;

	/**
	 * Create the panel.
	 */
	public SimpleSectionConfig(String initialUser, String initialPassword) {
		this.initialUser = initialUser;
		this.initialPassword = initialPassword;
		JLabel lblNewLabel = new JLabel("Usuario");
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		
		userField = new JTextField();
		userField.setColumns(10);
		userField.setText(initialUser);
		
		passwordField = new JPasswordField();
		passwordField.setText(initialPassword);
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
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(107)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(userField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(41)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(124, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}

	@Override
	public Map<String, String> getChangedValues() {
		Map<String, String> result = new HashMap<String, String>();
		if(!initialUser.equals(userField.getText().trim())){
			result.put(FilmAffinityBot.FILMAFFINITY_USER_AUTH_CONFIG_KEY, userField.getText().trim());
		}
		String password = new String(passwordField.getPassword());
		if(!initialPassword.equals(password)){
			if(!password.isEmpty()){
				password = Base64.encodeBytes(password.getBytes());
			}
			result.put(FilmAffinityBot.FILMAFFINITY_PASSWORD_AUTH_CONFIG_KEY, password);
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
}
