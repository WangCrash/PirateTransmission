package GUI.Panel;

import java.awt.Graphics;

@SuppressWarnings("serial")
public class MainContentPanel extends CustomPanel{

	public MainContentPanel() {
		super("/images/simple-panel-background.png", false);
		//super("/images/drawing-panel-background.png", false);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		System.out.println("REPINTANDO");
		super.paintComponent(g);
	}

}
