package GUI.Panel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

import Utils.UtilTools;

@SuppressWarnings("serial")
public abstract class CustomPanel extends JPanel {
	private boolean makeTransparent;
	private Image backgroundImage;
	
	
	public CustomPanel(String backgroundImagePath, boolean makeTransparent){
		this(backgroundImagePath, makeTransparent, -1, -1);
	}
	
	public CustomPanel(String backgroundImagePath, boolean makeTransparent, int width, int height){
		super();
		if(backgroundImagePath != null){
			Image image = Toolkit.getDefaultToolkit().getImage(CustomPanel.class.getResource(backgroundImagePath));
			if(width > 0 && height > 0){
				image = new UtilTools().getScaledImage(image, width, height);
			}
			this.backgroundImage = image;
		}else{
			this.backgroundImage = null;
		}
		this.makeTransparent = makeTransparent;
		this.setDoubleBuffered(true);
		this.setOpaque(false);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		if(backgroundImage != null){
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, this);
		}
		if(makeTransparent){
			Graphics2D g2 = (Graphics2D) g;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 1f));
            if (isBackgroundSet()) {
                Color c = getBackground();
                g2.setColor(c);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
            super.paintComponent(g2);
		}
	}
}
