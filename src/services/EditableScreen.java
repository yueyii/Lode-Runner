package services;
import services.Cell;
import services.Screen;

public interface EditableScreen extends Screen{
	
	/** Observateur: un ecran est pret a etre joue? */
	public boolean isPlayable();
	
	/** Invariant: mininisation de isPlayable()
	 * 	isPlayable() == (\forall x (x \in {0...width()-1}) \and \forall y (y \in {0...Height()-1}) \with CellNature(x,y)!=HOL
	 * 						 \and \forall x (x \in {0...width()-1}) \and y==0 \with CellNature(x,y)==MTL)
	 */

	 /** Operateur: editer directement le contenu d'une case
	  *  pre: 0 <= y < getHeight(S) \and 0 <= x < getWidth(S)
	  *  post: CellNature(x,y) = C
	  *  post: \forall x (x \in {0...width()-1}) and \forall y (y \in {0...Height()-1}), (x!=u \or y!=y) \implies CellNature(x,y)=CellNature(x,y)@pre
	  */
	public void setNature(int x , int y, Cell cell);

}
