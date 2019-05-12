package impl;
import services.Cell;
import services.Command;
import services.Holes;
import services.Player;
import services.RequireEngineService;

public class PlayerImpl extends CharacterImpl  implements 
Player, 
RequireEngineService{

	private EngineImpl engine;

	public PlayerImpl() {
		engine = null; 
	}

	@Override
	public void bindEngineService(EngineImpl service) {
		engine = service;
	}

	@Override
	public Command NextCommand() {
		return engine.NextCommand();
	}

	/** Operateur:definir les predicats cet reutiliser dans
	 * 
	 * GoDown(C) \def (Environment::CellNature(Envi(C),Wdt(C),Hgt(C)-1)) \in {HOL,EMP}
	 * 		\and not exists Character c \in Environment::CellContent(Envi(C),Wdt(C),Hgt(C)-1))
	 * 		\and Environment::CellNature(Envi(C),Wdt(C),Hgt(C)-1)) \not in {LAD, HDR}
	 *
	 * Environment::CellNature(Envi(C),Wdt(C),Hgt(C)-1)) \not in {HOL,EMP} 
	 *	\implies Engine::NextCommand(Step(C))
	 *
	 * Engine::NextCommand(Step(C))=DigL
	 * 		\and (Environment::CellNature(Envi(C),Wdt(C),Hgt(C)-1)) \not in {HOL,EMP}
	 * 			  \or exists Character c \in Environment::CellContent(Envi(C),Wdt(C),Hgt(C)-1)))
	 * 		\and Environment::CellNature(Envi(C),Wdt(C)-1,Hgt(C)-1))=PLT
	 * 		\implies Environment::CellNature(Envi(C),Wdt(C)-1,Hgt(C)-1))= HOL
	 * 		
	 * Engine::NextCommand(Step(C))=DigR
	 * 		\and (Environment::CellNature(Envi(C),Wdt(C),Hgt(C)-1)) \not in {HOL,EMP}
	 * 			  \or exists Character c \in Environment::CellContent(Envi(C),Wdt(C),Hgt(C)-1)))
	 * 		\and Environment::CellNature(Envi(C),Wdt(C)+1,Hgt(C)-1))=PLT
	 * 		\implies Environment::CellNature(Envi(C),Wdt(C)+1,Hgt(C)-1))= HOL
	 **/

	@Override
	public void Step() { 
		boolean tomber = false;
		if((getEnvi().CellNature(getWdt(), getHgt()-1)==Cell.HOL)
				||(getEnvi().CellNature(getWdt(), getHgt()-1)==Cell.EMP)) {
			if(getEnvi().getCellContent(getWdt(), getHgt()-1).isEmpty()) {
				if(getEnvi().CellNature(getWdt(), getHgt())!=Cell.LAD 
						&& getEnvi().CellNature(getWdt(), getHgt())!=Cell.HDR) {
					super.goDown(); 
					tomber = true;
					System.out.println("Player tombe");
				}
			}
		}

		if(tomber == false) {
			if(NextCommand()==Command.DOWN) {
				super.goDown();
				System.out.println("Player descend");
			}

			if(NextCommand()==Command.UP) {
				super.goUp();
				System.out.println("Player monte");
			}

			if(NextCommand()==Command.LEFT) {
				super.goLeft();
				System.out.println("Player marche vers gauche");
			}

			if(NextCommand()==Command.RIGHT) {
				super.goRight();
				System.out.println("Player marche vers droite");
			}
		}

		if(NextCommand()==Command.DIGL) {
			if(((getEnvi().CellNature(getWdt(), getHgt()-1)!=Cell.HOL)
					&&(getEnvi().CellNature(getWdt(), getHgt()-1)!=Cell.EMP))
					||(!getEnvi().getCellContent(getWdt(), getHgt()-1).isEmpty())) {
				if(getEnvi().CellNature(getWdt()-1, getHgt()-1)==Cell.PLT) {			

					//appeler holes  
					Holes hole = new HolesImpl();
					hole.init(getEnvi(), getWdt()-1, getHgt()-1, 0);
					hole.getEnvi().Dig(getWdt()-1, getHgt()-1);
					engine.bindHolesService(hole);
					System.out.println("Player creuse a gauche");
					System.out.println(hole.getEnvi().CellNature(getWdt()-1, getHgt()-1));
				}
			}
		}


		if(NextCommand()==Command.DIGR) {
			if(((getEnvi().CellNature(getWdt(), getHgt()-1)!=Cell.HOL)
					&&(getEnvi().CellNature(getWdt(), getHgt()-1)!=Cell.EMP))
					||(!getEnvi().getCellContent(getWdt(), getHgt()-1).isEmpty())) {
				if(getEnvi().CellNature(getWdt()+1, getHgt()-1)==Cell.PLT) {
					getEnvi().Dig(getWdt()+1, getHgt()-1);
					//appeler holes 
					Holes hole = new HolesImpl();
					hole.init(getEnvi(), getWdt()+1, getHgt()-1, 0);
					engine.bindHolesService(hole);
					System.out.println("Player creuse a droite");
				}
			}
		} 

		
		
		if(engine.isPrepareToFight() && NextCommand()==Command.ATTACKLEFT) {
			if (wdt!=0) {
				if(getEnvi().CellNature(getWdt()-1,getHgt())!=Cell.PLT 
						&& getEnvi().CellNature(getWdt()-1,getHgt())!=Cell.MTL) {

					if ((getEnvi().CellNature(getWdt(), getHgt())==Cell.LAD
							|| getEnvi().CellNature(getWdt(), getHgt())==Cell.HDR 
							|| (getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.PLT 
							||getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.MTL 
							||getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.LAD))

							|| (!getEnvi().getCellContent(getWdt(),getHgt()-1).isEmpty())) {
						//le joueur attaque 
						engine.prepareToFight(getWdt()-1,getHgt());
						System.out.println("Player attaque sa gauche");
					}
				}
			} 
		}

		if(engine.isPrepareToFight() && NextCommand()==Command.ATTACKRIGHT) {
			if (wdt!=getEnvi().getWidth()-1) {
				if (getEnvi().CellNature(getWdt()+1,getHgt())!=Cell.MTL 
						&& getEnvi().CellNature(getWdt()+1,getHgt())!=Cell.PLT) {

					if ((getEnvi().CellNature(getWdt(),getHgt())==Cell.LAD 
							|| getEnvi().CellNature(getWdt(),getHgt())==Cell.HDR 
							|| (getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.PLT
							|| getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.MTL 
							|| getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.LAD))
							|| (!getEnvi().getCellContent(getWdt(),getHgt()-1).isEmpty())) {

						//le joueur attaque 
						engine.prepareToFight(getWdt()+1,getHgt());
						System.out.println("Player attaque sa droite"); 
					}
				}
			}
		}
	}
}
