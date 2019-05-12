package impl;

import services.Command;
import services.Engine;
import services.Guard;
import services.Holes;
import services.Item;
import services.ItemType;
import services.Player;
import services.RequireGuardService;
import services.RequireHolesService;
import services.RequireItemService;
import services.RequirePlayerService;
import services.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javafx.util.Pair;
import services.CellContent;
import services.Character;

public class EngineImpl implements  
Engine, 
RequirePlayerService,
RequireGuardService,
RequireItemService,
RequireHolesService{

	Character character;
	Player player;
	Guard guard;
	Item item;
	Item itemdeletePlayer;
	Item itemdeleteGuard;	
	Item itemdeleteArms;
	Item itemdeleteSuper;
	Guard guarddelete;
	Guard guardFight;
	Guard guarddeleteSuper;
	Command command;
	Status status;
	Holes holes;
	private int score;
	private int life;
	private int level;
	private final int timeInhole;
	private boolean toFill;
	private boolean toPickUp;
	private boolean toPutDown;
	private boolean toPutDownTresor;
	private boolean toFight;
	private boolean toDelete;
	private boolean isSuper;
	private int itemid;
	private int guardid;
	private int itemTea;

	public HashMap<CellContent, Pair<Integer, Integer>> cellMap;
	public HashMap<Pair<Integer, Integer>, Integer> holesMap;

	public ArrayList<Pair<Integer,Integer>> guardinitlist;
	public Pair<Integer, Integer> playerinit;
	public Pair<Integer, Integer> playerattack;
	public Pair<Integer, Integer> attackitem;
	public ArrayList<Pair<Integer,Integer>> iteminitlist;

	public ArrayList<Guard> guardlist;
	public ArrayList<Item> itemlist;

	public EngineImpl() {
		cellMap = new HashMap<>();
		holesMap = new HashMap<>();
		guardlist =new ArrayList<>();
		guardinitlist =new ArrayList<>();
		iteminitlist = new ArrayList<>();
		itemlist = new ArrayList<>();

		guardid=Integer.MAX_VALUE;
		score = 0; 
		level = 1;
		life = 3;
		timeInhole=4;		
	}

	public void init() {
		cellMap.clear();
		holesMap.clear();
		guardlist.clear();
		guardinitlist.clear();
		iteminitlist.clear();
		itemlist.clear();
		character=null;
		player=null;
		guard= null;
		guarddelete=null;
		guardFight=null;
		item=null;
		itemdeletePlayer = null;
		itemdeleteGuard = null;
		command=null;
		status=null;
		holes =null;
		playerinit = null; 
		toDelete = false;
		toPickUp = false;
		toPutDown =false;
		toPutDownTresor =false;
		toDelete = false;
		isSuper= false;
		guardid=Integer.MAX_VALUE;
		score = 0; 
		life = 3;
	}

	//renvoyer liste de cellcontent a environment
	public  HashMap<CellContent, Pair<Integer, Integer>> getCellContent() {
		return cellMap;
	}

	@Override
	public void bindGuardService(Guard service) {
		guard = service;
		//ajouter ce garde dans une liste  
		guardlist.add(guard);
		//ajouter la paire de positions initiales de ce garde dans une liste
		guardinitlist.add(new Pair<Integer,Integer>(guard.getWdt(), guard.getHgt()));
		updateGuard(guard);
	}

	@Override
	public void bindPlayerService(Player service) {
		player=service;
		//stocker les coordonnees initiales de player
		playerinit  = new Pair<Integer, Integer>(player.getWdt(), player.getHgt());
		//stocker la paire de position initiales du joueur.
		updatePlayer(player);
	}

	@Override
	public void bindItemService(Item service) {
		item = service;
		itemlist.add(item);
		if(item.getNature()==ItemType.TREASURE) {
			itemTea++;
		}
		//stocker la paire de position initiales du tresor.
		iteminitlist.add(new Pair<Integer, Integer>(item.getItemCol(), item.getItemHgt()));
		System.out.println("Item" +item.getItemId() + " Wdt :"+ item.getItemCol()+" Hgt :"+ item.getItemHgt());
		updateItem(item);
	}

	@Override
	public void bindHolesService(Holes service) {
		holes = service;
		updateHole(holes);
		System.out.println("Hole Wdt :"+holes.getHoleCol()+" Hole Hgt :"+holes.getHoleHgt());
	}

	public void restart() {
		//reinitisaliser
		System.out.println("!!! Recommencer le jeu !!!");
		System.out.println("Status :"+ status + " Score :"+score + " Life :"+life);
		cellMap.clear();
		holesMap.clear();
		guardid=Integer.MAX_VALUE;
		itemlist.clear();
		toFill =false;
		toPickUp = false;
		toPutDown =false;
		toDelete = false;
		toFight = false;

		int wdtP = playerinit.getKey();
		int	hgtP = playerinit.getValue();
		player.init(player.getEnvi(), wdtP, hgtP);
		cellMap.put((Character)player, new Pair<Integer,Integer>(wdtP, hgtP));
		System.out.println("Player Wdt :"+ player.getWdt()+" player Hgt :"+ player.getHgt());

		for (int i = 0; i <guardinitlist.size(); i++) {
			//on ne modifie pas guardlist
			int wdtG = guardinitlist.get(i).getKey();
			int hgtG = guardinitlist.get(i).getValue();
			//creer un nouveau garde avec l'ancien id
			guardlist.get(i).init(guardlist.get(i).getEnvi(), wdtG , hgtG ,guardlist.get(i).getGardeId());
			cellMap.put((Character)guardlist.get(i), new Pair<Integer,Integer>(wdtP, hgtP));
			System.out.println("Guard "+guardlist.get(i).getGardeId()+" Wdt :"+ guardlist.get(i).getWdt()+" Hgt :"+ guardlist.get(i).getHgt());
		}

		//reinitiliser tous les positions des items
		for (int i = 0; i < iteminitlist.size(); i++) {
			int wdtI = iteminitlist.get(i).getKey();
			int hgtI = iteminitlist.get(i).getValue();
			item.init(player.getEnvi(), wdtI, hgtI, i, ItemType.TREASURE);
			itemlist.add(item);
			cellMap.put((Item)item, new Pair<Integer,Integer>(wdtI, hgtI));
			System.out.println("Item" +itemlist.get(i).getItemId() + " Wdt :"+wdtI+" item Hgt :"+ hgtI);
		}
	}

	public void updatePlayer(Player player) {
		int wdt = player.getWdt();
		int hgt = player.getHgt();

		cellMap.put((Character)player, new Pair<Integer,Integer>(wdt, hgt));
		System.out.println("Player Wdt :"+ player.getWdt()+" player Hgt :"+ player.getHgt());
	}

	//Mettre à jour tous les gardes
	public void updateGuard(Guard guard) {
		int wdt = guard.getWdt();
		int hgt = guard.getHgt();

		//parcourir tous les gardes
		for (int i = 0; i < guardlist.size(); i++) {
			//verifier si le garde a deplace 
			if(guard.equals(guardlist.get(i))) {

				//verifier si ce garde a ete rebouche
				if(guardid == guard.getGardeId() && toFill) {	
					cellMap.remove(guard);			

					//reboucher le trou
					holes.getEnvi().Fill(guard.getWdt(),guard.getHgt());
					//prendre les coordonnees de garde
					wdt = guardinitlist.get(i).getKey();
					hgt = guardinitlist.get(i).getValue();

					//creer un nouveau garde avec l'ancien id
					guard.init(guard.getEnvi(), wdt , hgt ,guardid);

					//supprimer ce garde dans la liste
					guardlist.remove(i);
					guard.getGuardIdList().remove(i);
					guardlist.add(guard);

					//reboucher le trou
					System.out.println("Guard id :"+ guard.getGardeId()+" a ete rebouche");	
					toFill=false;
				}

				//verifier si ce garde a ete attaque
				else if(guardFight == guard && toFight) {	

					cellMap.remove(guard);			

					//prendre les coordonnees de garde
					wdt = guardinitlist.get(i).getKey();
					hgt = guardinitlist.get(i).getValue();

					//creer un nouveau garde avec l'ancien id
					guard.init(guard.getEnvi(), wdt , hgt ,guard.getGardeId());

					//supprimer ce garde dans la liste
					guardlist.remove(i);
					guard.getGuardIdList().remove(i);
					//todo il a ete ajoute a la fin de liste 
					guardlist.add(guard);

					//reboucher le trou
					System.out.println("Guard id :"+ guard.getGardeId()+" a ete attaque");	
					toFight=false;
				}

				//verifier si ce garde a ete attaque
				else if(guarddeleteSuper == guard && isSuper) {	

					cellMap.remove(guard);			

					//prendre les coordonnees de garde
					wdt = guardinitlist.get(i).getKey();
					hgt = guardinitlist.get(i).getValue();

					//creer un nouveau garde avec l'ancien id
					guard.init(guard.getEnvi(), wdt , hgt ,guard.getGardeId());

					//supprimer ce garde dans la liste
					guardlist.remove(i);
					guard.getGuardIdList().remove(i);
					//todo il a ete ajoute a la fin de liste 
					guardlist.add(guard);

					//reboucher le trou
					System.out.println("Guard id :"+ guard.getGardeId()+" a ete attaque le joueur en mode super");	
					System.out.println("Score :"+score + " Life :"+life);
					isSuper=false;
				}

				//mettre a jour chaque fois les positions des gardes
				cellMap.put((Character)guard, new Pair<Integer,Integer>(wdt, hgt));
				System.out.println("Guard "+guard.getGardeId()+" Wdt :"+ guard.getWdt()+" Hgt :"+ guard.getHgt());
			}
		}
	}

	//Mettre a jour tous les items
	public void updateItem(Item item) {
		int wdt = item.getItemCol();
		int hgt = item.getItemHgt();

		//1er cas, si le garde prend le tresor
		if (toPickUp) {
			if(item.equals(itemdeleteGuard)) {
				//Guand le garde prend le tresor, la position de tresor est egal a la position de garde
				int newwdt = cellMap.get(guarddelete).getKey();
				int newhgt = cellMap.get(guarddelete).getValue();
				cellMap.put((Item)item, new Pair<Integer,Integer>(newwdt, newhgt));
				//supprimer ancien position
				itemlist.remove(itemid);
				item.init(player.getEnvi(), newwdt, newhgt, itemdeleteGuard.getItemId(), itemdeleteGuard.getNature());
				itemlist.add(item);	

				//2eme cas : si le gardes a depose le tresor
				if(toPutDown) {
					int newwdt2 = cellMap.get(guarddelete).getKey();
					int newhgt2 = cellMap.get(guarddelete).getValue()+1;
					cellMap.put((Item)item, new Pair<Integer,Integer>(newwdt, newhgt));
					//supprimer ancien position
					itemlist.remove(itemid);
					item.init(player.getEnvi(), newwdt2, newhgt2, itemdeleteGuard.getItemId(), itemdeleteGuard.getNature());
					itemlist.add(item);	
					System.out.println("Garde" +guarddelete.getGardeId()+" libere le tresor");
					System.out.println("Item" +item.getItemId() + " Wdt :"+newwdt2+" item Hgt :"+ newhgt2);
					toPickUp=false;
				}
				else if(toPutDownTresor) { 
						int newwdt2 = playerattack.getKey();
						int newhgt2 = playerattack.getValue();
						cellMap.put((Item)item, new Pair<Integer,Integer>(newwdt, newhgt));
						//supprimer ancien position
						itemlist.remove(itemid);
						item.init(player.getEnvi(), newwdt2, newhgt2, itemdeleteGuard.getItemId(), itemdeleteGuard.getNature());
						itemlist.add(item);	
						System.out.println("Garde" +guardFight.getGardeId()+" a ete attaque,il libere le tresor");
						System.out.println("Item" +item.getItemId() + " Wdt :"+newwdt2+" item Hgt :"+ newhgt2);
						toPickUp=false;
						toPutDownTresor =false;
					}
				}
		}
		// Mettre a jour tous les CellContent
		else {
			cellMap.put((Item)item, new Pair<Integer,Integer>(wdt, hgt));
		}

		//si le joueur a pris le tresor
		if(toDelete) {
			//parcourir tous les tresors, supprimer les items qui n'existent plus
			for (int i = 0; i < itemlist.size(); i++) {			
				if(itemlist.get(i).equals(itemdeletePlayer)) {
					itemlist.remove(i);	
					itemTea--;
				}

				else if(itemlist.get(i).equals(itemdeleteArms)) {
					itemlist.remove(i);	
				}

				else if(itemlist.get(i).equals(itemdeleteSuper)) {
					itemlist.remove(i);	
				}
				toDelete=false;
			}
		}

	}

	public void updateHole(Holes hole) {
		int wdt = holes.getHoleCol();
		int hgt= holes.getHoleHgt();
		holesMap.put(new Pair<Integer, Integer>(wdt, hgt), holes.getTime());
	}

	//Verifier le status de chaque item
	public Item checkStatusItem(Item item) {
		//si la position d'item = la position de joueur 
		if (item.getItemCol()==player.getWdt() 
				&&  item.getItemHgt()==player.getHgt()) {
			//1er cas : si le joueur sur une case contenant un tresor, tresor disparait
			if(toPickUp==false &&  item.getNature()==ItemType.TREASURE) {
				//supperimer le tresor dans la liste de tresors
				toDelete=true;
				itemdeletePlayer = item;
				//incremente la score 
				score++;
				System.out.println("Le joueur se trouve le tresor"+item.getItemId() + " Wdt :"+item.getItemCol()+" Hgt :"+item.getItemHgt());
			}

			//2eme cas: si le joueur sur une case contenant une arme, il la prend
			else if(item.getNature()==ItemType.ARMS){
				//il peut attaquer les gardes
				toFight=true;
				//supperimer cet arme dans la liste de tresors
				toDelete = true;
				itemdeleteArms = item;
				System.out.println("Le joueur prend une arme"+item.getItemId());
			}

			//3eme cas: si le joueur sur une case contenant item super 
			else if(item.getNature()==ItemType.SUPER){

				//il est au mode super
				isSuper = true;
				//supperimer cet arme dans la liste de tresors
				toDelete= true;
				itemdeleteSuper = item;
				System.out.println("Le joueur prend item Super"+item.getItemId());
			}
		}

		//4eme cas: si le garde sur une case contenant un tresor, il prend le tresor
		for (int i = 0; i < guardlist.size(); i++) {
			if (guardlist.get(i).getWdt()==item.getItemCol()
					&& guardlist.get(i).getHgt()==item.getItemHgt()) {

				guarddelete = guardlist.get(i);
				//supperimer cet tresor dans la liste de tresors
				itemdeleteGuard = item;
				itemid=i;
				toPickUp = true;
				System.out.println("Guard "+ i + " prend le tresor "+item.getItemId() + " Wdt :"+item.getItemCol()+ "Hgt :"+item.getItemHgt());
			}
		}

		return item;
	}

	public Guard checkStatusGuard(Guard guard) {
		//1) si le joueur attaque le garde, ne perde pas la vie, garde reinitialise
		if(isPrepareToFight()) { 
			if(playerattack!=null) {
				if(guard.getWdt() == playerattack.getKey()  
						&& guard.getHgt()== playerattack.getValue()) {	
					System.out.println("Player attaque "+guard.getGardeId());
					guardFight = guard;
					toPutDownTresor=true;
				}
			}
		}

		//2) si le joueur sur une case contenant un garde
		if (guard.getWdt()==player.getWdt() 
				&& guard.getHgt()==player.getHgt()) {

			System.out.println("Player a ete attrape par "+guard.getGardeId());
			//1er cas si le joueur dans le mode super, il ne decremente la vie
			if(isSuper) {
				guarddeleteSuper = guard;
				toPutDownTresor=true;
				//stocker position de item
				attackitem = new Pair<Integer, Integer>(player.getWdt(), player.getHgt());
			}

			//2eme cas, si la vie de joueur est 0, la jeu est perdu 
			else if(life==0) {
				this.status=Status.Loss;
				System.out.println("Tu a perdu!!!");
				System.out.println("Player wdt:"+player.getWdt()+" hgt:"+player.getHgt());
				System.out.println("Status :"+ status + " Score :"+score + " Life :"+life);		
				return guard;
			}

			//3eme cas, si la vie de joueur est > que 0, decrementer la vie
			else{
				life--;
				//Reinitialise l'ecran
				restart();
			}
			return guard;
		}

		//3) Verifier/Mise a jour les status de trous
		if (!holesMap.isEmpty()) {
			for (Entry<Pair<Integer, Integer>, Integer> hole : holesMap.entrySet()) { 

				//1er cas: si le garde est dans le trou
				if(hole.getKey().getKey() == guard.getWdt() 
						&& hole.getKey().getValue()==guard.getHgt()) {
					//la garde libere le tresor
					toPutDown=true; 

					//1.1) si le temps de trou == timeInHole
					if(hole.getValue()==timeInhole) {
						//init guard a poision initiale
						guardid = guard.getGardeId();
						toFill=true;

						//supprimer ce trou dans la liste de trou
						holesMap.remove(hole.getKey());
					}

					//1.2) si le temps de trou est < timeInHole
					else if(hole.getValue()<timeInhole) {
						holesMap.put(hole.getKey(), hole.getValue()+1);
					} 
				}

				//2eme cas: si le joueur est dans le trou, la vie de joueur decremente
				else if(hole.getKey().getKey() ==player.getWdt() 
						&& hole.getKey().getValue()==player.getHgt() ) {

					//2.1) si le temps de trou == timeInHole
					if(hole.getValue()==timeInhole) {
						System.out.println("Player a ete rebouche");	
						//decrementer la vie de joueur
						life--;
						//2.1.1)si la vie==0, le jeu est perdu
						if(life==0) {
							this.status=Status.Loss; 
							System.out.println("Tu a perdu!!!");
							System.out.println("Player wdt:"+player.getWdt()+" hgt:"+player.getHgt());
							System.out.println("Status :"+ status + " Score :"+score + " Life :"+life);
							return guard;
						}

						//2.1.2) si la vie > 0
						else {
							//Reinitialise l'ecran
							restart();
						}
					}
					//2.2) si le temps de trou est < timeInHole
					else if(hole.getValue()<timeInhole) {
						holesMap.put(hole.getKey(), hole.getValue()+1);
					} 
					return guard;
				}
			}
		}
		return guard;
	}

	@Override 
	public void Step() {
		System.out.println("-----------------------------------");	
		if (this.status == Status.Playing) {
			if(NextCommand()!=null) {
				player.Step();
				// Mise a jour la position de player	
				updatePlayer(player);
			}

			//Mise a jour les positions des gardes
			for (int i = 0; i < guardlist.size(); i++) {
				guardlist.get(i).Step();
				updateGuard(checkStatusGuard(guardlist.get(i)));
			}

			//Mise à jour les tresors
			for (int j = 0; j < itemlist.size(); j++) {
				updateItem(checkStatusItem(itemlist.get(j)));
			}

			//si il y a plus de tresor
			if(itemTea==0) {
				this.status=Status.Win;
				level ++;
			}
		}
	}

	public void control(int nbSteps) {
		//nbstep est le temps d'un tour

		for(int i=0;i<nbSteps;i++) {
			//si le jeu termine, sortir la boucle
			if(this.status == Status.Loss || this.status == Status.Win){
				break;
			}

			//sinon, status est playing
			this.status=Status.Playing;
			this.Step();
		}
	}

	public int getTimeLimitInHole() {
		return timeInhole;
	}

	@Override
	public Command NextCommand() {
		return command;
	} 

	public void prepareToFight(int x, int y) {
		playerattack = new Pair<Integer, Integer>(x, y);
	}


	public boolean isPrepareToFight() {
		return toFight;
	}
	public Command setCommand(Command command) {
		return this.command=command;
	}

	public Status getStatus() {
		return status;
	}

	public int getscore() {
		return score;
	}

	public int getlevel() {
		return level;
	}

	public boolean isToPutDown() {
		return toPutDown;
	}

	public void setToPutDown(boolean toPutDown) {
		this.toPutDown = toPutDown;
	}
}