package com.zirtoshka.zirtoshka.db;

import com.zirtoshka.zirtoshka.beans.Coordinates;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "HitResult")
public class HitRRResult implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Integer id;
    private String sessionId;
    private double x;
    private double y;
    private double r;
    private String currentTime;
    private boolean result;
    private boolean removed = false;
    public HitRRResult(String sessionId, Coordinates coordinates, String currentTime, boolean result){
        this.sessionId = sessionId;
        this.x = coordinates.getX();
        this.y = coordinates.getY();
        this.r = coordinates.getR();
        this.currentTime = currentTime;
        this.result = result;
    }
}
