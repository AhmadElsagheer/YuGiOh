package eg.edu.guc.yugioh.gui.panels;

import java.awt.*;

import javax.swing.*;

public class FieldPanel extends JPanel {
	
	private SpellsPanel spellsPanel;
	private MonstersPanel monstersPanel;
	private HandPanel handPanel;
	
	public FieldPanel(boolean isLower)
	{
		super(new FlowLayout());
		this.setPreferredSize(new Dimension(1050,320));
		this.setOpaque(false);
		spellsPanel = new SpellsPanel(isLower);
		monstersPanel = new MonstersPanel(isLower);
		handPanel = new HandPanel(isLower);

		if(isLower)
		{
			this.add(monstersPanel);
			this.add(spellsPanel);
			this.add(handPanel);
		}
		
		else
		{
			this.add(handPanel);
			this.add(spellsPanel);
			this.add(monstersPanel);
		}
	}

	public SpellsPanel getSpellsPanel() {
		return spellsPanel;
	}

	public MonstersPanel getMonstersPanel() {
		return monstersPanel;
	}

	public HandPanel getHandPanel() {
		return handPanel;
	}

}
