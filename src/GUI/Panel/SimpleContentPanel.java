package GUI.Panel;

import java.awt.Graphics;

@SuppressWarnings("serial")
public class SimpleContentPanel extends CustomPanel {

	public SimpleContentPanel(int width, int height) {
		super("/images/simple-panel-background.png", false);
	}
	
	@Override
	protected  void paintComponent(Graphics g){
		System.out.println("Pintando Una de Simple");
		super.paintComponent(g);
	}

}
