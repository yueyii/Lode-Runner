package contract;

import java.util.ArrayList;

import contractsError.InvariantError;
import contractsError.PostconditionError;
import contractsError.PreconditionError;
import services.Cell;
import services.Character;
import services.Command;
import services.Guard;
import services.Move;
import services.Screen;

public class GuardContract extends CharacterContract implements Guard{

	public GuardContract(Guard delegate) {
		super(delegate); 
	}

	@Override
	protected Guard getDelegate() {
		return (Guard) super.getDelegate();
	}
 
	public void CheckInvariant() {

		if(getTarget().equals(super.getDelegate())) {
			throw new InvariantError("GuardContract ==> \\inv target n'est pas une caract¨¨re");
		}

		if(getTimeInhole()<0) {
			throw new InvariantError("GuardContract ==> \\inv timeinhole in¨¦frieur que 0");
		}

		if(getEnvi().CellNature(getWdt(), getHgt())==Cell.LAD) {
			if(getHgt() < getTarget().getHgt()) {
				if(getBehaviour()!=Move.UP) {
					throw new InvariantError("GuardContract ==> \\inv getBehaviour() monter pas");
				}

			}

			else if(getHgt() > getTarget().getHgt()) {
				if(getBehaviour()!=Move.DOWN) {
					throw new InvariantError("GuardContract ==> \\inv getBehaviour() desendre pas");

				}
			}
			else if(getWdt() > getTarget().getWdt()) {
				if(getBehaviour()!=Move.LEFT) {
					throw new InvariantError("GuardContract ==> \\inv getBehaviour() bouger pas ¨¤ gauche");
				}

			}
			else if(getWdt() < getTarget().getWdt()) {
				if(getBehaviour()!=Move.RIGHT) {
					throw new InvariantError("GuardContract ==> \\inv getBehaviour() bouger pas ¨¤ droite");

				}
			}
			else {
				if(getBehaviour()!=Move.NEUTRAL) {
					throw new InvariantError("GuardContract ==> \\inv getBehaviour() bouger");
				}
			}
		}

		//si le garde dans une trou/rail/au-dessus non libre/contentant une personne
		else if (getEnvi().CellNature(getWdt(),getHgt())==Cell.HOL
				||getEnvi().CellNature(getWdt(),getHgt())==Cell.HDR 
				|| (getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.PLT
				|| getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.MTL 
				|| getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.LAD)
				|| (!getEnvi().getCellContent(getWdt(),getHgt()-1).isEmpty())) {

			if(getWdt() > getTarget().getWdt()) {
				if(getBehaviour()!=Move.LEFT) {
					throw new InvariantError("GuardContract ==> \\inv getBehaviour() bouger pas ¨¤ gauche");
			}

			}
			else if(getWdt() < getTarget().getWdt()) {
				if(getBehaviour()!=Move.RIGHT) {
					throw new InvariantError("GuardContract ==> \\inv getBehaviour() bouger pas ¨¤ droite");

				}
			}
		}

		else if ((getEnvi().CellNature(getWdt(), getHgt())==Cell.LAD)&&
				(getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.PLT
				|| getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.MTL 
				|| getEnvi().CellNature(getWdt(),getHgt()-1)==Cell.LAD
				|| (!getEnvi().getCellContent(getWdt(),getHgt()-1).isEmpty()))) {
			if(getHgt() < getTarget().getHgt()) {
				if((getTarget().getHgt()-getHgt())<Math.abs(getTarget().getWdt()-getWdt())) {
					if(getBehaviour()!=Move.UP) {
						throw new InvariantError("GuardContract ==> \\inv getBehaviour() monter pas");
					}
				}

				//si le joueur est en dessous de lui
				else if(getHgt() > getTarget().getHgt()) {
					if((getHgt()-getTarget().getHgt())<Math.abs(getTarget().getWdt()-getWdt())) {
						if(getBehaviour()!=Move.DOWN) {
							throw new InvariantError("GuardContract ==> \\inv getBehaviour() desendre pas");

						}
					}
				}
				else if(getWdt() > getTarget().getWdt()) {	
					if((getWdt()-getTarget().getWdt())<Math.abs(getTarget().getHgt()-getHgt())) {
						if(getBehaviour()!=Move.LEFT) {
							throw new InvariantError("GuardContract ==> \\inv getBehaviour() bouger pas ¨¤ gauche");
						}
					}
				}
				//si le joueur est ¨¤ sa droite
				else if(getWdt() < getTarget().getWdt()) {			
					if((getTarget().getWdt()-getWdt())<Math.abs(getTarget().getHgt()-getHgt())) {
						if(getBehaviour()!=Move.RIGHT) {
							throw new InvariantError("GuardContract ==> \\inv getBehaviour() bouger pas ¨¤ droite");

						}
					}
				}

				else {
					if(getBehaviour()!=Move.NEUTRAL) {
						throw new InvariantError("GuardContract ==> \\inv getBehaviour() bouger");
					}
					System.out.println("number"+getGardeId()+"garde sur rail/sur case non-libre ==> Neutral");
				}
			}
		}
		super.checkInvariant();

	}

