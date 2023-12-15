package com.zirtoshka.zirtoshka.db;

import com.zirtoshka.zirtoshka.beans.Coordinates;

import javax.persistence.*;

import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hit_result")
public class HitResult implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Integer id;
    @Column(nullable = false, name = "session_id")
    private String sessionId;
    @Column(nullable = false)
    private double x;
    @Column(nullable = false)
    private double y;
    @Column(nullable = false)
    private double r;
    @Column(nullable = false, name = "cr_time")
    private String currentTime;
    @Column(nullable = false)
    private boolean result;
    @Column(nullable = false)
    private boolean removed = false;

    //    public HitResult(String sessionId, Coordinates coordinates, String currentTime, boolean result){
    public HitResult(String sessionId, Coordinates coordinates, boolean result, String currentTime) {

        this.sessionId = sessionId;
        this.x = coordinates.getX();
        this.y = coordinates.getY();
        this.r = coordinates.getR();
        this.currentTime = currentTime;
        this.result = result;
    }

//    @PrePersist
//    protected void prePersist() {
//        this.currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
//        this.result = checkHit();
//    }

    private boolean checkHit() {
        boolean area1_hit = x >= 0 && y >= 0 && x <= r && y <= r / 2;
        boolean area2_hit = x >= 0 && y <= 0 && y >= x - r / 2;
        boolean area3_hit = x <= 0 && y >= 0 && x * x + y * y <= r * r;
        return area1_hit || area2_hit || area3_hit;
    }
}
