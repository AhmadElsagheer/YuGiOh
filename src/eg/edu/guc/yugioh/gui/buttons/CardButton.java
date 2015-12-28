package eg.edu.guc.yugioh.gui.buttons;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import eg.edu.guc.yugioh.board.player.Player;

public abstract class CardButton extends JButton{
	
	private JLabel power=new JLabel("",JLabel.CENTER);
	
	public CardButton()
	{
		super();
		this.setLayout(new BorderLayout());
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor((new ImageIcon("resources/others/handCursor.PNG")).getImage(),new Point(), "Normal Cursor");
		this.setCursor(cursor);
	}
	
	public CardButton(String m)
	{
		super(m);
		this.setLayout(new BorderLayout());
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor((new ImageIcon("resources/others/handCursor.PNG")).getImage(),new Point(), "Normal Cursor");
		this.setCursor(cursor);
	}

	public JLabel getPower() {
		return power;
	}
	
}
