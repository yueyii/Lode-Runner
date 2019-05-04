package services;

import java.util.ArrayList;

public interface Guard extends Character, Engine {
	public int getGardeId();	
	public Move getBehaviour();
	public Character getTarget();
	public int getTimeInhole();
	
	//l'op¨¦ration ce qu'on a ajout¨¦ pour compl¨¦ter la service guard
	public void init(Screen screen, int x, int y, int id);
	public ArrayList<Integer> getGuardIdList();

	 /** Operateur: 
	  * pre: Environment::CellNature(Envi(G),Hgt(G),Wdt(G))=HOL
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
	
	public void ClimbLeft();
	
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
	public void ClimbRight();
	
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
	public void Step();
	
	/** Invariant: 
	 * 
	 *	Environnment::CellNature(Envi(G),Wdt(G),Hgt(G))=LAD
	 * 
	 * \and Hgt(G)<Character::Hgt(getTarget(G))
	 * 
	 * \and (Environnment::CellNature CellNature(Envi(G),Wdt(G),Hgt(G))
	 * 		\not in {PLT,MTL}
	 * 		\or exists Character \in Environnment::CellNature CellNature(Envi(G),Wdt(G),Hgt(G)-1)
	 * 		\implies Environnment::Hgt(getTarget(G))-Hgt(G)<|Environnment::Wdt(Target(G))-Wdt(G))|)
	 * 	\implies getBehaviour(G)=UP		  
	 */

	/** Invariant: 
	 * 
	 *	Environnment::CellNature(Envi(G),Wdt(G),Hgt(G))=LAD
	 * 
	 * \and Hgt(G)>Character::Hgt(getTarget(G))
	 * 
	 * \and (Environnment::CellNature CellNature(Envi(G),Wdt(G),Hgt(G))
	 * 		\not in {PLT,MTL}
	 * 		\or exists Character \in Environnment::CellNature CellNature(Envi(G),Wdt(G),Hgt(G)-1)
	 * 		\implies Environnment::Hgt(getTarget(G))-Hgt(G)>|Environnment::Wdt(Target(G))-Wdt(G))|)
	 * 	\implies getBehaviour(G)=DOWN		  
	 */
	
	/** Invariant: 
	 * 
	 *	Environnment::CellNature(Envi(G),Wdt(G),Hgt(G))=LAD
	 * 
	 * \and Hgt(G)=Character::Hgt(getTarget(G))
	 * 
	 * \and (Environnment::CellNature CellNature(Envi(G),Wdt(G),Hgt(G))
	 * 		\not in {PLT,MTL}
	 * 		\or exists Character \in Environnment::CellNature CellNature(Envi(G),Wdt(G),Hgt(G)-1)
	 * 		\implies Environnment::Hgt(getTarget(G))-Hgt(G)=|Environnment::Wdt(Target(G))-Wdt(G))|)
	 * 	\implies getBehaviour(G)=NEUTRAL		  
	 */

	/** Invariant: 
	 * 
	 *	Environnment::CellNature(Envi(G),Wdt(G),Hgt(G))={HOL,HDR}
	 * 
	 * \and Wdt(G)>Character::Wdt(getTarget(G))
	 * 
	 * \and (Environnment::CellNature CellNature(Envi(G),Wdt(G),Hgt(G))
	 * 		\not in {PLT,MTL}
	 * 		\or exists Character \in Environnment::CellNature CellNature(Envi(G),Wdt(G),Hgt(G)-1)
	 * 		\implies Environnment::Environnment::Wdt(Target(G))-Wdt(G))>|Hgt(getTarget(G))-Hgt(G)|)
	 * 	\implies getBehaviour(G)=LEFT		  
	 */
	
	/** Invariant: 
	 * 
	 *	Environnment::CellNature(Envi(G),Wdt(G),Hgt(G))={HOL,HDR}
	 * 
	 * \and Wdt(G)<Character::Wdt(getTarget(G))
	 * 
	 * \and (Environnment::CellNature CellNature(Envi(G),Wdt(G),Hgt(G))
	 * 		\not in {PLT,MTL}
	 * 		\or exists Character \in Environnment::CellNature CellNature(Envi(G),Wdt(G),Hgt(G)-1)
	 * 		\implies Environnment::Environnment::Wdt(Target(G))-Wdt(G))<|Hgt(getTarget(G))-Hgt(G)|)
	 * 	\implies getBehaviour(G)=RIGHT
	 */
	
	/** Invariant: 
	 * 
	 *	Environnment::CellNature(Envi(G),Wdt(G),Hgt(G))={HOL,HDR}
	 * 
	 * \and Wdt(G) = Character::Wdt(getTarget(G))
	 * 
	 * \and (Environnment::CellNature CellNature(Envi(G),Wdt(G),Hgt(G))
	 * 		\not in {PLT,MTL}
	 * 		\or exists Character \in Environnment::CellNature CellNature(Envi(G),Wdt(G),Hgt(G)-1)
	 * 		\implies Environnment::Environnment::Wdt(Target(G))-Wdt(G))=|Hgt(getTarget(G))-Hgt(G)|)
	 * 	\implies getBehaviour(G)=NEUTRAL
	 */
	
	
}
