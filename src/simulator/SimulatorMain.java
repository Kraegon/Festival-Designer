package simulator;
/**
 * Volledige revisie. Copy paste.
 * @author Julian G. West
 *
 */
public class SimulatorMain extends Thread implements Runnable{
	private static SimulatorMain INSTANCE;
	private Designer gui;
	
	public void run(){
		gui = new Designer();
	}
	
	public static SimulatorMain getInstance(){
		if(INSTANCE == null){
			INSTANCE = new SimulatorMain();
			INSTANCE.start();
		}	
		return INSTANCE;
	}
	public Designer getDesigner(){
		return gui;
	}
}
