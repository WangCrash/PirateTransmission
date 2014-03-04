package GUI.Panel;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;

public class PanelProperties extends JPanel{
	public PanelProperties() {
		setBackground(new Color(153, 204, 204));
	}
	//public static final Color BACKGROUND = new Color(153, 204, 255, 99);
	public static final Color TRANSPARENT_BACKGROUND = new Color(255, 255, 255, 0);
	public static final Color BACKGROUND = new Color(153, 204, 204);
	public static final Border BORDER = new SoftBevelBorder(BevelBorder.RAISED, new Color(64, 64, 64), new Color(64, 64, 64), new Color(64, 64, 64), Color.DARK_GRAY);
}
