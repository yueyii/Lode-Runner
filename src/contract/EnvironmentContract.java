package contract;

import java.util.HashSet;

import contractsError.InvariantError;
import contractsError.PreconditionError;
import services.Cell;
import services.CellContent;
import services.EditableScreen;
import services.Item;
import services.Environment;

public class EnvironmentContract extends ScreenContract implements Environment {
	Item item;
	Item item2;

	public EnvironmentContract(Environment delegate) {
		super(delegate); 
	}

	protected Environment getDelegate() {
		return (Environment) super.getDelegate();
	}

	/** Observateur: contenu d'une case choisie 
	 *  pre: 0 <= x < getWidth() and 0 <= y < getHeight()
	 */
	public HashSet<CellContent> getCellContent(int x,int y) {
		if (y < 0 || y >= getHeight()) {
			throw new PreconditionError("EnvironnmentContract CellContent(int x,int y) ==> \\pre 0 <= y < getHeight(S)");
		}
		if (x < 0 || x >= getWidth()) {
			throw new PreconditionError("ScreenContract CellContent(int x,int y) ==> \\pre 0 <= y < 0<= x < getWidth(S)");
		} 

		return getDelegate().getCellContent(x, y);
	}

	/** Invariant: une case contient au plus 1 Charracter
	 *  \forall x (x \in {0...width()-1}), \forall y (y \in {0...Height()-1}),
	 *  	 \forall Character c1,c2 \in CellContent(x,y), c1=c2
	 */

	/** Invariant: une case non-libre ne contient rien
	 *  \forall x (x \in {0...width()-1}), 
	 *  \forall y (y \in {0...Height()-1}), CellNature(x,y) \in {MTL,PLT} \implies CellContent(x,y)=null
	 */

	/** Invariant: une case ne contient un tresor que si la case en dessous d'elle est non-libre
	 *  \forall x (x \in {0...width()-1}), \forall y (y \in {0...Height()-1}), 
	 *  	\exists Treasure t \in CellContent(x,y) \implies (CellContent(x,y)=EMP 
	 *  	\and CellContent(x,y-1) \in {PLT,MTL}) 
	 */
	public void checkInvariant() {
		super.checkInvariant();

		//Modifier pour le monteur: si le joueur sur une case contenant un garde, il a perdu

		//		for (int i = 0; i < getWidth(); i++) {
		//			for (int j = 0; j < getHeight(); j++) {
		//				if (getCellContent(i,j).contains(c1) 
		//						&& getCellContent(i,j).contains(c2)) {
		//					if (!c1.equals(c2)) {
		//						throw new InvariantError("EnvironnmentContract","\\inv une case contient au plus 1 Charracter ");
		//						break;
		//					}
		//				}
		//			}
		//		}

		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				if (CellNature(i,j)==Cell.MTL || CellNature(i,j)==Cell.PLT) {	
					if (!getCellContent(i,j).isEmpty()) {
						throw new InvariantError("EnvironnmentContract ==> \\inv une case non-libre ne contient rien ");
					}
				}
			}
		}
 
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				if (!( CellNature(i,j)==Cell.EMP && 
						(CellNature(i,j-1)==Cell.PLT || CellNature(i,j-1)==Cell.MTL))) {
					if(getCellContent(i, j).contains(item)) {
						if(getCellContent(i, j).contains(item2)) {
							throw new InvariantError("EnvironnmentContract ==> \\inv une case ne contient un tresor que si la case en dessous d'elle est non-libre ");
						}
					}
				}
			}
		}

	}

	@Override
	public void init(int x , int y , EditableScreen edit) {
		checkInvariant();
		getDelegate().init(x, y ,edit);
	}

	@Override
	public Cell CellNature(int x, int y) {
		return getDelegate().CellNature(x, y); 
	}

}
