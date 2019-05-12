package services;

import java.util.HashSet;

import services.Screen;

public interface Environment extends Screen {
	
	/** Observateur: contenu d'une case choisie 
	 *  pre: 0 <= x < getWidth() and 0 <= y < getHeight()
	 */
	 public HashSet<CellContent> getCellContent(int x,int y);	
	 
	/** Invariant: une case non-libre ne contient rien
	 *  \forall x (x \in {0...width()-1}), \forall y (y \in {0...Height()-1}),  CellContent(x,y) \in {MTL,PLT} \implies CellContent(x,y)=null
	 */
	 
	/** Invariant: une case ne contient un tresor que si la case en dessous d'elle est non-libre
	 *  \forall x (x \in {0...width()-1}), \forall y (y \in {0...Height()-1}), \exists Treasure t \in CellContent(x,y) \implies (CellContent(x,y)=EMP \and CellContent(x,y-1) \in {PLT,MTL}) 
	 */
	// ce qu'on a ajoute pour lier envi et edit
	public void init(int x, int y , EditableScreen edit);
	 
}
