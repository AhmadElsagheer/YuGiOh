package eg.edu.guc.yugioh.gui.buttons;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import eg.edu.guc.yugioh.cards.Card;

public class ActionButton extends JButton {
	
	private Card card;
	public ActionButton(String m)
	{
		super(m);
		this.setVisible(false);
		this.setBackground(Color.WHITE);
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor((new ImageIcon("resources/others/handCursor.PNG")).getImage(),new Point(), "Normal Cursor");
		this.setCursor(cursor);
	}
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}

}
