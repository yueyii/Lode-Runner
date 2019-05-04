package services;

public interface Screen {

	/** Observateur: nombre de lignes de l'ecran */
	 public int getHeight() ;
	 
	/** Observateur: nombre de colonnes de l'ecran */
	 public int getWidth();
	 
	/** Observateur: nature d'une case de l'ecran 
	 *  pre: 0 <= x < getWidth() and 0 <= y < getHeight()
	 */
	 public Cell CellNature(int x,int y);

	/** Initialisation
	 *  pre:  0 < h and 0 < w
	 *  post: getHeight() = h
	 *  post: getWidth() = w
	 *  post: \forall x (x \in {0...width()-1}) and \forall y (y \in {0...Height()-1}) \with CellNature(x,y)=EMP
	 */
	 public void Init(int x , int y);
	 
	 /** Operateur: transforme une case PLT en case HOL
	  *  pre: 0 <= x < getHeight() and 0 <= y < getWidth() and CellNature(x,y) = PLT
	  *  post: CellNature(x,y) = HOL
	  *  post: \forall x (x \in {0...width()-1}) and \forall y (y \in {0...Height()-1}) \with (u!=x \or v!=y) \implies CellNature(u,v)=CellNature(u,v)@pre
	  */
	 public void Dig(int x, int y);
	 
	 /** Operateur: transforme une case HOL en case PLT
	  *  pre: 0 <= x < getHeight() and 0 <= y < getWidth() and CellNature(x,y) = HOL
	  *  post: CellNature(x,y) = PLT
	  *  post: \forall x (x \in {0...width()-1}) and \forall y (y \in {0...Height()-1}) \with (u!=x \or v!=y) \implies CellNature(u,v)=CellNature(u,v)@pre
	  */
	 public void Fill(int x, int y);
	 
}
