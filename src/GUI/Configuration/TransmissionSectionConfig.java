package GUI.Configuration;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;

import Codification.Base64;
import Managers.Manager;
import Managers.TorrentClient.TransmissionManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

public class TransmissionSectionConfig extends ConfigurationSection {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8542575866959232029L;
	
	private String initialRpcServer;
	private String initialUser;
	private String initialPassword;
	
	private JTextField userField;
	private JTextField rpcServerField;
	private JPasswordField passwordField;
	private JLabel userLabel;
	private JLabel passwordLabel;
	private JCheckBox needsAuthCheckBox;

	/**
	 * Create the panel.
	 */
	public TransmissionSectionConfig() {
		setInitialVariables();
		
		JLabel lblNewLabel = new JLabel("RPC Server");
		
		userLabel = new JLabel("Usuario");
		
		passwordLabel = new JLabel("Password");
		
		userField = new JTextField();
		userField.setColumns(10);
		
		passwordField = new JPasswordField();
		
		needsAuthCheckBox = new JCheckBox("Necesita autenticaci\u00F3n");
		needsAuthCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JCheckBox checkBox = (JCheckBox)arg0.getSource();
				userLabel.setEnabled(checkBox.isSelected());
				userField.setEnabled(checkBox.isSelected());
				passwordLabel.setEnabled(checkBox.isSelected());
				passwordField.setEnabled(checkBox.isSelected());
			}
		});
		if(TransmissionManager.getInstance().user.isEmpty()){
			needsAuthCheckBox.setSelected(false);
			needsAuthCheckBox.doClick();//porque inicialmente está marcado
		}else{
			needsAuthCheckBox.setSelected(true);
			userField.setText(initialUser);
			passwordField.setText(initialPassword);
		}
		
		rpcServerField = new JTextField();
		rpcServerField.setColumns(10);
		setRPCFieldText(initialRpcServer);
		
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(48)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(needsAuthCheckBox)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(rpcServerField, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(userLabel)
								.addComponent(passwordLabel))
							.addGap(38)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(passwordField)
								.addComponent(userField, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(97, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(36)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(rpcServerField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(19)
					.addComponent(needsAuthCheckBox)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(userLabel)
						.addComponent(userField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordLabel)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(36, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
	
	private void setInitialVariables() {
		initialRpcServer = TransmissionManager.getInstance().urlBase;
		initialUser = TransmissionManager.getInstance().user;
		initialPassword = TransmissionManager.getInstance().password;
	}

	private String getRPCFieldText(){
		String prefix = "";
		if(!rpcServerField.getText().startsWith("//")){
			prefix = "//";
		}
		return prefix + rpcServerField.getText();
	}
	
	private void setRPCFieldText(String rpcServer){
		if(rpcServer.startsWith("//")){
			rpcServer = rpcServer.replace("//", "");
		}
		rpcServerField.setText(rpcServer.trim());
	}

	@Override
	public Map<String, String> getChangedValues(){
		Map<String, String> result = new HashMap<String, String>();
		if(!initialRpcServer.equals(getRPCFieldText())){
			result.put(TransmissionManager.TRANSMISSION_RPC_SERVER_CONFIG_KEY, getRPCFieldText());
		}
		if(this.needsAuthCheckBox.isSelected()){
			if(!initialUser.equals(userField.getText().trim())){
				result.put(TransmissionManager.TRANSMISSION_USER_AUTH_CONFIG_KEY, userField.getText().trim());
			}
			String password = new String(passwordField.getPassword());
			if(!initialPassword.equals(password)){
				result.put(TransmissionManager.TRANSMISSION_PASSWORD_AUTH_CONFIG_KEY, Base64.encodeBytes(password.getBytes()));
			}
		}
		System.out.println(result);
		return result;
	}

	@Override
	public Manager getManager() {
		return manager;
	}

	@Override
	public void setManager(Manager manager) {
		this.manager = manager;
	}

	@Override
	public boolean isValidPassLength() {
		String password = new String(passwordField.getPassword());
		return super.isValidPassLength(password);
	}
}
