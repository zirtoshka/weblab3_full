package com.zirtoshka.zirtoshka.beans;

import com.zirtoshka.zirtoshka.db.HitResult;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedMap;
import java.util.function.Function;


@Getter
@Setter
@ToString
@Named("client")
@SessionScoped

public class ClientBean implements Serializable {
    private final String sessionId;
    private final LinkedList<HitResult> currentHits;

    @ManagedProperty(value = "#{coordinates}")
    private Coordinates coordinates = new Coordinates();
    @ManagedProperty(value = "#{service}")
    private Service service = new Service();

    public ClientBean() {
        this.sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);
        this.currentHits = service.getUserHits(sessionId);
    }


    public void makeUserRequest() {
        makeRequest(this.coordinates);
    }

    public void makeRemoteRequest() {
//        Function<String, Double> getParam = (name) -> {
//            return Double.parseDouble(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name));
//        };
        try {
            System.out.println(coordinates.getY() + "it is y from user");
//            Coordinates coordinates = new Coordinates(getParam.apply("x"), getParam.apply("y"), getParam.apply("r"));
            makeRequest(coordinates);
        } catch (NullPointerException | NumberFormatException exception) {
            System.out.println("error in params");
        }
    }

    public void makeCanvasRequest() {
        final Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        try {
            String xValue = params.get("x");
            String yValue = params.get("y");
            String rValue = params.get("r");

            if (xValue != null && yValue != null && rValue != null) {
                double x = Double.parseDouble(xValue.trim());
                double y = Double.parseDouble(yValue.trim());
                double r = Double.parseDouble(rValue.trim());

                coordinates.setX(x);
                coordinates.setY(y);
                coordinates.setR(r);
                makeRequest(coordinates);
            } else {
                System.out.println(xValue);
                System.out.println(yValue);
                System.out.println(rValue);
                System.out.println("ОНО ПОЧЕМУ ТО НАЛ НО ПОЧЕМУ");
            }
        } catch ( IllegalArgumentException e) {
            e.printStackTrace();
            // Обработка ошибок при парсинге или некорректных данных
        }
    }


    public void makeRequest(Coordinates coordinates) {
        System.out.println("make rq");
        HitResult res = service.processRequest(this.sessionId, coordinates);
        if (res != null) {
            this.currentHits.addFirst(res);
        }
    }


    public void clearHits() {
        currentHits.clear();
        service.clearUserHits(this.sessionId);
    }
}
