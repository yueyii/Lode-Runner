package services;

public interface Holes {
	public Environment getEnvi();
	public int getHoleHgt();
	public int getHoleCol();
	public int getTime();
	public void init(Screen screen, int x, int y, int t);
	
}
