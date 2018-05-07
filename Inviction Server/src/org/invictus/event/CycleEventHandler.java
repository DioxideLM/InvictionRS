package org.invictus.event;

import java.util.ArrayList;
import java.util.List;

public class CycleEventHandler {

	private static CycleEventHandler instance;

	public static CycleEventHandler getSingleton() {
		if (instance == null) {
			instance = new CycleEventHandler();
		}
		return instance;
	}

	private List<CycleEventContainer> events;

	public CycleEventHandler() {
		this.events = new ArrayList<CycleEventContainer>();
	}

	public void addEvent(Object owner, CycleEvent event, int cycles) {
		this.events.add(new CycleEventContainer(owner, event, cycles));
	}

	public void process() {
		List<CycleEventContainer> eventsCopy = new ArrayList<CycleEventContainer>(events);
		List<CycleEventContainer> remove = new ArrayList<CycleEventContainer>();
		for (CycleEventContainer c : eventsCopy) {
			if (c != null) {
				if (c.needsExecution()) {
					c.execute();
				}
				if (!c.isRunning()) {
					remove.add(c);
				}
			}
		}
		for (CycleEventContainer c : remove) {
			events.remove(c);
		}
	}

	public int getEventsCount() {
		return this.events.size();
	}

	public void stopEvents(Object owner) {
		for (CycleEventContainer c : events) {
			if (c.getOwner() == owner) {
				c.stop();
			}
		}
	}

}