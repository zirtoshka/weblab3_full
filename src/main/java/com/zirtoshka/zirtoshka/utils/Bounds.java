package com.zirtoshka.zirtoshka.utils;
//TODO: fix bounds
public enum Bounds {
    X_BOUNDS(0 ,0 , true),
    Y_BOUNDS(0,0, true),
    R_BOUNDS(0,0, true);
    private final double left;
    private final double right;
    private final boolean inclusive;

    Bounds(double left, double right, boolean inclusive){
        this.left = left;
        this.right = right;
        this.inclusive = inclusive;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public boolean isInclusive() {
        return inclusive;
    }
}
