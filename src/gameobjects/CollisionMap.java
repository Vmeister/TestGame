/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameobjects;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Ville
 */
public class CollisionMap {
    HashMap<GameObject, double[]> startXY;
    HashMap<GameObject, double[]> endXY;
    Map parentMap;
    public CollisionMap(Map parentMap) {
        this.startXY = new HashMap<>();
        this.endXY = new HashMap<>();
        this.parentMap = parentMap;
    }
    
    public void addToCollisionMap(double startX, double startY, double endX, 
      double endY, GameObject object) {
        double[] startXY2 = new double[2];
        startXY2[0] = startX;
        startXY2[1] = startY;
        double[] endXY2 = new double[2];
        endXY2[0] = endX;
        endXY2[1]= endY;
        startXY.put(object, startXY2);
        endXY.put(object, endXY2);
        
    }
    
    public void removeFromCollisionMap(GameObject object) {
        Entry removableStartEntry = null;
        for(Entry entry : startXY.entrySet()) {
            if(entry.getKey().equals(object)) {
                removableStartEntry = entry;
                break;
            }
            
        }
        Entry removableEndEntry = null;
        for(Entry entry : endXY.entrySet()) {
            if(entry.getKey().equals(object)) {
                removableEndEntry = entry;
                break;
            }
        }
        startXY.remove(removableStartEntry.getKey(), removableStartEntry.getValue());
        endXY.remove(removableEndEntry.getKey(), removableEndEntry.getValue());
    }
    
    public boolean checkCollisionNorth(double[] start, double[] end, GameObject movingObject) {
        boolean canmove = true;
        for(double[] someEndPos : endXY.values()) {
            if(someEndPos[0] >= start[0] && someEndPos[1] >= start[1]) {
                GameObject object = getObjectByEndPos(someEndPos);
                if(!object.equals(movingObject)) {
                    double[] lowerLeftPos = new double[2];
                    double[] startPos = findStartPosByObject(object);
                    double[] endPos = findEndPosByObject(object);
                    lowerLeftPos[0] = startPos[0];
                    lowerLeftPos[1] = endPos[1];
                    if(lowerLeftPos[0] <= end[0] && lowerLeftPos[1] <= end[1]) {
                        if(object.getClass().equals(LayerChanger.class)) {
                            ((LayerChanger)object).changeLayerNorth(movingObject);
                            parentMap.changeLayer(movingObject, ((LayerChanger)object).getNorth());
                        }
                        if(!object.allowCollision(movingObject)) {
                            canmove = false;
                        }
                    }
                }
            }
        }

        return canmove;
    }
    
    public boolean checkCollisionSouth(double[] start, double[] end, GameObject movingObject) {
        boolean canmove = true;
        for(double[] someStartPos : startXY.values()) {
            if(someStartPos[0] <= end[0] && someStartPos[1] <=end[1]) {
                GameObject object = getObjectByStartPos(someStartPos);
                if(!object.equals(movingObject)) {
                    double[] someUpperRightCorner = new double[2];
                    double[] startPos = findStartPosByObject(object);
                    double[] endPos = findEndPosByObject(object);
                    someUpperRightCorner[0] = endPos[0];
                    someUpperRightCorner[1] = startPos[1];
                    if(someUpperRightCorner[0] >= start[0] && someUpperRightCorner[1] >= start[1]) {
                        if(object.getClass().equals(LayerChanger.class)) {
                            ((LayerChanger)object).changeLayerSouth(movingObject);
                            parentMap.changeLayer(movingObject, ((LayerChanger)object).getSouth());
                        }
                        if(!object.allowCollision(movingObject)) {
                           canmove = false;
                        }
                    }
                }
            }
        }
        return canmove;
    }
    
    public boolean checkCollisionEast(double[] start, double[] end, GameObject movingObject) {
        boolean canmove = true;
        double[] someEndValue = null;
        for(double[] someEndPos : endXY.values()) {
            if(start[0] <= someEndPos[0] && start[1] <= someEndPos[1]) {
                GameObject object = getObjectByEndPos(someEndPos);
                if(!object.equals(movingObject)) {
                    double[] startingPoint = findStartPosByObject(object);
                    if(end[0] >= startingPoint[0] && end[1] >= startingPoint[1]) {
                        if(object.getClass().equals(LayerChanger.class)) {
                            ((LayerChanger)object).changeLayerEast(movingObject);
                            parentMap.changeLayer(movingObject, ((LayerChanger)object).getEast());
                        }
                        if(!object.allowCollision(movingObject))
                            canmove = false;
                    }
                }
            }
        }
        return canmove;
    }
    
    public boolean checkCollisionWest(double[] start, double[] end, GameObject movingObject) {
        boolean canmove = true;
        double[] someStartValue = null;
        for(double[] someStartPos : startXY.values()) {
            if(someStartPos[0] <= end[0] && someStartPos[1] <=end[1]) {
                someStartValue = someStartPos;
                GameObject object = getObjectByStartPos(someStartValue);
                if(!object.equals(movingObject)) {
                   double[] someEndValue = findEndPosByObject(object);
                   if(someEndValue[0] >= start[0] && someEndValue[1] >= start[1]) {
                        if(object.getClass().equals(LayerChanger.class)) {
                            ((LayerChanger)object).changeLayerWest(movingObject);
                            parentMap.changeLayer(movingObject, ((LayerChanger)object).getWest());
                        }
                       if(!object.allowCollision(movingObject))
                           canmove = false;
                   }
               }
            }
        } 
        return canmove;
    }
    
