package contract;

import java.util.HashSet;

import contractsError.InvariantError;
import contractsError.PostconditionError;
import contractsError.PreconditionError;
import decorators.CharacterDecorator;
import services.Cell;
import services.CellContent;
import services.Screen;
import services.Character;

public class CharacterContract extends CharacterDecorator {
	public CharacterContract(Character delegate) {
		super(delegate);
	}

	/** Invariant: un personnage peut etre dans une case seulement si celle-ci est libre
	 *  Environnement::CellNature(getEnvi(C),getWdt(C),getHgt(C)) \in {EMP,HOL,LAD,HDR}, 
	 *  \exist Character x \in Environnement::CellNature(getEnvi(C),getWdt(C),getHgt(C)) \implie x=C
	 */
	public void checkInvariant() { 
		if ((getEnvi().CellNature(getWdt(),getHgt())!=Cell.EMP)
				&& (getEnvi().CellNature(getWdt(),getHgt())!=Cell.HOL)
				&& (getEnvi().CellNature(getWdt(),getHgt())!=Cell.LAD)
				&& (getEnvi().CellNature(getWdt(),getHgt())!=Cell.HDR)){
			throw new InvariantError("CharacterContract ==> \\inv un personnage peut etre dans une case seulement si celle-ci est libre ");
		}  
	}

	/** Initialisation
	 *  pre:  Environnement::CellNature(S,x,y) = EMP
	 */
	public void init(Screen screen, int x, int y) {
		//1 Pre-condition
		if (screen.CellNature(x,y)!=Cell.EMP) {
			throw new PreconditionError("CharacterContract init(Screen screen, int x, int y) ==> \\pre  Environnement::CellNature(S,x,y) = EMP");
		}  
		
		checkInvariant();
		
		//4 Traitement
		super.init(screen,x,y);

	}

	/** Operateur: se deplacer vers la gauche
	 *  post: getHgt()= getHgt()@pre
	 *  post: getWdt() = 0 \implies getWdt()= getWdt()@pre
	 *  post: Environnment::CellNaure(getEnvi(),getWdt()-1,getHgt()) \in {MTL,PLT} \implies getWdt()=getWdt()@pre
	 *  post: Environnment::CellNaure(getEnvi(),getWdt(),getHgt()) \not \in {LAD,HDR} 
	 *  		\and Environnment::CellNaure(getEnvi(),getWdt(),getHgt()-1)\not in{PLT,MTL,LAD}
	 *  		\and \not \exist Character c \in Environnment::CellContent(getEnvi(),getWdt(),getHgt()-1)
	 *  		\implies getWdt()=getWdt()@pre
	 *  post: \exist Character c \in Environnment::CellContent(getEnvi(),getWdt()-1,getHgt()) \implies getWdt()=getWdt()@pre
	 *  post: (getWdt()!=0) \and  Environnment::CellNaure(getEnvi(),getWdt()-1,getHgt()) \not \in {PLT,MTL}
	 *  		\and (Environnment::CellNaure(getEnvi(),getWdt(),getHgt()) \in {LAD,HDR}
	 *  			\or Environnment::CellNaure(getEnvi(),getWdt(),getHgt()-1) \in {MTL,PLT,LAD}
	 *  			\or \exist Character c \in Environnment::CellContent(getEnvi(),getWdt(),getHgt()-1)
	 *  		\and \not (\exit Character c \in Environnment::CellContent(getEnvi(),getWdt()-1,getHgt()))
	 *  		\implies getWdt()=getWdt()@pre - 1
	 */
	public void goLeft() {
		//1 Pre-condition

		//2 Invariant
		checkInvariant();

		//3 Capture
		int getHgt_pre = getHgt();
		int getWdt_pre = getWdt();

		//4 Traitement
		super.goLeft();
		
		//5 Inv
		checkInvariant();

		Cell CellLeft =getEnvi().CellNature(getWdt()-1,getHgt());
		Cell cell = getEnvi().CellNature(getWdt(),getHgt());
		Cell CellDown = getEnvi().CellNature(getWdt(),getHgt()-1);
		HashSet<CellContent> cDown = getEnvi().getCellContent(getWdt(),getHgt()-1);
		HashSet<CellContent> cLeft = getEnvi().getCellContent(getWdt()-1,getHgt());


		//6 PostCondition
		if (getHgt_pre!=getHgt()) {
			throw new PostconditionError("CharacterContract goLeft() ==> \\post invariant de hauteur");	
		}
		if (getWdt_pre==0 && getWdt_pre!=getWdt()) {
			throw new PostconditionError("CharacterContract goLeft() ==> \\post les limites de l'ecran");	
		}
		if (CellLeft==Cell.MTL || CellLeft==Cell.PLT) {
			if (getWdt_pre!=getWdt()) {
				throw new PostconditionError("CharacterContract goLeft() ==> \\post une plateforme a gauche");	
			}
		}
		if (cell!=Cell.LAD && cell!=Cell.HDR) {
			if (CellDown!=Cell.PLT && CellDown!=Cell.MTL && CellDown!=Cell.LAD) {
				if (cDown.isEmpty()) {
					if (getWdt_pre!=getWdt()) {
						throw new PostconditionError("CharacterContract goLeft() ==> \\post en train de tomber");	
					}
				}
			}
		}
		if (!cLeft.isEmpty()) {
			if (getWdt_pre!=getWdt()) {
				throw new PostconditionError("CharacterContract goLeft() ==> \\post un personnage a gauche");	
			}
		}
		if (getWdt_pre!=0) {
			if (CellLeft!=Cell.MTL && CellLeft!=Cell.PLT) {
				if ((cell==Cell.LAD || cell==Cell.HDR) 
						|| (CellDown==Cell.PLT ||CellDown==Cell.MTL ||CellDown==Cell.LAD)
						|| (!cDown.isEmpty())) {
					if (cLeft.isEmpty()) {
						if(getWdt_pre-1!=getWdt())  {
							throw new PostconditionError("CharacterContract goLeft()  ==> \\post getWdt()=getWdt()@pre-1");	
						}
					}
				}
			}
		}
	}

