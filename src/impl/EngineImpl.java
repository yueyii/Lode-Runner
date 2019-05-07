package impl;

import services.Command;
import services.Engine;
import services.Environment;
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
	Item itemdelete;
	Guard guarddelete;
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
	private boolean toDelete;

	public HashMap<CellContent, Pair<Integer, Integer>> cellMap;
	public HashMap<Pair<Integer, Integer>, Integer> holesMap;

	public ArrayList<Pair<Integer,Integer>> guardinitlist;
	public Pair<Integer, Integer> playerinit;
	public ArrayList<Pair<Integer,Integer>> iteminitlist;

	public ArrayList<Guard> guardlist;
	public ArrayList<Item> itemlist;

	private int guardid;
	private int guardposition;
	private int itemposition;

	public EngineImpl() {
		cellMap = new HashMap<>();
		holesMap = new HashMap<>();
		guardlist =new ArrayList<>();
		guardinitlist =new ArrayList<>();
		iteminitlist = new ArrayList<>();
		itemlist = new ArrayList<>();

		guardid=Integer.MAX_VALUE;
		guardposition = Integer.MAX_VALUE;
		itemposition = Integer.MAX_VALUE;
		score = 0; 
		level = 1;
		life = 3;
		//Le temps embouch¨¦ des caract¨¨re
		timeInhole=2;		
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
		item=null;
		itemdelete = null;
		command=null;
		status=null;
		holes =null;
		playerinit = null; 
		toDelete = false;
		toPickUp = false;
		toPutDown =false;
		toDelete = false;
		guardid=Integer.MAX_VALUE;
		guardposition = Integer.MAX_VALUE;
		itemposition = Integer.MAX_VALUE;
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
		//stocker la paire de position initiales du tresor.
		iteminitlist.add(new Pair<Integer, Integer>(item.getItemCol(), item.getItemHgt()));
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
		System.out.println("Restart the game!!!");
		cellMap.clear();
		holesMap.clear();
		guardid=Integer.MAX_VALUE;
		guardposition = Integer.MAX_VALUE;
		itemposition = Integer.MAX_VALUE;
		toFill =false;
		toPickUp = false;
		toPutDown =false;
		toDelete = false;

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
		for (int i = 0; i < itemlist.size(); i++) {
			int wdtI = iteminitlist.get(i).getKey();
			int hgtI = iteminitlist.get(i).getValue();
			itemlist.get(i).init(itemlist.get(i).getEnvi(), wdtI , hgtI ,itemlist.get(i).getItemId(), ItemType.TREASURE);
			cellMap.put((Item)itemlist.get(i), new Pair<Integer,Integer>(wdtI, hgtI));
			System.out.println("Item" +item.getItemId() + " Wdt :"+wdtI+" item Hgt :"+ hgtI);
		}
	}


	public void updatePlayer(Player player) {
		int wdt = player.getWdt();
		int hgt = player.getHgt();

		cellMap.put((Character)player, new Pair<Integer,Integer>(wdt, hgt));
		System.out.println("Player Wdt :"+ player.getWdt()+" player Hgt :"+ player.getHgt());
	}

	public void updateGuard(Guard guard) {
		int wdt = guard.getWdt();
		int hgt = guard.getHgt();

		//parcourir tous les gardes
		for (int i = 0; i < guardlist.size(); i++) {
			//verifier si le garde a deplace 
			if(guard.equals(guardlist.get(i))) {

				//verifier si ce garde a ete rebouche
				if(guardid == guard.getGardeId() && toFill) {	
					//supprimer ce garde dans la liste
					guardlist.remove(i);
					guard.getGuardIdList().remove(i);
					cellMap.remove(guard);			

					//prendre les coordonnees de garde
					wdt = guardinitlist.get(guard.getGardeId()).getKey();
					hgt = guardinitlist.get(guard.getGardeId()).getValue();

					//creer un nouveau garde avec l'ancien id
					guard.init(guard.getEnvi(), wdt , hgt ,guardid);
					//todo il a ete ajoute a la fin de liste 
					guardlist.add(guard);

					//reboucher le trou
					System.out.println("Guard id :"+ guard.getGardeId()+" a ete rebouche");	
					toFill=false;
				}
				//mettre a jour chaque fois les positions des gardes
				cellMap.put((Character)guard, new Pair<Integer,Integer>(wdt, hgt));
				System.out.println("Guard "+guard.getGardeId()+" Wdt :"+ guard.getWdt()+" Hgt :"+ guard.getHgt());
			}
		}
	}

	public void updateItem(Item item) {
		int wdt = item.getItemCol();
		int hgt = item.getItemHgt();

		if (toPickUp) {

			if(item.equals(itemdelete)) {
				//Guand le garde prend le tresor, la position de tresor est egal a la position de garde
				int newwdt = cellMap.get(guarddelete).getKey();
				int newhgt = cellMap.get(guarddelete).getValue();
				cellMap.put((Item)item, new Pair<Integer,Integer>(newwdt, newhgt));
				System.out.println("Item" +item.getItemId() + " Wdt :"+newwdt+" item Hgt :"+ newhgt);

				//si le gardes a depose le tresor
				if(isToPutDown()) {
					int newwdt2 = cellMap.get(guarddelete).getKey();
					int newhgt2 = cellMap.get(guarddelete).getValue()+1;
					cellMap.put((Item)item, new Pair<Integer,Integer>(newwdt2, newhgt2));
					System.out.println("Garde" +guarddelete.getGardeId()+" libere le tresor");
					System.out.println("Item" +item.getItemId() + " Wdt :"+newwdt2+" item Hgt :"+ newhgt2);
					toPickUp=false;
				}
			}
		}
		else {
			cellMap.put((Item)item, new Pair<Integer,Integer>(wdt, hgt));
			System.out.println("Item" +item.getItemId() + " Wdt :"+wdt+" item Hgt :"+ hgt);
		}

		//si le joueur a pris le tresor
		if(toDelete) {
			//parcourir tous les tresors
			for (int i = 0; i < itemlist.size(); i++) {			
				if(itemlist.get(i).equals(itemdelete)) {
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

	@Override 
	public void Step() {
		System.out.println("-----------------------------------");

		if (this.status == Status.Playing) {
			if(NextCommand()!=null) {
				player.Step();
				// Mise a jour la position de player	
				updatePlayer(player);
			}

			Label: 
				//mise a jour les positions des gardes
				for (int i = 0; i < guardlist.size(); i++) {
					guardlist.get(i).Step();

					//si le joueur sur une case contenant un garde, le jeu est perdu
					if (guardlist.get(i).getWdt()==player.getWdt() 
							&& guardlist.get(i).getHgt()==player.getHgt()) {
						System.out.println("Player a ete attape par "+guardlist.get(i).getGardeId());
						//decrementer la vie de joueur
						life--;
						//Reinitialise l'ecran
						restart();

						if(life==0) {
							this.status=Status.Loss;
							System.out.println("Player wdt:"+player.getWdt()+" hgt:"+player.getHgt());
							System.out.println("Status :"+ status + " Score :"+score);
						}
						break Label; 
					}

					//verifier/Mise a jour les status de trous
					if (!holesMap.isEmpty()) {
						for (Entry<Pair<Integer, Integer>, Integer> hole : holesMap.entrySet()) {

							//si le temps de trou est 15
							if(hole.getValue()==timeInhole) {
								//1er cas: si le garde est dans le trou
								if(hole.getKey().getKey() == guardlist.get(i).getWdt() 
										&& hole.getKey().getValue()==guardlist.get(i).getHgt()) {
									//init guard a poision initiale
									guardid = guardlist.get(i).getGardeId();
									toFill=true;
									//reboucher le trou
									holes.getEnvi().Fill(guardlist.get(i).getWdt(),guardlist.get(i).getHgt());
									//supprimer ce trou dans la liste de trou
									holesMap.remove(hole.getKey());
								}

								//2eme cas: si le joueur est dans le trou, la vie de joueur decremente
								else if(hole.getKey().getKey() ==player.getWdt() 
										&& hole.getKey().getValue()==player.getHgt() ) {
									System.out.println("Player a ete rebouche");	
									//decrementer la vie de joueur
									life--;
									//Reinitialise l'ecran
									restart();

									if(life==0) {
										this.status=Status.Loss; 
										System.out.println("Player wdt:"+player.getWdt()+" hgt:"+player.getHgt());
										System.out.println("Status :"+ status + " Score :"+score);
									}
									break Label;
								}
								//si le temps de trou<15, incrementer le temps
							}else if(hole.getValue()<timeInhole) {
								holesMap.put(hole.getKey(), hole.getValue()+1);
							} 
						}
					}
					updateGuard(guardlist.get(i));
				}

			//pour les tresors
			for (int j = 0; j < itemlist.size(); j++) {
				//1er cas: si le joueur sur une case contenant un tresor, tresor disparait
				if (itemlist.get(j).getItemCol()==player.getWdt() 
						&&  itemlist.get(j).getItemHgt()==player.getHgt()) {
					//supperimer le tresor dans la liste de tresors
					toDelete=true;
					itemdelete = itemlist.get(j);
					//incremente la score 
					score++;
					System.out.println("Le joueur se trouve sur une case contenant le tresor");
				}

				//2eme cas: si le garde sur une case contenant un tresor, il prend le tresor
				for (int i = 0; i < guardlist.size(); i++) {
					if (guardlist.get(i).getWdt()==itemlist.get(j).getItemCol()
							&& guardlist.get(i).getHgt()==itemlist.get(j).getItemHgt()) {
						//Stocker l'ordre de garde de la liste
						guarddelete = guardlist.get(i);
						itemdelete = itemlist.get(j);
						toPickUp = true;
						System.out.println("Guard "+i + " prend le tresor "+itemlist.get(j).getItemId());
					}
				}
				updateItem(itemlist.get(j));
			}

			//si il y a plus de tresor
			if(itemlist.size()==0) {
				this.status=Status.Win;
				level ++;
				System.out.println("You won!!! Pass to next game "+ level);
				System.out.println("Player wdt:"+player.getWdt()+" hgt:"+player.getHgt());
				System.out.println("Status :"+ status + " Score :"+score);
			}
		}
	}

	public void control(int nbSteps) {
		//nbstep est le temps d'un tour

		for(int i=0;i<nbSteps;i++) {
			//si le jeu termine, sortir la boucle
			if(getStatus()==Status.Loss || getStatus()==Status.Win) {
				break;
			}

			//sinon, status est playing
			this.status=Status.Playing;
			this.Step();
		}
	}

	@Override
	public Command NextCommand() {
		//todo keyboard listenner
		return command;
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
