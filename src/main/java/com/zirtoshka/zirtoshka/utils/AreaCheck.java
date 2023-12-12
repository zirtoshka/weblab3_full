package com.zirtoshka.zirtoshka.utils;

import com.zirtoshka.zirtoshka.beans.Coordinates;
//TODO:fix figures
public class AreaCheck {
    public boolean isHit(Coordinates coordinates){
        return coordinates != null && isHit(coordinates.getX() , coordinates.getY(), coordinates.getR());
    }
    public boolean isHit(double x , double y, double r) {
        return isFigureOneHit(x,y,r) || isFigureTwoHit(x,y,r) || isFigureThreeHit(x,y,r);
    }
    private boolean isFigureOneHit(double x, double y, double r) {return (x <= 0 && y <=0);}
    private boolean isFigureTwoHit(double x, double y, double r) {return (x <= 0 && y <=0);}
    private boolean isFigureThreeHit(double x, double y, double r) {return (x <= 0 && y <=0);}
}
