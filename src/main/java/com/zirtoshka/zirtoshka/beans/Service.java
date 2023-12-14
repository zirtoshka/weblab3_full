package com.zirtoshka.zirtoshka.beans;

import com.zirtoshka.zirtoshka.db.HitResult;
import com.zirtoshka.zirtoshka.db.dbController;
import com.zirtoshka.zirtoshka.utils.AreaCheck;
import com.zirtoshka.zirtoshka.utils.Validation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@ApplicationScoped
@Named("service")
public class Service implements Serializable {
    private final AreaCheck areaCheck;
    private final dbController dbController;

    public Service() {
        areaCheck = new AreaCheck();
        dbController = new dbController();
    }
    public LinkedList<HitResult> getUserHits(String sessionId) {
        System.out.println(4);
        if (sessionId == null) return new LinkedList<>();
        System.out.println(5);
        List<HitResult> hits = dbController.getUserHits(sessionId);
        System.out.println("userhits" + hits);
        return hits != null ? new LinkedList<>(hits) : new LinkedList<>();
    }
    public HitResult processRequest(String sessionId, Coordinates coordinates) {
        if (!Validation.validate(coordinates)) {
            System.out.println("Not valid");
            return null;
        }
        boolean isHit = areaCheck.isHit(coordinates);
        HitResult hitResult = new HitResult(sessionId, coordinates, getCurrentDate(), isHit);
        dbController.addHitResult(hitResult);
        return hitResult;
    }

    public void clearUserHits(String sessionId) {dbController.markUserHitsRemoved(sessionId);}
    public String getCurrentDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

}
