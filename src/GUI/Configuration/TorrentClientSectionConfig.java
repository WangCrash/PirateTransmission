package GUI.Configuration;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;

import Codification.Base64;
import GUI.Panel.PanelProperties;
import Managers.Manager;
import Managers.TorrentClient.TorrentClient;
import Utils.UtilTools;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Font;

public class TorrentClientSectionConfig extends ConfigurationSection {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8542575866959232029L;
	
	private String initialRpcServer;
	private String initialUser;
	private String initialPassword;
	private boolean initialNeedsAuth;
	
	private JTextField userField;
	private JTextField rpcServerField;
	private JPasswordField passwordField;
	private JLabel userLabel;
	private JLabel passwordLabel;
	private JCheckBox needsAuthCheckBox;

	/**
	 * Create the panel.
	 */
	public TorrentClientSectionConfig(ConfigView parentView, Manager manager) {
		super(parentView);
		setManager(manager);
		setInitialVariables();
		
		setBackground(PanelProperties.BACKGROUND);
		JLabel lblNewLabel = new JLabel("RPC Server");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
		
		userLabel = new JLabel("Usuario");
		userLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
		userLabel.setBackground(PanelProperties.TRANSPARENT_BACKGROUND);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Calibri", Font.PLAIN, 15));
		passwordLabel.setBackground(PanelProperties.TRANSPARENT_BACKGROUND);
		
		userField = new JTextField();
		userField.setFont(new Font("Calibri", Font.PLAIN, 15));
		userField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				userField.selectAll();
			}
		});
		userField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				TorrentClientSectionConfig.this.parentView.keyReleased(e);
			}
		});
		userField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Calibri", Font.PLAIN, 15));
		passwordField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				passwordField.selectAll();
			}
		});
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				TorrentClientSectionConfig.this.parentView.keyReleased(e);
			}
		});
		
		needsAuthCheckBox = new JCheckBox("Necesita autenticaci\u00F3n");
		needsAuthCheckBox.setFont(new Font("Calibri", Font.PLAIN, 15));
		needsAuthCheckBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE){
					needsAuthCheckBox.doClick();
				}else{
					TorrentClientSectionConfig.this.parentView.keyReleased(e);
				}
			}
		});
		needsAuthCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JCheckBox checkBox = (JCheckBox)arg0.getSource();
				if(!checkBox.isSelected()){
					userField.setText("");
					passwordField.setText("");
				}
				enableAuthFields(checkBox.isSelected());
			}
		});

		needsAuthCheckBox.setSelected(initialNeedsAuth);
		enableAuthFields(initialNeedsAuth);
		userField.setText(initialUser);
		passwordField.setText(initialPassword);
		
		rpcServerField = new JTextField();
		rpcServerField.setFont(new Font("Calibri", Font.PLAIN, 15));
		rpcServerField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				rpcServerField.selectAll();
			}
		});
		rpcServerField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				TorrentClientSectionConfig.this.parentView.keyReleased(e);
			}
		});
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
		Map<String, String> config = new UtilTools().getConfiguration();
		
		TorrentClient torrentClient = (TorrentClient)manager;
		initialRpcServer = config.get(torrentClient.getServerConfigKey());
		if(config == null || initialRpcServer == null){
			initialRpcServer = "";
		}
		initialUser = config.get(torrentClient.getUserConfigKey());
		if(config == null || initialUser == null){
			initialUser = "";
		}
		initialPassword = config.get(torrentClient.getPasswordConfigKey());
		if(config == null || initialPassword == null){
			initialPassword = "";
		}
		initialNeedsAuth = !initialUser.isEmpty();
	}

	private String getRPCFieldText(){
		String prefix = "";
		if(rpcServerField.getText().trim().isEmpty()){
			return rpcServerField.getText().trim();
		}
		if(!rpcServerField.getText().trim().startsWith("//")){
			prefix = "//";
		}
		return prefix + rpcServerField.getText().trim();
	}
	
	private void setRPCFieldText(String rpcServer){
		if(rpcServer.startsWith("//")){
			rpcServer = rpcServer.replace("//", "");
		}
		rpcServerField.setText(rpcServer.trim());
	}

	@Override
	public Map<String, String> getChangedValues(){
		TorrentClient torrentClient = (TorrentClient)this.manager;
		Map<String, String> result = new HashMap<String, String>();
		if(!initialRpcServer.equals(getRPCFieldText())){
			result.put(torrentClient.getServerConfigKey(), getRPCFieldText());
		}
		System.out.println(this.needsAuthCheckBox.isSelected());
		//if(this.needsAuthCheckBox.isSelected()){
		if(!userField.getText().trim().equals(initialUser)){
			result.put(torrentClient.getUserConfigKey(), userField.getText().trim());
		}
		String password = new String(passwordField.getPassword());
		if(!password.equals(initialPassword)){
			if(!password.isEmpty()){
				password = Base64.encodeBytes(password.getBytes());
			}
			result.put(torrentClient.getPasswordConfigKey(), password);
		}
		/*}else{
			result.put(torrentClient.getUserConfigKey(), "");
			result.put(torrentClient.getPasswordConfigKey(), "");
		}*/
		
		return result;
	}

	@Override
	public Manager getManager() {
		return manager;
	}

	@Override
	public boolean isValidPassLength() {
		if(needsAuthCheckBox.isSelected()){
			String password = new String(passwordField.getPassword());
			return super.isValidPassLength(password);
		}
		return true;
	}
	
	private void enableAuthFields(boolean selected) {
		userLabel.setEnabled(selected);
		userField.setEnabled(selected);
		passwordLabel.setEnabled(selected);
		passwordField.setEnabled(selected);
	}
}