	/** Operateur: se deplacer vers la droite
	 *  post: getHgt()= getHgt()@pre
	 *  post: getWdt() = Environnment::getWidth()-1 \implies getWdt()= getWdt()@pre
	 *  post: Environnment::CellNaure(getEnvi(),getWdt()+1,getHgt()) \in {MTL,PLT} \implies getWdt()=getWdt()@pre
	 *  post: Environnment::CellNaure(getEnvi(),getWdt(),getHgt()) \not \in {LAD,HDR} 
	 *  		\and Environnment::CellNaure(getEnvi(),getWdt(),getHgt()-1)\not \in {PLT,MTL,LAD}
	 *  		\and \not \exist Character c \in Environnment::CellContent(getEnvi(),getWdt(),getHgt()-1)
	 *  		\implies getWdt()=getWdt()@pre
	 *  post: \exist Character c \in Environnment::CellContent(getEnvi(),getWdt()+1,getHgt()) \implies getWdt()=getWdt()@pre
	 *  post: (getWdt()!=Environnment::getWidth()-1) \and  Environnment::CellNaure(getEnvi(),getWdt()+1,getHgt()) \not \in {PLT,MTL}
	 *  		\and (Environnment::CellNaure(getEnvi(),getWdt(),getHgt()) \in {LAD,HDR}
	 *  			\or Environnment::CellNaure(getEnvi(),getWdt(),getHgt()-1) \in {MTL,PLT,LAD}
	 *  			\or \exist Character c \in Environnment::CellContent(getEnvi(),getWdt(),getHgt()-1)
	 *  		\and \not (\exit Character c \in Environnment::CellContent(getEnvi(),getWdt()+1,getHgt()))
	 *  		\implies getWdt()=getWdt()@pre + 1
	 */
	public void goRight() {
		//1 Pre-condition

		//2 Invariant
		checkInvariant();

		//3 Capture
		int getHgt_pre = getHgt();
		int getWdt_pre = getWdt();

		//4 Traitement
		super.goRight();

		//5 Inv
		checkInvariant();

		Cell CellRight = getEnvi().CellNature(getWdt()+1,getHgt());
		Cell cell = getEnvi().CellNature(getWdt(),getHgt());
		Cell CellDown = getEnvi().CellNature(getWdt(),getHgt()-1);
		HashSet<CellContent> cDown = getEnvi().getCellContent(getWdt(),getHgt()-1);
		HashSet<CellContent> cRight = getEnvi().getCellContent(getWdt()+1,getHgt());

		//6 PostCondition
		if (getHgt_pre!=getHgt()) {
			throw new PostconditionError("CharacterContract goRight() ==> \\post invariant de hauteur");	
		}
		if (getWdt_pre==(getEnvi().getWidth()-1) && getWdt_pre!=getWdt()) {
			throw new PostconditionError("CharacterContract goRight() ==> \\post les limites de l'ecran");	
		}
		if (CellRight==Cell.MTL || CellRight==Cell.PLT) {
			if (getWdt_pre!=getWdt()) {
				throw new PostconditionError("CharacterContract goRight() ==> \\post une plateforme a droite");	
			}
		}
		if (cell!=Cell.LAD && cell!=Cell.HDR) {
			if (CellDown!=Cell.PLT && CellDown!=Cell.MTL && CellDown!=Cell.LAD) {
				if (cDown.isEmpty()) {
					if (getWdt_pre!=getWdt()) {
						throw new PostconditionError("CharacterContract goRight() ==> \\post en train de tomber");	
					}
				}
			}
		}
		if (!cRight.isEmpty()) {
			if (getWdt_pre!=getWdt()) {
				throw new PostconditionError("CharacterContract goRight() ==> \\post un personnage a droite");	
			}
		}
		if (getWdt_pre!=getEnvi().getWidth()-1) {
			if (CellRight!=Cell.MTL && CellRight!=Cell.PLT) {
				if ((cell==Cell.LAD || cell==Cell.HDR) 
						|| (CellDown==Cell.PLT ||CellDown==Cell.MTL ||CellDown==Cell.LAD)
						|| (!cDown.isEmpty())) {
					if (cRight.isEmpty()) {
						if(getWdt_pre+1!=getWdt())  {
							throw new PostconditionError("CharacterContract goRight() ==> \\post getWdt()=getWdt()@pre+1");	
						}
					}
				}
			}
		}
	}

