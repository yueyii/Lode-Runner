package contract;

import java.util.HashMap;
import java.util.Map.Entry;
import contractsError.InvariantError;
import contractsError.PostconditionError;
import contractsError.PreconditionError;
import javafx.util.Pair;
import services.Cell;
import services.EditableScreen;

public class EditableScreenContract extends ScreenContract implements EditableScreen{

	public EditableScreenContract(EditableScreen delegate) {
		super(delegate);
	}

	protected EditableScreen getDelegate() {
		return (EditableScreen) super.getDelegate();
	}
	/** Invariant: mininisation de isPlayable()
	 * 	isPlayable() == (\forall x (x \in {0...width()-1}) and \forall y (y \in {0...Height()-1}) 
	 * 					 \with CellNature(x,y)!=HOL
	 * 						 \and \forall x (x \in {0...width()-1}) and \forall y (y == 0) \with CellNature(x,y)=MTL)
	 */
	public void checkInvariant() {

		super.checkInvariant();
		
	
		if(isPlayable()) {
		
			for (int i=0; i<getWidth(); i++) {
				if (CellNature(i,0)!=Cell.MTL) {
					throw new InvariantError("EditableScreenContract ==> \\inv CellNature(i,0)==Cell.MTL isPlayable() ");
				}
			}

			for (int i = 0; i < getWidth(); i++) {
				for (int j = 0; j < getHeight(); j++) {
					if (CellNature(i, j) == Cell.HOL) {
						throw new InvariantError("EditableScreenContract ==> \\inv CellNature(i, j) != Cell.HOL isPlayable() ");
					}
				}
			}
		}
	}

	/** Operateur: editer directement le contenu d'une case
	 *  pre: 0 <= y < getHeight(S) \and 0 <= x < getWidth(S)
	 *  post: CellNature(x,y) = C
	 *  post: \forall x (x \in {0...width()-1}) and \forall y (y \in {0...Height()-1}), (x!=u \or y!=y) 
	 *  		\implies CellNature(x,y)=CellNature(x,y)@pre
	 */
	public void setNature(int x , int y, Cell cell) {

		//1 Pre-condition
		if(x<0 || x >= getWidth() || y<0 || y>=getHeight()) {
			throw new PreconditionError("EditableScreenContract setNature(int x , int y, Cell cell) ==> \\pre  0 <= x < getWidth() and 0 <= y < getHeight() ");
		}

		//2 Invariant
		checkInvariant();

		//3 capture
		HashMap<Pair<Integer, Integer>,Cell> cellNatureMap_pre = new HashMap<>();

		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				cellNatureMap_pre.put(new Pair<>(i,j),CellNature(i, j));
			}
		}
		//4 Traitement
		getDelegate().setNature(x, y, cell);

		//5 Inv
		checkInvariant();

		//6 PostCondition
		if (CellNature(x,y)!=cell) {
			throw new PostconditionError("EditableScreenContract setNature(int x , int y, Cell cell) ==> \\post CellNature(x,y) = cell");	
		}

		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				for(Entry<Pair<Integer, Integer>, Cell> entry : cellNatureMap_pre.entrySet()) {
					if((entry.getKey().getKey()	== i && entry.getKey().getKey()!=x) 
							&& (entry.getKey().getValue()==j && entry.getKey().getValue()!=y)) {
						if(entry.getValue()!=CellNature(i, j)) {
							throw new PostconditionError("EditableScreenContract setNature(int x , int y, Cell cell) ==> \\post CellNature(x,y) != cell");	
						}
					}
				}
			}
		}

	}

	@Override
	public boolean isPlayable() {
		return getDelegate().isPlayable();
	}

	@Override
	public void Init(int x ,int y) {
		getDelegate().Init(x, y);
	}

}
