package org.invictus.event;

public abstract class CycleEvent {

	public abstract void execute(CycleEventContainer container);

	public abstract void stop();

}