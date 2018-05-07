package org.invictus.event;

public class CycleEventContainer {

	private Object owner;

	private boolean isRunning;

	private int tick;

	private CycleEvent event;

	private int cyclesPassed;

	public CycleEventContainer(Object owner, CycleEvent event, int tick) {
		this.owner = owner;
		this.event = event;
		this.isRunning = true;
		this.cyclesPassed = 0;
		this.tick = tick;
	}

	public void execute() {
		event.execute(this);
	}

	public void stop() {
		isRunning = false;
		event.stop();
	}

	public boolean needsExecution() {
		if (++this.cyclesPassed >= tick) {
			this.cyclesPassed = 0;
			return true;
		}
		return false;
	}

	public Object getOwner() {
		return owner;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setTick(int tick) {
		this.tick = tick;
	}

}