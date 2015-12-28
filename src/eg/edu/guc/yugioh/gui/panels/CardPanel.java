package eg.edu.guc.yugioh.gui.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import eg.edu.guc.yugioh.gui.buttons.ActionButton;

public class CardPanel extends JPanel {
	
	private JLabel cardView = new JLabel();
	private ActionButton summonMonster = new ActionButton("Summon Monster");
	private ActionButton setMonster = new ActionButton("Set Monster");
	private ActionButton setSpell = new ActionButton("Set Spell");
	private ActionButton activateSpell = new ActionButton("Activate Spell");
	private JButton sacrifice = new JButton("Sacrifice");
	private JButton cancel = new JButton("Cancel");
	private ActionButton attack = new ActionButton("Attack");
	private ActionButton changeMode= new ActionButton("Change Mode");
	private JLabel power = new JLabel("",JLabel.CENTER);
	
	public CardPanel()
	{
		super(new FlowLayout());
		this.setPreferredSize(new Dimension(300, 428));
		this.setOpaque(false);
		cardView.setPreferredSize(new Dimension(300,378));
		cardView.setLayout(new FlowLayout(FlowLayout.TRAILING,24,342));
		cardView.add(power);
		this.add(cardView);
		this.add(summonMonster);
		this.add(setMonster);
		this.add(activateSpell);
		this.add(setSpell);
		this.add(attack);
		this.add(changeMode);
		sacrifice.setVisible(false);
		cancel.setVisible(false);
		this.add(sacrifice);
		this.add(cancel);
		sacrifice.setBackground(Color.white);
		cancel.setBackground(Color.white);
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor((new ImageIcon("resources/others/handCursor.PNG")).getImage(),new Point(), "Normal Cursor");
		sacrifice.setCursor(cursor);
		cancel.setCursor(cursor);
		
		
	}
	
	public void hideButtons()
	{
		summonMonster.setVisible(false);
		sacrifice.setVisible(false);
		cancel.setVisible(false);
		setMonster.setVisible(false);
		setSpell.setVisible(false);
		activateSpell.setVisible(false);
		attack.setVisible(false);
		changeMode.setVisible(false);
	}
	
	public JLabel getCardView() {
		return cardView;
	}

	public ActionButton getSummonMonster() {
		return summonMonster;
	}

	public ActionButton getSetMonster() {
		return setMonster;
	}

	public ActionButton getSetSpell() {
		return setSpell;
	}

	public ActionButton getActivateSpell() {
		return activateSpell;
	}

	public JButton getSacrifice() {
		return sacrifice;
	}

	public JButton getCancel() {
		return cancel;
	}

	public ActionButton getAttack() {
		return attack;
	}

	public ActionButton getChangeMode() {
		return changeMode;
	}

	public JLabel getPower() {
		return power;
	}

}
