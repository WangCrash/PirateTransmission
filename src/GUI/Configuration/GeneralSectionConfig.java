package GUI.Configuration;

import java.util.HashMap;
import java.util.Map;

import GUI.ApplicationConfiguration;
import Managers.Manager;
import Managers.TorrentClient.TransmissionManager;
import Managers.TorrentClient.microTorrentManager;

import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;

public class GeneralSectionConfig extends ConfigurationSection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7110732631195916702L;

	private String initialTorrentClient;
	private JComboBox torrentClientBox;
	private String[] comboModel;
	/**
	 * Create the panel.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GeneralSectionConfig(Manager manager) {
		setManager(manager);
		setInitialVariables();
		JLabel lblNewLabel = new JLabel("Cliente Torrent");
		
		JLabel lblUsar = new JLabel("Usar:");
		
		comboModel = new String[]{TransmissionManager.TRANSMISSION_NAME_CONFIG_VALUE, microTorrentManager.MICROTORRENT_NAME_CONFIG_VALUE};
		torrentClientBox = new JComboBox(comboModel);
		
		if(!initialTorrentClient.equals(comboModel[0])){
			torrentClientBox.setSelectedIndex(1);
		}

		JSeparator separator = new JSeparator();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(43, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(67)
							.addComponent(lblUsar, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(torrentClientBox, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE))
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 372, GroupLayout.PREFERRED_SIZE))
					.addGap(35))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(90)
					.addComponent(lblNewLabel)
					.addGap(13)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsar)
						.addComponent(torrentClientBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(140, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}

	private void setInitialVariables() {
		initialTorrentClient = ApplicationConfiguration.getInstance().getDefaultTorrentClient().getTorrentClientName();
	}

	@Override
	public boolean isValidPassLength() {
		return true;
	}

	@Override
	public Map<String, String> getChangedValues() {
		Map<String, String> result = new HashMap<String, String>();
		if(!initialTorrentClient.equals(comboModel[torrentClientBox.getSelectedIndex()])){
			String name = comboModel[torrentClientBox.getSelectedIndex()];
			result.put(ApplicationConfiguration.DEFAULT_TORRENT_CLIENT_CONFIG_KEY, name);
			if(name.equals(microTorrentManager.MICROTORRENT_NAME_CONFIG_VALUE)){
				ApplicationConfiguration.getInstance().setDefaultTorrentClient(microTorrentManager.getInstance());
			}else{
				ApplicationConfiguration.getInstance().setDefaultTorrentClient(TransmissionManager.getInstance());
			}
		}
		System.out.println(result);
		return result;
	}

}
