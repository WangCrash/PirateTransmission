package GUI.Panel;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Container;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.plaf.LayerUI;

import Utils.UtilTools;

@SuppressWarnings("serial")
public abstract class WallpaperLayerUI extends LayerUI<Container> {
	//private boolean makeTransparent;
	private Image backgroundImage;
	
	public WallpaperLayerUI(){
		this(null);
	}
	
	public WallpaperLayerUI(String backgroundImagePath){
		this(backgroundImagePath, -1, -1);
	}
	
	public WallpaperLayerUI(String backgroundImagePath, int width, int height){
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
//		this.setDoubleBuffered(true);
//		this.setOpaque(false);
	}
	@Override
	public void paint(Graphics g, JComponent c) {
	    super.paint(g, c);
	
	    Graphics2D g2 = (Graphics2D) g.create();
	
	    int w = c.getWidth();
	    int h = c.getHeight();
	    //g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .3f));
	    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN, 1.0f));
	    g2.setPaint(new GradientPaint(0, 0, Color.RED, 0, h, Color.YELLOW));
	    g2.fillRect(0, 0, w, h);	
	    g2.dispose();   
	}
	
//	@Override
//	public void paint(Graphics g, JComponent c){
//		Graphics2D g2 = (Graphics2D)g.create();
//		super.paint(g, c);
//		
//		if(backgroundImage != null){
//			g.drawImage(backgroundImage, 0, 0, null);
//		}else{
//		    int w = c.getWidth();
//		    int h = c.getHeight();
//			g2.setPaint(new GradientPaint(0, 0, Color.BLUE, 0, h, Color.WHITE));
//		    g2.fillRect(0, 0, w, h);
//		}
//		
//		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//		
//		g2.dispose();
//	}
}