	/** Operateur: monter quand le psersonnage est sur une case d'echelle
	 *  post: getWdt()= getWdt()@pre
	 *  post: getHgt() = Environnment::getHeight()-1 \implies getHgt()= getHgt()@pre
	 *  post: Environnment::CellNaure(getEnvi(),getWdt(),getHgt()+1) \in {MTL,PLT} \implies getHgt()=getHgt()@pre
	 *  post: Environnment::CellNaure(getEnvi(),getWdt(),getHgt()) \not \in {LAD,HDR} 
	 *  		\and Environnment::CellNaure(getEnvi(),getWdt(),getHgt()-1)\not \in {PLT,MTL,LAD}
	 *  		\and \not \exist Character c \in Environnment::CellContent(getEnvi(),getWdt(),getHgt()-1)
	 *  		\implies getHgt()=getHgt()@pre
	 *  post: \exist Character c \in Environnment::CellContent(getEnvi(),getWdt(),getHgt()+1) \implies getHgt()=getHgt()@pre
	 *  post: (getHgt()!=Environnment::getHeight()-1) \and  Environnment::CellNaure(getEnvi(),getWdt(),getHgt()+1) \not \in {PLT,MTL}
	 *  		\and (Environnment::CellNaure(getEnvi(),getWdt(),getHgt()) \in {LAD}
	 *  		\and (Environnment::CellNaure(getEnvi(),getWdt(),getHgt()+1) \in {EMP,LAD,HDR,HOL}
	 *  		\and \not (\exit Character c \in Environnment::CellContent(getEnvi(),getWdt(),getHgt()+1))
	 *  		\implies getHgt()=getHgt()@pre + 1
	 */
	public void goUp() {
		//1 Pre-condition

		//2 Invariant
		checkInvariant();

		//3 Capture
		int getHgt_pre = getHgt();
		int getWdt_pre = getWdt();

		//4 Traitement
		super.goUp();

		//5 Inv
		checkInvariant();

		Cell CellUp = getEnvi().CellNature(getWdt(),getHgt()+1);
		Cell cell = getEnvi().CellNature(getWdt(),getHgt());
		Cell CellDown = getEnvi().CellNature(getWdt(),getHgt()-1);
		HashSet<CellContent> cDown = getEnvi().getCellContent(getWdt(),getHgt()-1);
		HashSet<CellContent> cUp = getEnvi().getCellContent(getWdt(),getHgt()+1);

		//6 PostCondition
		if (getWdt_pre!=getWdt()) {
			throw new PostconditionError("CharacterContract goUp()  ==> \\post invariant de largeur");	
		}
		if (getHgt_pre==(getEnvi().getHeight()-1) && getHgt_pre!=getHgt()) {
			throw new PostconditionError("CharacterContract goUp()  ==> \\post les limites de l'ecran");	
		}
		if (CellUp==Cell.MTL || CellUp==Cell.PLT) {
			if (getHgt_pre!=getHgt()) {
				throw new PostconditionError("CharacterContract goUp() ==> \\post une plateforme en haut");	
			}
		}
		if (cell!=Cell.LAD && cell!=Cell.HDR) {
			if (CellDown!=Cell.PLT && CellDown!=Cell.MTL && CellDown!=Cell.LAD) {
				if (cDown.isEmpty()) {
					if (getHgt_pre!=getHgt()) {
						throw new PostconditionError("CharacterContract goUp() ==> \\post en train de tomber");	
					}
				}
			}
		}
		if (!cUp.isEmpty()) {
			if (getHgt_pre!=getHgt()) {
				throw new PostconditionError("CharacterContract goUp() ==> \\post un personnage en haut");	
			}
		}
		if (getHgt_pre!=(getEnvi().getHeight()-1)) {
			if (CellUp!=Cell.MTL && CellUp!=Cell.PLT) {
				if ((cell==Cell.LAD) && (CellUp==Cell.EMP||CellUp==Cell.LAD||CellUp==Cell.HDR||CellUp==Cell.HOL) ) {
					if (cUp.isEmpty()) {
						if(getHgt_pre+1!=getHgt())  {
							throw new PostconditionError("CharacterContract goUp()  ==> \\post getHgt()=getHgt()@pre+1");	
						}
					}
				}
			}
		}
	}

