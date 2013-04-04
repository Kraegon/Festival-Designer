package simulator.clock;

/**
 * Time machine to keep track of and manipulate time.
 * @author Julian G. West
 */
public class Clock {
	private static Clock INSTANCE;

	private int startTime;
	private int time; //total in seconds
	private int elapseSpeed = 1000;
	private TimeElapse absoluteTimeCoreRuler;

	public Clock(){
		startTime = 0;
		absoluteTimeCoreRuler = new TimeElapse();
		absoluteTimeCoreRuler.start();
	}

	public static Clock getInstance(){
		if(INSTANCE == null)
			INSTANCE = new Clock();
		return INSTANCE;
	}
	/**
	 * Start running through time.
	 */
	public void start(){
		absoluteTimeCoreRuler.go();
	}
	/**
	 * Go back to square one.
	 */
	public void reset(){
		time = 0;
	}
	/**
	 * Za warudo.
	 */
	public void pause(){
		absoluteTimeCoreRuler.halt();
	}
	/**
	 * Go back to the start.
	 */
	public void stop(){
		pause();
		reset();
	}
	/**
	 * Set what time to start counting from. e.g. 9 * 3600 = 9 AM to start counting from.
	 * @param seconds : The time to go to in seconds.
	 */
	public void setStartTime(int startTime){
		this.startTime = startTime;
		//time = startTime;
	}
	
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Speed up or slow down the flow of time by setting the amount of milliseconds per increment. 
	 * The smaller the number the faster, a speed of 1000 is real time and negative numbers are unacceptable.
	 */
	public void setSpeed(int elapseSpeed){
		if(elapseSpeed < 1)
			elapseSpeed = 1;
		if(elapseSpeed > 5000)
			elapseSpeed = 5000;
		this.elapseSpeed = elapseSpeed;
		absoluteTimeCoreRuler.setSpeed(elapseSpeed);
	}
	/**
	 * Get the current speed at which time elapses.
	 * @return : Elapse speed.
	 */
	public int getSpeed(){
		return elapseSpeed;
	}
	/**
	 * Toggle running/not running.
	 */
	public void toggle(){
		absoluteTimeCoreRuler.toggle();
	}
	/**
	 * Defy logic and start going backwards through time.
	 */
	public void rewind(){
		absoluteTimeCoreRuler.toggleTimeDirection();
	}
	/**
	 * Increments time by one second to a maximum of an entire day's worth of seconds.
	 */
	public void incrementTime(){
		time++;
		time %= 86400 - startTime;
	}
	/**
	 * Decreases time until it reaches 0.
	 */
	public void decrementTime(){
		time--;
		time %= 0;
	}
	/**
	 * Returns the current time in seconds
	 * @return current mount of seconds passed.
	 */
	public long getCurrentTime(){
		return time + startTime;
	}
	/**
	 * Get the total amount of minutes passed.
	 * @param time : The time in seconds to get the minutes out of.
	 * @return : The amount of minutes lost in the miserable heap of seconds, rounded down.
	 */
	public int parseMinutes(long time){
		return (int) Math.floor(time / 60);
	}
	/**
	 * Get the total amount of hours passed.
	 * @param time : The time in seconds to evolve into hours.
	 * @return : Rounded down amount of hours.
	 */
	public int parseHours(long time){
		return (int) Math.floor(time / 3600);
	}
	/**
	 * Display time in String form.
	 */
	public String toString(){
		int hours = (int) Math.floor((time + startTime) / 3600);
		int minutes = (int) Math.floor(((time + startTime) % 3600) / 60);
		int seconds = (int) (((time + startTime) % 3600) % 60);
		if(minutes < 10 && seconds < 10)
			return "[" + hours + ":0" + minutes + ":0" + seconds + "]";
		else if(minutes < 10 && seconds >= 10)
			return "[" + hours + ":0" + minutes + ":" + seconds + "]";
		else if(minutes >= 10 && seconds < 10)
			return "[" + hours + ":" + minutes + ":0" + seconds + "]";
		else
			return "[" + hours + ":" + minutes + ":" + seconds + "]";
	}

}
/**
 * Private class that makes sure seconds tick away.
 * @author Julian G. West
 */
class TimeElapse extends Thread implements Runnable{

	private int elapseSpeed;
	private boolean isActive;
	private boolean isIncreasing;

	public TimeElapse(){
		elapseSpeed = 1000;
		isActive = false;
		isIncreasing = true;
	}

	public void run(){
		while(true){
			if(isActive){
				try {
					Thread.sleep(elapseSpeed);
					if(isIncreasing)
						Clock.getInstance().incrementTime();
					else
						Clock.getInstance().decrementTime();
				} catch (InterruptedException e) {
					halt();
				}
			} else {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {}
			}
		}
	}
	public void setSpeed(int elapseSpeed){
		this.elapseSpeed = elapseSpeed;
	}
	public void halt(){
		isActive = false;
	}
	public void go(){
		isActive = true;
	}
	public void toggle(){
		isActive = !isActive;
	}
	public void toggleTimeDirection(){
		isIncreasing = !isIncreasing;
	}
}