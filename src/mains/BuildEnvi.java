package mains;

import services.Cell;
import services.EditableScreen;

public class BuildEnvi {
//	EditableScreenImpl editable = new EditableScreenImpl();
//	EditableScreenContract editableContract = new EditableScreenContract(editable);
// 
	public EditableScreen buildEnvi(EditableScreen editableContract) {
		
		//d¨¦finie les plateformes
		for (int i = 0; i < editableContract.getWidth(); i++) {
			for (int j = 0; j < 2 ; j++) {
				editableContract.setNature(i, j, Cell.PLT);
			
			}
		}
		for (int i = 0; i < 6; i++) {
			for (int j = 5; j < 7 ; j++) {
				editableContract.setNature(i, j, Cell.PLT);
			}
			
			for (int j = 9; j < 11 ; j++) {
				editableContract.setNature(i, j, Cell.PLT); 
			}
		}
		
		 
		for (int i = 11; i < editableContract.getWidth(); i++) {
			for (int j = 4; j < 6; j++) {
				editableContract.setNature(i, j, Cell.PLT);
			}
		}
		
		for (int i = 12; i < editableContract.getWidth() ; i++) {
			for (int j = 10 ; j < 12; j++) {
				editableContract.setNature(i, j, Cell.PLT);
			}
		}
		
		//d¨¦finie le rail
		for (int i = 7; i < 11 ; i++) {
			editableContract.setNature(i, 7, Cell.HDR);
		}
		
		//d¨¦finir l'¨¦chelle
		for (int j = 2 ; j < 7 ; j++) {
			editableContract.setNature(3, j, Cell.LAD);
		}
		
		for (int j = 2 ; j < editableContract.getHeight() ; j++) {
			editableContract.setNature(6, j, Cell.LAD);
		}
		
		for (int j = 6 ; j < 12 ; j++) {
			editableContract.setNature(11, j, Cell.LAD);
		}
		return editableContract;
	}
}
