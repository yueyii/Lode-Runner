package services;

import java.util.ArrayList;

public interface Character extends CellContent {
	
	/** Observateur: Screen dans lequel evolue l'objet */
	public Environment getEnvi(); 
	 
	/** Observateur: hauteur du personnage */
	 public int getHgt() ;
	 
	/** Observateur: colonne du personnage */
	 public int getWdt();
	
	/** Invariant: un personnage peut etre dans une case seulement si celle-ci est libre
	 *  Environnement::CellNature(getEnvi(C),getWdt(C),getHgt(C)) \in {EMP,HOL,LAD,HDR}, \exist Character x \in Environnement::CellNature(getEnvi(C),getWdt(C),getHgt(C)) \implie x=C
	 */
	
	/** Initialisation
	 *  pre:  Environnement::CellNature(S,x,y) = EMP
	 */
	 public void init(Screen screen, int x, int y);
	 
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
	 public void goLeft();
	 
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
	 public void goRight();
	 
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
	 public void goUp();
	 
	 /** Operateur: descendre quand le psersonnage est sur une case d'echelle ou en train de tomber
	  *  post: getWdt()= getWdt()@pre
	  *  post: getHgt() = 0 \implies getHgt()= getHgt()@pre
	  *  post: Environnment::CellNaure(getEnvi(),getWdt(),getHgt()-1) \in {MTL,PLT} \implies getHgt()=getHgt()@pre
	  *  post: Environnment::CellNaure(getEnvi(),getWdt(),getHgt()) \not \in {LAD,HDR} 
	  *  		\and Environnment::CellNaure(getEnvi(),getWdt(),getHgt()-1)\not \in {PLT,MTL,LAD}
	  *  		\and \not \exist Character c \in Environnment::CellContent(getEnvi(),getWdt(),getHgt()-1)
	  *  		\implies getHgt()=getHgt()@pre
	  *  post: \exist Character c \in Environnment::CellContent(getEnvi(),getWdt(),getHgt()-1) \implies getHgt()=getHgt()@pre
	  *  post: (getHgt()!=0) \and  Environnment::CellNaure(getEnvi(),getWdt(),getHgt()-1) \not in{PLT,MTL}
	  *  		\and (Environnment::CellNaure(getEnvi(),getWdt(),getHgt()-1) \in {EMP,LAD,HDR,HOL}
	  *  		\and \not (\exit Character c \in Environnment::CellContent(getEnvi(),getWdt(),getHgt()-1))
	  *  		\implies getHgt()=getHgt()@pre - 1
	  */
	 public void goDown();
	
}
