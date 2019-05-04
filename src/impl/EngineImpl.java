package impl;

import services.Command;
import services.Engine;
import services.Guard;
import services.Holes;
import services.Item;
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
	Command command;
	Status status;
	Holes holes;
	private int score;
	private boolean toFill =false;
	private boolean toPickUp = false;
	private boolean toPutDown =false;

	private boolean toDelete = false;
	public HashMap<CellContent, Pair<Integer, Integer>> cellMap;
	public HashMap<Pair<Integer, Integer>, Integer> holesMap;
	public ArrayList<Pair<Integer,Integer>> guardinitlist;
	public ArrayList<Guard> guardlist;
	public ArrayList<Item> itemlist;
	private int guardid=9999;
	private int guardposition = 9999;
	private int itemposition = 9999;

	public EngineImpl() {
		cellMap = new HashMap<>();
		holesMap = new HashMap<>();
		guardlist =new ArrayList<>();
		guardinitlist =new ArrayList<>();
		itemlist = new ArrayList<>();
		score = 0; 
	}

	//renvoyer liste de cellcontent ¨¤ environment
	public  HashMap<CellContent, Pair<Integer, Integer>> getCellContent() {
		return cellMap;
	}

	@Override
	public Command NextCommand() {
		//todo keyboard listenner
		return command;
	} 

	public Command setCommand(Command command) {
		return this.command=command;
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
		updatePlayer(player);
	}

	@Override
	public void bindItemService(Item service) {
		item = service;
		itemlist.add(item);
		updateItem(item);
	}

	@Override
	public void bindHolesService(Holes service) {
		holes = service;
		updateHole(holes);
		System.out.println("Hole Wdt :"+holes.getHoleCol()+" Hole Hgt :"+holes.getHoleHgt());
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
			//v¨¦rifier si le garde a d¨¦plac¨¦ 
			if(guard.equals(guardlist.get(i))) {

				//v¨¦rifier si ce garde a ¨¦t¨¦ rebouch¨¦
				if(guardid == guard.getGardeId() && toFill) {	
					//supprimer ce garde dans la liste
					guardlist.remove(i);
					guard.getGuardIdList().remove(i);
					cellMap.remove(guard);			

					//prendre les coordonn¨¦es de garde
					wdt = guardinitlist.get(guard.getGardeId()).getKey();
					hgt = guardinitlist.get(guard.getGardeId()).getValue();

					//cr¨¦er un nouveau garde avec l'ancien id
					guard.init(guard.getEnvi(), wdt , hgt ,guardid);
					//todo il a ¨¦t¨¦ ajout¨¦ ¨¤ la fin de liste 
					guardlist.add(guard);

					//reboucher le trou
					System.out.println("Guard id :"+ guard.getGardeId()+" a ¨¦t¨¦ rebouch¨¦");	
					toFill=false;
				}
				//mettre ¨¤ jour chaque fois les positions des gardes
				cellMap.put((Character)guard, new Pair<Integer,Integer>(wdt, hgt));
				System.out.println("Guard "+guard.getGardeId()+" Wdt :"+ guard.getWdt()+" Hgt :"+ guard.getHgt());
			}
		}
	}

	public void updateItem(Item item) {
		int wdt = item.getItemCol();
		int hgt = item.getItemHgt();

		if (toPickUp) {
			if(item.getItemId()==itemposition) {
				//Guand le garde prend le tr¨¦sor, la position de tr¨¦sor est ¨¦gal ¨¤ la position de garde
				int newwdt = cellMap.get(guardlist.get(guardposition)).getKey();
				int newhgt = cellMap.get(guardlist.get(guardposition)).getValue();
				cellMap.put((Item)item, new Pair<Integer,Integer>(newwdt, newhgt));
				System.out.println("Item" +item.getItemId() + " Wdt :"+newwdt+" item Hgt :"+ newhgt);
				
				//si le gardes a d¨¦pos¨¦ le tr¨¦sor
				if(isToPutDown()) {
					int newwdt2 = cellMap.get(guardlist.get(guardposition)).getKey();
					int newhgt2 = cellMap.get(guardlist.get(guardposition)).getValue()+1;
					cellMap.put((Item)item, new Pair<Integer,Integer>(newwdt2, newhgt2));
					System.out.println("Garde" +guardlist.get(guardposition).getGardeId()+" lib¨¨re le tr¨¦sor");
					System.out.println("Item" +item.getItemId() + " Wdt :"+newwdt2+" item Hgt :"+ newhgt2);
					toPickUp=false;
				}
			}else {
				cellMap.put((Item)item, new Pair<Integer,Integer>(wdt, hgt));
				System.out.println("Item" +item.getItemId() + " Wdt :"+wdt+" item Hgt :"+ hgt);
			}
		}
		
		//si le joueur a pris le tr¨¦sor
		if(toDelete) {
			//parcourir tous les tr¨¦sors
			for (int i = 0; i < itemlist.size(); i++) {			
				if(itemlist.contains(item)) {
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
				// Mise ¨¤ jour la position de player	
				updatePlayer(player);
			}

			Label: 
				//mise ¨¤ jour les positions des gardes
				for (int i = 0; i < guardlist.size(); i++) {
					guardlist.get(i).Step();

					//si le joueur sur une case contenant un garde, le jeu est perdu
					if (guardlist.get(i).getWdt()==player.getWdt() 
							&& guardlist.get(i).getHgt()==player.getHgt()) {
						this.status=Status.Loss; 
						System.out.println("Player a ¨¦t¨¦ attap¨¦ par "+guardlist.get(i).getGardeId());
						break Label;
					}

					//v¨¦rifier/Mise ¨¤ jour les status de trous
					if (!holesMap.isEmpty()) {
						for (Entry<Pair<Integer, Integer>, Integer> hole : holesMap.entrySet()) {

							//si le temps de trou est 15
							if(hole.getValue()==15) {
								//1er cas: si le garde est dans le trou
								if(hole.getKey().getKey() == guardlist.get(i).getWdt() 
										&& hole.getKey().getValue()==guardlist.get(i).getHgt()) {
									//init guard ¨¤ poision initiale
									guardid = guardlist.get(i).getGardeId();
									toFill=true;
									//reboucher le trou
									holes.getEnvi().Fill(guardlist.get(i).getWdt(),guardlist.get(i).getHgt());
									//supprimer ce trou dans la liste de trou
									holesMap.remove(hole.getKey());
								}

								//2eme cas: si le joueur est dans le trou, le jeu est perdu
								else if(hole.getKey().getKey() ==player.getWdt() 
										&& hole.getKey().getValue()==player.getHgt() ) {
									this.status=Status.Loss;
									System.out.println("Player a ¨¦t¨¦ rebouch¨¦");	
									break Label;
								}
								//si le temps de trou<15, incrementer le temps
							}else if(hole.getValue()<15) {
								holesMap.put(hole.getKey(), hole.getValue()+1);
							} 
						}
					}
					updateGuard(guardlist.get(i));
				}

			//pour les tr¨¦sors
			for (int j = 0; j < itemlist.size(); j++) {
				//1er cas: si le joueur sur une case contenant un tr¨¦sor, tr¨¦sor disparait
				if (itemlist.get(j).getItemCol()==player.getWdt() 
						&&  itemlist.get(j).getItemHgt()==player.getHgt()) {
					//supperimer le tr¨¦sor dans la liste de tr¨¦sors
					toDelete=true;
					//incremente la score 
					score++;
					System.out.println("Le joueur se trouve sur une case contenant le tr¨¦sor");
				}

				//2¨¨me cas: si le garde sur une case contenant un tr¨¦sor, il prend le tr¨¦sor
				for (int i = 0; i < guardlist.size(); i++) {
					if (guardlist.get(i).getWdt()==itemlist.get(j).getItemCol()
							&& guardlist.get(i).getHgt()==itemlist.get(j).getItemHgt()) {
						//Stocker l'ordre de garde de la liste
						guardposition = i;
						itemposition = j;
						toPickUp = true;
						System.out.println("Guard "+i + " prend le tr¨¦sor "+itemlist.get(j).getItemId());
					}
				}
				updateItem(itemlist.get(j));
			}

			//si il y a plus de tr¨¦sor
			if(itemlist.size()==0) {
				this.status=Status.Win;
			}
		}
	}

	public Status getStatus() {
		return status;
	}

	public int getscore() {
		return score;
	}

	public void control(int nbSteps) {
		//nbstep est le temps d'un tour
		
		for(int i=0;i<nbSteps;i++) {
			//si le jeu termine, sortir la boucle
			if(this.status==Status.Loss || this.status==Status.Win) {
				System.out.println("Player wdt:"+player.getWdt()+" hgt:"+player.getHgt());
				System.out.println("Status :"+ status + " Score :"+score);
				break;
			}

			//sinon, status est playing
			this.status=Status.Playing;
			this.Step();
		}
	}

	public boolean isToPutDown() {
		return toPutDown;
	}

	public void setToPutDown(boolean toPutDown) {
		this.toPutDown = toPutDown;
	}
}
