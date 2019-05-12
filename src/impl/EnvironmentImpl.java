package impl;

import java.util.HashSet;
import java.util.Map.Entry;

import javafx.util.Pair;
import services.Cell;
import services.CellContent;
import services.EditableScreen;
import services.Environment;
import services.RequireEngineService;

public class EnvironmentImpl extends ScreenImpl implements 
Environment, 
RequireEngineService{
	EngineImpl engine;
	HashSet<CellContent> cellContentList;
	EditableScreen edit;
	Cell cell;

	public EnvironmentImpl() {
		cellContentList = new HashSet<>();
	}

	@Override
	public void bindEngineService(EngineImpl service) {
		engine = service;
	} 

	@Override
	public Cell CellNature(int x, int y) {
		return edit.CellNature(x, y);
	}

	@Override
	public void Dig(int x, int y) {
		edit.Dig(x, y);
	}

	@Override
	public void Fill(int x, int y) {
		edit.Fill(x, y); 
	} 


	@Override
	public void init(int x , int y, EditableScreen edit) {
		super.Init(x, y);
		this.edit = edit;
	}

	@Override
	public HashSet<CellContent> getCellContent(int x, int y) {
		cellContentList.clear(); 
		for (Entry<CellContent, Pair<Integer, Integer>> entry 
				: engine.getCellContent().entrySet()) {
			if(entry.getValue().getKey()==x && entry.getValue().getValue()==y) {
				cellContentList.add(entry.getKey()); 
			}
		}
		return cellContentList;
	}
}