	@Override
	public Command NextCommand() {
		return getDelegate().NextCommand();
	}

	@Override
	public int getGardeId() {
		return getDelegate().getGardeId();
	}

	@Override
	public Move getBehaviour() {
		return getDelegate().getBehaviour();
	}

	@Override
	public Character getTarget() {
		return getDelegate().getTarget();
	}

	@Override
	public int getTimeInhole() {
		return getDelegate().getTimeInhole();
	}

	/** Operateur: 
	 * pre: Environment::CellNature(Envi(G),Hgt(G),Wdt(G))=HOL
	 * 
	 * post: Wdt(C)=0 \implies Wdt(ClimbLeft(C))=Wdt(C) \and Hgt(ClimbLeft(C))=Hgt(C)
	 * 
	 * Screen::CellNature(Envi(C),Wdt(C)-1,Hgt(C)+1) \in {MTL,PLT}
	 * 	\implies Wdt(ClimbLeft(C))=Wdt(C) \and Hgt(ClimbLeft(C))=Hgt(C)
	 *
	 * \exists Character \in Environment::CellContent((Envi(C),Wdt(C)-1,Hgt(C)+1))
	 * 	\implies  Wdt(ClimbLeft(C))=Wdt(C) \and Hgt(ClimbLeft(C))=Hgt(C)
	 * 
	 * Wdt!=0 \and Screen::CellNature(Envi(C),Wdt(C)-1,Hgt(C)+1) \not in {MTL,PLT}
	 * 	\and not exists Character c \in Environment::CellContent(Envi(C),Wdt(C)-1,Hgt(C)+1)
	 * 	\implies Wdt(ClimbLeft(C))=Wdt(C)-1 \and Hgt(ClimbLeft(C))=Hgt(C)+1
	 */	
	@Override
	public void ClimbLeft() {
		//pre-condition
		if(getEnvi().CellNature(getWdt(), getHgt())!=Cell.HOL) {
			throw new PreconditionError("GuardContract ClimbLeft() ==> //pre:guard n'esr pas dans le trou ");	
		}

		//checkinv
		CheckInvariant();

		//capture
		int hgt_pre=getHgt();
		int wdt_pre=getWdt();

		//checkinv
		CheckInvariant();

		//traitement
		getDelegate().ClimbLeft();

		//post-condition
		if(wdt_pre==0) {
			if(getWdt()!=wdt_pre) {
				throw new PostconditionError("GuardContract ClimbLeft() ==> //post:wdt=0 Wdt(ClimbLeft(C))=Wdt(C)");	
			}
			if(getHgt()!=hgt_pre) {
				throw new PostconditionError("GuardContract ClimbLeft() ==> //post:wdt=0 Hgt(ClimbLeft(C))=Hgt(C)");
			}

		}

		if(getEnvi().CellNature(wdt_pre-1, hgt_pre+1)==Cell.MTL
				||getEnvi().CellNature(wdt_pre-1,hgt_pre+1)==Cell.PLT) {
			if(getWdt()!=wdt_pre) {
				throw new PostconditionError("GuardContract ClimbLeft() ==> //post:MTL,PLT Wdt(ClimbLeft(C))=Wdt(C)");	
			}
			if(getHgt()!=hgt_pre) {
				throw new PostconditionError("GuardContract ClimbLeft() ==> //post:MTL,PLT Hgt(ClimbLeft(C))=Hgt(C)");
			}
		}

		if(!getEnvi().getCellContent(wdt_pre-1, hgt_pre+1).isEmpty()) {
			if(getWdt()!=wdt_pre) {
				throw new PostconditionError("GuardContract ClimbLeft() ==> //post:character Wdt(ClimbLeft(C))=Wdt(C)");	
			}
			if(getHgt()!=hgt_pre) {
				throw new PostconditionError("GuardContract ClimbLeft() ==> //post:character Hgt(ClimbLeft(C))=Hgt(C)");
			}			
		}

		if(wdt_pre!=0) {
			if((getEnvi().CellNature(wdt_pre-1, hgt_pre+1)!=Cell.MTL) 
					&& (getEnvi().CellNature(wdt_pre-1, hgt_pre+1)!=Cell.PLT)) {
				if(getEnvi().getCellContent(wdt_pre-1, hgt_pre+1).isEmpty()) {
					if(getWdt()!=wdt_pre-1) {
						throw new PostconditionError("GuardContract ClimbLeft() ==> //post:wdt!=0 Wdt(ClimbLeft(C))=Wdt(C)-1");
					}
					if(getHgt()!=hgt_pre+1) {
						throw new PostconditionError("GuardContract ClimbLeft() ==> //post:wdt!=0 Hgt(ClimbLeft(C))=Hgt(C)+1");
					}
				}
			}
		}
	}


