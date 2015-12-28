package eg.edu.guc.yugioh.gui.panels;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class PhasePanel extends JPanel {
	
	JLabel main1 = new JLabel("Main 1",JLabel.CENTER);
	JLabel battle = new JLabel("Battle",JLabel.CENTER);
	JLabel main2 = new JLabel("Main 2",JLabel.CENTER);
	
	public PhasePanel() 
	{
		super(new GridLayout(1,3));
		this.setOpaque(false);
		Font customFont;
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/others/prince.ttf")).deriveFont(40f);
			main1.setFont(customFont);
			main2.setFont(customFont);
			battle.setFont(customFont);
		} catch (FontFormatException e) {
			
		} catch (IOException e) {
			
		}
		
		main1.setForeground(Color.white);
		main2.setForeground(Color.white);
		battle.setForeground(Color.white);
		this.add(main1);
		this.add(battle);
		this.add(main2);
	}

	public JLabel getMain1() {
		return main1;
	}

	public JLabel getBattle() {
		return battle;
	}

	public JLabel getMain2() {
		return main2;
	}

}
