package eg.edu.guc.yugioh.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class BoardPanel extends JPanel{

	private FieldPanel lowerFieldPanel;
	private FieldPanel upperFieldPanel;
	private PhasePanel phasePanel;
	
	public BoardPanel()
	{
		super(new BorderLayout());
		this.setPreferredSize(new Dimension(1050,725));
		this.setOpaque(false);
		lowerFieldPanel = new FieldPanel(true);
		
		upperFieldPanel = new FieldPanel(false);
		
		phasePanel = new PhasePanel();
		this.add(upperFieldPanel,BorderLayout.NORTH);
		this.add(phasePanel,BorderLayout.CENTER);
		this.add(lowerFieldPanel,BorderLayout.SOUTH);
		this.setVisible(true);
		
	}

	public FieldPanel getLowerFieldPanel() {
		return lowerFieldPanel;
	}

	public FieldPanel getUpperFieldPanel() {
		return upperFieldPanel;
	}

	public PhasePanel getPhasePanel() {
		return phasePanel;
	}
}