	/** Operateur: 
	 * pre: Environment::CellNature(Envi(G),Hgt(G),Wdt(G))=HOL
	 * post: Wdt(C)=0 \implies Wdt(ClimbLeft(C))=Wdt(C) \and Hgt(ClimbLeft(C))=Hgt(C)
	 * 
	 * Screen::CellNature(Envi(C),Wdt(C)+1,Hgt(C)+1) \in {MTL,PLT}
	 * 	\implies Wdt(ClimbLeft(C))=Wdt(C) \and Hgt(ClimbLeft(C))=Hgt(C)
	 *
	 * \exists Character \in Environment::CellContent((Envi(C),Wdt(C)+1,Hgt(C)+1))
	 * 	\implies  Wdt(ClimbLeft(C))=Wdt(C) \and Hgt(ClimbLeft(C))=Hgt(C)
	 * 
	 * Wdt!=0 \and Screen::CellNature(Envi(C),Wdt(C)+1,Hgt(C)+1) \not in {MTL,PLT}
	 * 	\and not exists Character c \in Environment::CellContent(Envi(C),Wdt(C)+1,Hgt(C)+1)
	 * 	\implies Wdt(ClimbLeft(C))=Wdt(C)+1 \and Hgt(ClimbLeft(C))=Hgt(C)+1
	 */

