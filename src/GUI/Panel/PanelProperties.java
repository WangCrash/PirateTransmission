package GUI.Panel;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

public class PanelProperties extends JPanel{
	public PanelProperties() {
		setBorder(UIManager.getBorder("ToggleButton.border"));
		setBackground(new Color(153, 204, 204));
	}
	//public static final Color BACKGROUND = new Color(153, 204, 255, 99);
	public static final Color TRANSPARENT_BACKGROUND = new Color(255, 255, 255, 0);
	public static final Color BACKGROUND = new Color(153, 204, 204);
	//public static final Border BORDER = UIManager.getBorder("DesktopIcon.border");
	public static final Border BORDER = new CompoundBorder(new LineBorder(new Color(0, 0, 0), 1, true), new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(180, 180, 180), new Color(180, 180, 180)));
}
