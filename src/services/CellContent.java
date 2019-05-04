package services;

public interface CellContent {
	/** Observateur: contenu d'une case choisie 
	 *  pre: 0 <= x < getWidth() and 0 <= y < getHeight()
	 */
	 
	/** Invariant: une case contient au plus 1 Character
	 *  \forall x (x \in {0...width()-1}), \forall y (y \in {0...Height()-1}), 
	 *  	\forall Character c1,c2 \in CellContent(x,y)^2, c1=c2
	 */
	 
	/** Invariant: une case non-libre ne contient rien
	 *  \forall x (x \in {0...width()-1}), 
	 *  	\forall y (y \in {0...Height()-1}),  
	 *  	CellContent(x,y) \in {MTL,PLR} \implies CellContent(x,y)=null
	 */
	 
	/** Invariant: une case ne contient un tresor que si la case en dessous d'elle est non-libre
	 *  \forall x (x \in {0...width()-1}), \forall y (y \in {0...Height()-1}), \exists Treasure t \in CellContent(x,y) \implies (CellContent(x,y)=EMP \and CellContent(x,y-1) \in {PLT,MTL}) 
	 */
	 
	/** Initialisation
	 *  post: \forall x (x \in {0...width()-1}) and \forall y (y \in {0...Height()-1}) \with CellNature(x,y)=EditableScreen::CellNature(x,y)
	 */
	
}
