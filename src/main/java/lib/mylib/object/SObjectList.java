/*
 * @version 0.0 02.12.2008
 * @author Tobse F
 */
package lib.mylib.object;

import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SObjectList extends SObject implements Resetable {

    private final List<SObject> objectList;
    private boolean removeFinishedObjects = true;


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

    public void add(SObject... objects) { // TODO: add layers
        Collections.addAll(objectList, objects);
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

    List<RemoveObjectListener> removeObjectListenerList = new LinkedList<RemoveObjectListener>();

    private void notifyRemoveObjectListener(SObject removedSObject) {
        for (RemoveObjectListener removeObjectListener : removeObjectListenerList) {
            removeObjectListener.removedObecect(new SObjectRemovedEvent(this, removedSObject));
        }
    }

    public void addRemoveObjectListener(RemoveObjectListener removeObjectListener) {
        removeObjectListenerList.add(removeObjectListener);
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
        for (SObject o : objectList) {
            o.translate(x, y);
        }
    }
}