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
        System.out.println(1);
        this.sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(true);
        System.out.println(2);
        this.currentHits = service.getUserHits(sessionId);
        System.out.println(3);
    }



    public void makeUserRequest() {
        makeRequest(this.coordinates);
    }

    public void makeRemoteRequest() {
        Function<String, Double> getParam = (name) -> {
            return Double.parseDouble(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name));
        };
        try {
            Coordinates coordinates = new Coordinates(getParam.apply("x"), getParam.apply("y"), getParam.apply("r"));
            makeRequest(coordinates);
        } catch (NullPointerException | NumberFormatException exception) {
            System.out.println("error in params");
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