	@Override
	public void ClimbRight() {
		//pre-condition
		if(getEnvi().CellNature(getWdt(), getHgt())!=Cell.HOL) {
			throw new PreconditionError("GuardContract ClimbRight() ==> //pre:guard n'esr pas dans le trou ");	
		}

		//checkinv
		CheckInvariant();

		//capture
		int hgt_pre=getHgt();
		int wdt_pre=getWdt();

		//checkinv
		CheckInvariant();

		//traitement
		getDelegate().ClimbRight();

		//post-condition
		if(wdt_pre==0) {
			if(getWdt()!=wdt_pre) {
				throw new PostconditionError("GuardContract ClimbRight() ==> //post:wdt=0 Wdt(ClimbLeft(C))=Wdt(C)");	
			}
			if(getHgt()!=hgt_pre) {
				throw new PostconditionError("GuardContract ClimbRight() ==> //post:wdt=0 Hgt(ClimbLeft(C))=Hgt(C)");
			}

		}

		if(getEnvi().CellNature(wdt_pre+1, hgt_pre+1)==Cell.MTL
				||getEnvi().CellNature(wdt_pre+1, hgt_pre+1)==Cell.PLT) {
			if(getWdt()!=wdt_pre) {
				throw new PostconditionError("GuardContract ClimbRight() ==> //post:MTL,PLT Wdt(ClimbLeft(C))=Wdt(C)");	
			}
			if(getHgt()!=hgt_pre) {
				throw new PostconditionError("GuardContract ClimbRight() ==> //post:MTL,PLT Hgt(ClimbLeft(C))=Hgt(C)");
			}
		}

		if(!getEnvi().getCellContent(wdt_pre+1, hgt_pre+1).isEmpty()) {
			if(getWdt()!=wdt_pre) {
				throw new PostconditionError("GuardContract ClimbRight() ==> //post:character Wdt(ClimbLeft(C))=Wdt(C)");	
			}
			if(getHgt()!=hgt_pre) {
				throw new PostconditionError("GuardContract ClimbRight() ==> //post:character Hgt(ClimbLeft(C))=Hgt(C)");
			}			
		}

		if(wdt_pre!=0) {
			if((getEnvi().CellNature(wdt_pre+1, hgt_pre+1)!=Cell.MTL) 
					&& (getEnvi().CellNature(wdt_pre+1, hgt_pre+1)!=Cell.PLT)) {
				if(getEnvi().getCellContent(wdt_pre+1, hgt_pre+1).isEmpty()) {
					if(getWdt()!=wdt_pre+1) {
						throw new PostconditionError("GuardContract ClimbRight() ==> //post:wdt!=0 Wdt(ClimbLeft(C))=Wdt(C)+1");
					}
					if(getHgt()!=hgt_pre+1) {
						throw new PostconditionError("GuardContract ClimbRight() ==> //post:wdt!=0 Hgt(ClimbLeft(C))=Hgt(C)+1");
					}
				}
			}
		}
	}

	/** Operateur:d¨¦finir les pr¨¦dicats cet r¨¦utiliser dans
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

		//checkinv
		CheckInvariant();

		//capture
		int time_pre=getTimeInhole();
		int hgt_pre=getHgt();
		int wdt_pre=getWdt();


		//checkinv 
		CheckInvariant();

		getDelegate().Step();

		if(time_pre<5) {
			if(getEnvi().CellNature(wdt_pre, hgt_pre)==Cell.HOL) {
				if(getTimeInhole()!=time_pre+1) {
					throw new PostconditionError("GuardContract Step() ==> //post:le temps n'augmente pas");
				}	
			}
		}

	}

	@Override
	public void init(Screen screen, int x, int y, int id) {
		if(id<0) {
			throw new PreconditionError("GuardContract init() ==> //pre : id>=0 ");	
		}

		if(y <0 || y >screen.getHeight()) {
			throw new InvariantError("GuardContract init() ==> \\pre 0<getHgt()<getEnvi().getHeight()");	
		}
		
		if(x <0 || x >screen.getWidth()) {
			throw new InvariantError("GuardContract init() ==> \\pre 0<getWdt()<getEnvi().getWidth()");	
		}
		
		//si garde existe d¨¦ja dans la liste de gardes
		if(!getGuardIdList().isEmpty()) {
			if (getGuardIdList().contains(id)) {
				throw new PreconditionError("GuardContract init() ==> //pre id de garde existe d¨¦ja");
			}
		}
		getDelegate().init(screen, x, y, id);
	}

	@Override
	public ArrayList<Integer> getGuardIdList() {
		return getDelegate().getGuardIdList();
	}

}
