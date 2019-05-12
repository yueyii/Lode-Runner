package facade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import services.Cell;
import services.EditableScreen;

public class BuildEnvi {

	public EditableScreen buildEnvi2(int level, EditableScreen editableContract) {
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader("src/levels/level" + level + ".lvl"));
			//pour hauteur 
			for (int i = 0; i < editableContract.getHeight(); i++)  
			{
				char[] line = reader.readLine().toCharArray();
				
				for (int k = 0; k < editableContract.getWidth(); k++)
				{	if(line[k]=='0') {
					editableContract.setNature(k, editableContract.getHeight()-1-i, Cell.EMP);	
					}
					else if(line[k]=='1') { 
						editableContract.setNature(k, editableContract.getHeight()-1-i, Cell.MTL);
					}
					else if(line[k]=='2') {
						editableContract.setNature(k, editableContract.getHeight()-1-i, Cell.PLT);	
					}
					else if(line[k]=='3') {
						editableContract.setNature(k,editableContract.getHeight()-1-i, Cell.LAD);		
					}
					else if(line[k]=='4') {
						editableContract.setNature(k,editableContract.getHeight()-1-i, Cell.HDR);		
					}
				}
			}
		} 
		catch (IOException ex) {}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException ex) {}
			}
		}
		return editableContract;

	}
}
