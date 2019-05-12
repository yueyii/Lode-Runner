package impl;

import java.util.ArrayList;

import services.Cell;
import services.Character;
import services.Command;
import services.Guard;
import services.Move;
import services.Player;
import services.RequireEngineService;
import services.RequirePlayerService;
import services.Screen;

public class GuardImpl extends CharacterImpl implements Guard, 
RequirePlayerService, RequireEngineService{
	private Player player;
	private int id;
	private int time;
	private int timeLimit=5;
	private Move move;
	private EngineImpl engine;

	public GuardImpl() {
		id=0;
		time=0;
		move=null;
		engine = null;
	}

	@Override
	public void bindPlayerService(Player service) {
		player = service;
	}

	@Override
	public void bindEngineService(EngineImpl service) {
		engine=service;
	}

	@Override
	public void init(Screen screen, int x, int y, int id) {
		this.id=id;
		Ids.setGuardId(id);
		super.init(screen, x, y);
	}

	public ArrayList<Integer> getGuardIdList(){
		return Ids.getGuardIdList();
	}

	@Override
	public int getGardeId() {
		return id;
	}


	@Override
	public Character getTarget() {
		return player;
	}

	@Override
	public Move getBehaviour() {	
		//si le joueur est au dessus de lui
		if(getEnvi().CellNature(getWdt(), getHgt())==Cell.LAD) {
			if(getHgt() < getTarget().getHgt()) {
				this.move=Move.UP;
			}

			//si le joueur est en dessous de lui
			else if(getHgt() > getTarget().getHgt()) {
				this.move=Move.DOWN; 
			}
			else if(getWdt() < getTarget().getWdt()) {
				this.move=Move.RIGHT;
			}

			else if(getWdt() > getTarget().getWdt()) {
				this.move=Move.LEFT;
			}

			else {
				this.move=Move.NEUTRAL;
			}
			return move;
		}
		//si le joueur est a sa gauche

		else if ((getEnvi().CellNature(getWdt(),getHgt())==Cell.HOL
				||getEnvi().CellNature(getWdt(),getHgt())==Cell.HDR 
				|| (getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.PLT
				|| getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.MTL 
				|| getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.LAD))
				|| (!getEnvi().getCellContent(getWdt(),getHgt()-1).isEmpty())) {

			if(getWdt() > getTarget().getWdt()) {
				this.move=Move.LEFT;
			}

			//si le joueur est a sa droite
			else if(getWdt() < getTarget().getWdt()) {	
				this.move=Move.RIGHT;
			}

			else {
				this.move=Move.NEUTRAL;
			}
			return move;
		}


		else if ((getEnvi().CellNature(getWdt(), getHgt())==Cell.LAD)&&
				(getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.PLT
				|| getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.MTL 
				|| getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.LAD
				|| (!getEnvi().getCellContent(getWdt(),getHgt()-1).isEmpty()))) {
			if(getHgt() < getTarget().getHgt()) {
				if((getTarget().getHgt()-getHgt())<Math.abs(getTarget().getWdt()-getWdt())) {
					this.move=Move.UP;
				} 
			}

			//si le joueur est en dessous de lui
			else if(getHgt() > getTarget().getHgt()) {
				if((getHgt()-getTarget().getHgt())<Math.abs(getTarget().getWdt()-getWdt())) {
					this.move=Move.DOWN;
				}
			}

			else if(getWdt() > getTarget().getWdt()) {	
				if((getWdt()-getTarget().getWdt())<Math.abs(getTarget().getHgt()-getHgt())) {
					this.move=Move.LEFT;
				}
			}

			//si le joueur est a sa droite
			else if(getWdt() < getTarget().getWdt()) {			
				if((getTarget().getWdt()-getWdt())<Math.abs(getTarget().getHgt()-getHgt())) {
					this.move=Move.RIGHT;
				}	
			}

			else {
				this.move=Move.NEUTRAL;
			}
		}
		return null;
	}


	@Override
	public int getTimeInhole() {
		return time;
	}

	/** Operateur: 
	 * Wdt!=0 \and Screen::CellNature(Envi(C),Wdt(C)-1,Hgt(C)+1) \not in {MTL,PLT}
	 * 	\and not exists Character c \in Environment::CellContent(Envi(C),Wdt(C)-1,Hgt(C)+1)
	 * 	\implies Wdt(ClimbLeft(C))=Wdt(C)-1 \and Hgt(ClimbLeft(C))=Hgt(C)+1
	 */

	@Override
	public void ClimbLeft() {
		if(getWdt()!=0) {
			if(getEnvi().CellNature(getWdt()-1, getHgt()+1)!=Cell.MTL
					&& getEnvi().CellNature(getWdt()-1, getHgt()+1)!=Cell.PLT) {
				if(getEnvi().getCellContent(getWdt()-1, getHgt()+1).isEmpty())
				{
					this.wdt=this.wdt-1;
					this.hgt=this.hgt+1;
				}
			}
		}
	}

	/** Operateur: 
	 * Wdt!=0 \and Screen::CellNature(Envi(C),Wdt(C)+1,Hgt(C)+1) \not in {MTL,PLT}
	 * 	\and not exists Character c \in Environment::CellContent(Envi(C),Wdt(C)+1,Hgt(C)+1)
	 * 	\implies Wdt(ClimbLeft(C))=Wdt(C)+1 \and Hgt(ClimbLeft(C))=Hgt(C)+1
	 */

	@Override
	public void ClimbRight() { 
		if(getWdt()!=0) {
			if(getEnvi().CellNature(getWdt()+1, getHgt()+1)!=Cell.MTL
					&& getEnvi().CellNature(getWdt()+1, getHgt()+1)!=Cell.PLT) {
				if(getEnvi().getCellContent(getWdt()+1, getHgt()+1).isEmpty())
				{
					this.wdt=this.wdt+1;
					this.hgt=this.hgt+1;
				}
			}
		}

	}

	/** Operateur:definir les predicats cet reutiliser dans
	 * 
	 * GoDown(C) \def (Environment::CellNature(Envi(C),Wdt(C),Hgt(C)-1)) \in {HOL,EMP}
	 * 		\and not exists Character c \in Environment::CellNature(Envi(C),Wdt(C),Hgt(C)-1))
	 * 		\and Environment::CellNature(Envi(C),Wdt(C),Hgt(C)-1)) \not in {LAD, HDR}
	 * 
	 * getTimeHole(G)<5
	 * 	 \and Environnment::CellNature(Envi(G),Wdt(G),Hgt(G))=HOL 
	 * 	 \implies getTimeHole(Step(G))=getTimeHole(G)+1
	 * 
	 * getTimeHole(G)=5
	 * 	 \and Environnment::CellNature(Envi(G),Wdt(G),Hgt(G))=HOL 
	 * 	 \and getBehaviour(G)=Left
	 * 	  \implies Step(G)=ClimbLeft(G)
	 * 
	 * getTimeHole(G)=5
	 * 	 \and Environnment::CellNature(Envi(G),Wdt(G),Hgt(G))=HOL 
	 * 	 \and getBehaviour(G)=Right
	 * 	  \implies Step(G)=ClimbRight(G)
	 * 
	 * getTimeHole(G)=5
	 * 	 \and Environnment::CellNature(Envi(G),Wdt(G),Hgt(G))=HOL 
	 * 	 \and getBehaviour(G)=Neutral
	 * 	  \implies Step(G)=Step(G)@pre
	 * 
	 * Environnment::CellNature(Envi(G),Wdt(G),Hgt(G))!=HOL 
	 * 		\and (getBehaviour(G)=Left  \implies Character::goLeft(Step(C))
	 * 			 \or getBehaviour(G)=Right  \implies Character::goRight(Step(C))
	 * 			 \or getBehaviour(G)=Down  \implies Character::goDown(Step(C))
	 *  		 \or getBehaviour(G)=Up  \implies Character::goUp(Step(C))
	 * */

	@Override
	public void Step() {
		boolean tomber= false;
		if(getEnvi().CellNature(getWdt(), getHgt())!=Cell.HOL){
			if((getEnvi().CellNature(getWdt(), getHgt()-1)==Cell.HOL)
					||(getEnvi().CellNature(getWdt(), getHgt()-1)==Cell.EMP)) {
				if(getEnvi().getCellContent(getWdt(), getHgt()-1).isEmpty()) {
					if(getEnvi().CellNature(getWdt(), getHgt())!=Cell.LAD 
							&& getEnvi().CellNature(getWdt(), getHgt())!=Cell.HDR) {
						System.out.println("Le garde "+getGardeId()+ " tombe");
						super.goDown();
						tomber=true;
						engine.setToPutDown(true);
					}
				}
			}
		}
		if(getEnvi().CellNature(getWdt(), getHgt())==Cell.HOL) {
			if(getTimeInhole()< timeLimit) {	
				this.time=this.time+1;
				System.out.println("temps +1, temps dans le trou est " + getTimeInhole());
			} 

			else if(getTimeInhole()==timeLimit) {
				if(getBehaviour()==Move.LEFT) {
					ClimbLeft();
					System.out.println("Le garde "+getGardeId()+" grimbe a gauche");
				}
				else if(getBehaviour()==Move.RIGHT) {
					ClimbRight();
					System.out.println("Le garde "+getGardeId()+" grimbe a droite");
				}
				else if(getBehaviour()==Move.NEUTRAL) {
					System.out.println("Le garde "+getGardeId()+" ne bouge pas");
				}
			}
		}
 
		else if(tomber==false){
			if(getBehaviour()==Move.LEFT) {
				super.goLeft();
				System.out.println("Le garde "+getGardeId()+" marche vers gauche");
			}
			else if(getBehaviour()==Move.RIGHT) {
				super.goRight();
				System.out.println("Le garde "+getGardeId()+" marche vers droite");
			}
			else if(getBehaviour()==Move.UP) {
				super.goUp();
				System.out.println("Le garde "+getGardeId()+" monte");
			}
			else if(getBehaviour()==Move.DOWN) {
				super.goDown();
				System.out.println("Le garde "+getGardeId()+" descend");
			}
			else if(getBehaviour()==Move.NEUTRAL) {
				System.out.println("Le garde "+getGardeId()+" ne bouge pas");
			}
		}

	}

	@Override
	public Command NextCommand() {
		return null;
	}


}
