package com.zirtoshka.zirtoshka.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Named
@RequestScoped
public class Coordinates implements Serializable {
    private double x;
    private double y;
    private double r;
    @ToString.Exclude
    private final double[] xValues = {-5, -4, -3, -2, -1, 0, 1, 2, 3};
}
