package eg.edu.guc.yugioh.cards;

public class MonsterCard extends Card  
{

	private int level;//1-8?
	private int defensePoints;
	private int attackPoints;
	private Mode mode = Mode.DEFENSE;
	private boolean attacked;
	private boolean modeChanged;
	
	public MonsterCard(String name, String description, int level, int attack, int defence)
	{
		super(name, description);
		this.level = level;
		this.attackPoints = attack;
		this.defensePoints = defence;
	}

	public int getLevel()
	{
		return this.level;
	}
	
	public int getDefensePoints()
	{
		return this.defensePoints;
	}
	
	public void setDefensePoints(int defensePoints)
	{
		this.defensePoints = defensePoints;
	}
	
	public int getAttackPoints()
	{
		return this.attackPoints;
	}
	
	public void setAttackPoints(int attackPoints)
	{
		this.attackPoints = attackPoints;
	}
	
	public Mode getMode()
	{
		return this.mode;
	}
	
	public void setMode(Mode mode)
	{
		this.mode = mode;
	}

	public boolean isAttacked() {
		return attacked;
	}

	public void setAttacked(boolean attacked) {
		this.attacked = attacked;
	}
	
	public void action(MonsterCard monster)
	{
		int activePoints = getBoard().getActivePlayer().getLifePoints();
		int opponentPoints = getBoard().getOpponentPlayer().getLifePoints();
		if(monster==null)
			opponentPoints-=this.attackPoints;
		else
			if(monster.getMode()==Mode.ATTACK)
			{
				if(this.attackPoints>monster.attackPoints)
				{
					opponentPoints-=(this.attackPoints-monster.attackPoints);
					getBoard().getOpponentPlayer().getField().removeMonsterToGraveyard(monster);
				}
				else if(this.attackPoints==monster.attackPoints)
				{
					getBoard().getActivePlayer().getField().removeMonsterToGraveyard(this);
					getBoard().getOpponentPlayer().getField().removeMonsterToGraveyard(monster);
				}
				else
				{
					activePoints+=(this.attackPoints-monster.attackPoints);
					getBoard().getActivePlayer().getField().removeMonsterToGraveyard(this);
				}
			}
			else
			{
				if(this.attackPoints>monster.defensePoints)
					getBoard().getOpponentPlayer().getField().removeMonsterToGraveyard(monster);
				else if(this.attackPoints<monster.defensePoints)
					activePoints+=(this.attackPoints-monster.defensePoints);
			}
		getBoard().getActivePlayer().setLifePoints(activePoints);
		getBoard().getOpponentPlayer().setLifePoints(opponentPoints);
		if(activePoints<=0)
			getBoard().setWinner(getBoard().getOpponentPlayer());
		if(opponentPoints<=0)
			getBoard().setWinner(getBoard().getActivePlayer());
	}
	
	public void action()
	{
		action(null);
	}

	public boolean isModeChanged() {
		return modeChanged;
	}

	public void setModeChanged(boolean modeChanged) {
		this.modeChanged = modeChanged;
	}
	
	
}