    public void updateCollisionPos(Sprite object) {
        if(!object.collisionSet) {
            double[] newStartXY = new double[2];
            newStartXY[0] = object.getLayoutX();
            newStartXY[1] = object.getLayoutY();
            double[] newEndXY = new double[2];
            newEndXY[0] = object.getLayoutX() + object.width;
            newEndXY[1] = object.getLayoutY() + object.height;
            startXY.replace(object, newStartXY);
            endXY.replace(object, newEndXY);
            
        } else {
            double[] newStartXY = object.getCollisionStart();
            double[] newEndXY = object.getCollisionEnd();
            startXY.replace(object, newStartXY);
            endXY.replace(object, newEndXY);
        }
    }

    public GameObject getObjectByStartPos(double[] pos) {
        for (Entry<GameObject,double[]> entry : startXY.entrySet()) {
            if (Arrays.equals(pos, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public GameObject getObjectByEndPos(double[] pos) {
        for (Entry<GameObject,double[]> entry : endXY.entrySet()) {
            if (Arrays.equals(pos, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    public double[] findStartPosByObject(GameObject object) {
        for (Entry<GameObject,double[]> entry : startXY.entrySet()) {
            if (entry.getKey().equals(object))
                return entry.getValue();
        }
        return null;
    }
    public double[] findEndPosByObject(GameObject object) {
        for (Entry<GameObject,double[]> entry : endXY.entrySet()) {
            if (entry.getKey().equals(object))
                return entry.getValue();
        }
        return null;
    }
    
    public void printCollisionBlocks() {
        System.out.println("Blocked areas: ");
        for(Entry<GameObject, double[]> entry : startXY.entrySet()) {
            for(Entry<GameObject, double[]> entry2: endXY.entrySet()) {
                if(entry.getKey().equals(entry2.getKey())) {
                    double[] start = entry.getValue();
                    double[] end = entry2.getValue();
                    System.out.println("start: (" + start[0] +","+start[1] + "),end: (" + end[0] +"," + end[1] +")");
                }
            }
        }
    }

    public GameObject findCollidingObjectNorth(double[] start, double[] end, GameObject movingObject) {
        for(double[] someEndPos : endXY.values()) {
            if(someEndPos[0] >= start[0] && someEndPos[1] >= start[1]) {
                GameObject object = getObjectByEndPos(someEndPos);
                if(!object.equals(movingObject)) {
                    double[] lowerLeftPos = new double[2];
                    double[] startPos = findStartPosByObject(object);
                    double[] endPos = findEndPosByObject(object);
                    lowerLeftPos[0] = startPos[0];
                    lowerLeftPos[1] = endPos[1];
                    if(lowerLeftPos[0] <= end[0] && lowerLeftPos[1] <= end[1]) {
                        return object;
                    }
                }
            }
        }
        return null;
    }
    
    public GameObject findCollidingObjectEast(double[] start, double[] end, GameObject movingObject) {
        double[] someEndValue = null;
        for(double[] someEndPos : endXY.values()) {
            if(start[0] <= someEndPos[0] && start[1] <= someEndPos[1]) {
                GameObject object = getObjectByEndPos(someEndPos);
                if(!object.equals(movingObject)) {
                    double[] startingPoint = findStartPosByObject(object);
                    if(end[0] >= startingPoint[0] && end[1] >= startingPoint[1]) {
                        return object;
                    }
                }
            }
        }
        return null;
    }
    
    public GameObject findCollidingObjectSouth(double[] start, double[] end, GameObject movingObject) {
        for(double[] someStartPos : startXY.values()) {
            if(someStartPos[0] <= end[0] && someStartPos[1] <=end[1]) {
                GameObject object = getObjectByStartPos(someStartPos);
                if(!object.equals(movingObject)) {
                    double[] someUpperRightCorner = new double[2];
                    double[] startPos = findStartPosByObject(object);
                    double[] endPos = findEndPosByObject(object);
                    someUpperRightCorner[0] = endPos[0];
                    someUpperRightCorner[1] = startPos[1];
                    if(someUpperRightCorner[0] >= start[0] && someUpperRightCorner[1] >= start[1]) {
                        return object;
                    }
                }
            }
        }
        return null;
    }
    
    public GameObject findCollidingObjectWest(double[] start, double[] end, GameObject movingObject) {
        double[] someStartValue = null;
        for(double[] someStartPos : startXY.values()) {
            if(someStartPos[0] <= end[0] && someStartPos[1] <=end[1]) {
                someStartValue = someStartPos;
                GameObject object = getObjectByStartPos(someStartValue);
                if(!object.equals(movingObject)) {
                    double[] someEndValue = findEndPosByObject(object);
                    if(someEndValue[0] >= start[0] && someEndValue[1] >= start[1]) {
                        return object;
                    }
                }
            }
        } 
        return null;
    }
}
