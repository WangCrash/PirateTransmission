package GUI.Panel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

import GUI.PirateTransmissionDemo;

@SuppressWarnings("serial")
public abstract class CustomPanel extends JPanel {
	private boolean makeTransparent;
	private String backgroundImagePath;
	
	public CustomPanel(String backgroundImage, boolean makeTransparent){
		this.backgroundImagePath = backgroundImage;
		this.makeTransparent = makeTransparent;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		if(backgroundImagePath != null){
			super.paintComponent(g);
			g.drawImage(Toolkit.getDefaultToolkit().getImage(PirateTransmissionDemo.class.getResource(backgroundImagePath)), 0, 0, null);
		}
		if(makeTransparent){
			Graphics2D g2 = (Graphics2D) g;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            if (isBackgroundSet()) {
                Color c = getBackground();
                g2.setColor(c);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
            super.paintComponent(g2);
		}
	}
}
