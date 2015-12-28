package eg.edu.guc.yugioh.board.player;

import java.io.*;
import java.util.*;

import eg.edu.guc.yugioh.cards.*;
import eg.edu.guc.yugioh.cards.spells.*;
import eg.edu.guc.yugioh.exceptions.*;




public class Deck {
	private static ArrayList<Card> monsters;
	private static ArrayList<Card> spells;
	private ArrayList<Card> deck;
	private static String monstersPath = "Database-Monsters.csv";
	private static String spellsPath = "Database-Spells.csv";
	private boolean loadingMonsters=true;
	
	public Deck() throws IOException, UnexpectedFormatException
	{
		
		if(monsters==null || spells ==null)
		{
			    monsters = loadCardsFromFileHandler(monstersPath);
			    loadingMonsters=false;
				spells = loadCardsFromFileHandler(spellsPath);
		}
		buildDeck(monsters, spells);
		shuffleDeck();
	}
	
	public ArrayList<Card> loadCardsFromFileHandler(String path) throws IOException, UnexpectedFormatException
	
	{
		Scanner sc = new Scanner(System.in);
		for(int i =1; i<=3;i++)
		{
			try
			{
				return loadCardsFromFile(path);
			}
			catch(FileNotFoundException e)
			{
				if(i!=1)
					System.out.println("The File was not found");
				System.out.print("Please enter a correct path: ");
				path = sc.nextLine();
			}
			catch(UnexpectedFormatException e)
			{
				System.out.print("Please enter a correct path: ");
				path = sc.nextLine();
			}
			
		}
		
		
		sc.close();
		return loadCardsFromFile(path);
	}
	
	public ArrayList<Card> getDeck()
	{
		return this.deck;
	}
	
	public static int getEmptyField(String [] st)
	{
		for(int i =0; i<st.length;i++)
			if(st[i].length()==0 || st[i].equals(" "))
				return i+1;
		return 0;
	}
	
	public ArrayList<Card> loadCardsFromFile(String path) throws IOException, UnexpectedFormatException
	{
		ArrayList<Card> cards = new ArrayList<Card>(30);
		BufferedReader br = new BufferedReader(new FileReader(path));
		int line = 1;
		while(br.ready())
		{ 
			String [] st = br.readLine().split(",");
			if((!(st[0].equals("Spell"))) && (!(st[0].equals("Monster"))))
				throw new UnknownCardTypeException(path, line, st[0]);
			
			if(loadingMonsters && st.length!=6 || !loadingMonsters && st.length!=3)
				throw new MissingFieldException(path, line);
			
			int sourceField = getEmptyField(st);
			if(sourceField!=0)
				throw new EmptyFieldException(path, line, sourceField);
			
			if(st[0].equals("Spell"))
			{
				String name = st[1];
				String des = st[2];
				switch(name)
				{
				case "Card Destruction": cards.add(new CardDestruction(name,des));break;
				case "Change Of Heart": cards.add(new ChangeOfHeart(name,des));break;
				case "Dark Hole": cards.add(new DarkHole(name,des));break;
				case "Graceful Dice": cards.add(new GracefulDice(name,des));break;
				case "Harpie's Feather Duster": cards.add(new HarpieFeatherDuster(name,des));break;
				case "Heavy Storm": cards.add(new HeavyStorm(name,des));break;
				case "Mage Power": cards.add(new MagePower(name,des));break;
				case "Monster Reborn": cards.add(new MonsterReborn(name,des));break;
				case "Pot of Greed": cards.add(new PotOfGreed(name,des));break;
				case "Raigeki": cards.add(new Raigeki(name,des));break;
				default : throw new UnknownSpellCardException(path, line, name);
				}
				
			}
			else
			{
				String name = st[1];
				String description = st[2];
				int attack = Integer.parseInt(st[3]);
				int defence = Integer.parseInt(st[4]);
				int level = Integer.parseInt(st[5]);
				cards.add(new MonsterCard(name, description, level, attack, defence));
			}
			line++;
		}
		br.close();
		return cards;
	}
	
	private void buildDeck(ArrayList<Card> monsters, ArrayList<Card> spells)
	{
		deck = new ArrayList<Card> (20);
		for(int i = 0; i < 15; i++)
		{
			MonsterCard monster = (MonsterCard)((MonsterCard)(monsters.get((new Random()).nextInt(monsters.size())))).clone();
			deck.add(monster);
		}
		for(int i = 0; i < 5; i++)
		{
			SpellCard spell = (SpellCard)((SpellCard)(spells.get((new Random()).nextInt(spells.size())))).clone();
			deck.add(spell);
		}
	}
	
	private void shuffleDeck()
	{
		Collections.shuffle(this.deck);
	}
	
	public ArrayList<Card> drawNCards(int n)
	{
		ArrayList<Card> draw = new ArrayList<Card> (n);
		for(int i = 0; i<n; i++)
			{
			Card m=drawOneCard();
			if(m==null)
				return null;
			draw.add(m);
			}
		return draw;
	}
	
	public Card drawOneCard()
	{
		if(deck.isEmpty())
		{
			Card.getBoard().setWinner(Card.getBoard().getOpponentPlayer());
			return null;
		}
		return deck.remove(deck.size()-1);
	}

	public static ArrayList<Card> getMonsters() {
		return monsters;
	}

	public static ArrayList<Card> getSpells() {
		return spells;
	}
	
	public static String getMonstersPath() {
		return monstersPath;
	}

	public static void setMonstersPath(String monstersPath) {
		Deck.monstersPath = monstersPath;
	}

	public static String getSpellsPath() {
		return spellsPath;
	}

	public static void setSpellsPath(String spellsPath) {
		Deck.spellsPath = spellsPath;
	}
	

}
