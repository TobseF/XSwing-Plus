/*
 * @version 0.0 02.12.2008
 * @author Tobse F
 */
package lib.mylib.object;

import java.util.*;
import javax.swing.event.EventListenerList;
import org.newdawn.slick.Graphics;

public class SObjectList extends SObject implements Resetable {

	private List<SObject> objectList;
	private boolean removeFinishedObjects = true;
	private EventListenerList removeObjectListenerList = new EventListenerList();

	public boolean isRemoveFinishedObjects() {
		return removeFinishedObjects;
	}

	public void setRemoveFinishedObjects(boolean removeFinishedObjects) {
		this.removeFinishedObjects = removeFinishedObjects;
	}

	public SObjectList() {
		objectList = new ArrayList<SObject>();
	}

	@Override
	public void reset() {
		for (SObject sObject : objectList) {
			if (sObject instanceof Resetable) {
				((Resetable) sObject).reset();
			}
		}
	}

	public void clear() {
		objectList.clear();
	}

	@Override
	public void render(Graphics g) {
		for (SObject sObject : objectList) {
			sObject.render(g);
		}
	}

	public int size() {
		return objectList.size();
	}

	public SObject get(int index) {
		return objectList.get(index);
	}

	public void add(SObject object) { // TODO: add layers
		objectList.add(object);
	}

	public void remove(int index) {
		objectList.remove(index);
	}

	public void remove(SObject object) {
		objectList.remove(object);
	}

	@Override
	public void update(int delta) {
		for (int i = 0; i < objectList.size(); i++) {
			if (removeFinishedObjects && objectList.get(i).isFinished()) {
				notifyRemoveObjectListener(objectList.get(i));
				objectList.remove(i);
			} else {
				objectList.get(i).update(delta);
			}
		}
	}

	private void notifyRemoveObjectListener(SObject removedSObject) {
		for (RemoveObjectListener removeObjectListener : removeObjectListenerList
				.getListeners(RemoveObjectListener.class)) {
			removeObjectListener.removedObecect(new SObjectRemovedEvent(this, removedSObject));
		}
	}

	public void addRemoveObjectListener(RemoveObjectListener removeObjectListener) {
		removeObjectListenerList.add(RemoveObjectListener.class, removeObjectListener);
	}

	public List<SObject> getObjectList() {
		return objectList;
	}
	
	@Override
	public void setPos(int x, int y) {
		for (SObject sObject : objectList) {
			sObject.setPos(x, y);
		}
	}
	
	@Override
	public void translate(int x, int y) {
		super.translate(x, y);
		for(SObject o:objectList){
			o.translate(x, y);
		}
	}
}