	/** Operateur: descendre quand le personnage est sur une case d'echelle ou en train de tomber
	 *  post: getWdt()= getWdt()@pre
	 *  post: getHgt() = 0 \implies getHgt()= getHgt()@pre
	 *  
	 *  post: (getHgt()!=0) \and Environnment::CellNaure(getEnvi(),getWdt(),getHgt()) \not \in {LAD,HDR} 
	 *  		\and Environnment::CellNaure(getEnvi(),getWdt(),getHgt()-1)\not \in {PLT,MTL,LAD}
	 *  		\and \not \exist Character c \in Environnment::CellContent(getEnvi(),getWdt(),getHgt()-1)
	 *  		\implies getHgt()=getHgt()-1@pre
	 *  
	 *  post: \exist Character c \in Environnment::CellContent(getEnvi(),getWdt(),getHgt()-1) 
	 *  		\implies getHgt()=getHgt()@pre
	 *  
	 *  post:  (getHgt()!=0) \and  Environnment::CellNaure(getEnvi(),getWdt(),getHgt()-1) \not in{PLT,MTL}
	 *  		\and (Environnment::CellNaure(getEnvi(),getWdt(),getHgt()-1) \in {EMP,LAD,HDR,HOL}
	 *  		\or (\exit Character c \in Environnment::CellContent(getEnvi(),getWdt(),getHgt()-1))
	 *  		\implies getHgt()-1=getHgt()@pre
	 */
	public void goDown() {
		//1 Pre-condition

		//2 Invariant
		checkInvariant();

		//3 Capture
		int getHgt_pre = getHgt();
		int getWdt_pre = getWdt();
		
		//4 Traitement 
		super.goDown();
	
		//5 Inv
		checkInvariant();
		Cell CellDown = getEnvi().CellNature(getWdt(),getHgt()-1);
		Cell cell = getEnvi().CellNature(getWdt(),getHgt());
		HashSet<CellContent> cDown = getEnvi().getCellContent(getWdt(),getHgt()-1);

		//6 PostCondition
		if (getWdt_pre!=getWdt()) {
			throw new PostconditionError("CharacterContract goDown() ==> \\post invariant de largeur");	
		}
		if (getHgt_pre==0 && getHgt_pre!=getHgt()) {
			throw new PostconditionError("CharacterContract goDown() ==> \\post les limites de l'ecran");	
		}

		if (CellDown==Cell.MTL || CellDown==Cell.PLT) {
			if (getHgt_pre!=getHgt()) {
				throw new PostconditionError("CharacterContract goDown() ==> \\post une plateforme en bas");	
			}
		}

		if (getHgt_pre!=0) {
			if (cell!=Cell.LAD && cell!=Cell.HDR) {
				if (CellDown!=Cell.PLT && CellDown!=Cell.MTL && CellDown!=Cell.LAD) {
					if (cDown.isEmpty()) {
						if (getHgt_pre==getHgt()) {
							throw new PostconditionError("CharacterContract goDown() ==> \\post ne pas tomber");	
						}
					}
				}
			}
		}

		if (!cDown.isEmpty()) {
			if (getHgt_pre!=getHgt()) {
				throw new PostconditionError("CharacterContract goDown() ==> \\post un personnage en bas");	
			}
		}

		if (getHgt_pre!=0) {
			if (CellDown!=Cell.MTL && CellDown!=Cell.PLT) {
				if ((CellDown==Cell.EMP||CellDown==Cell.LAD||CellDown==Cell.HDR||CellDown==Cell.HOL) ) {
					if (cDown.isEmpty()) {
						if(getHgt_pre-1!=getHgt())  {
							throw new PostconditionError("CharacterContract goDown() ==> \\post getHgt()=getHgt()@pre-1");	
						}
					}
				}
			}
		}
	}
}
