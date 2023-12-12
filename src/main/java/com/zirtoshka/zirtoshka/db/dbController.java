package com.zirtoshka.zirtoshka.db;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

@Named
@ApplicationScoped
public class dbController {
    private SessionFactory factory;
    private Session session;
    //TODO: fix sout

    public dbController(){
        try {
            this.factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(HitResult.class).buildSessionFactory();
            this.createSession();
        } catch (Exception e) {
            System.out.println("idi naxyu 4ert");
        }
    }
    private void createSession() {this.session = factory.getCurrentSession();}

    public List<HitResult> getUserHits(String sessiondId){
        if (sessiondId == null) return  null;
        createSession();
        this.session.beginTransaction();
        String sqlRequest = "SELECT hit FROM HitReuslt hit WHERE hit.sessionId= :sessiondId AND hit.removed=false";
        List<HitResult> results = this.session.createQuery(sqlRequest, HitResult.class).setParameter("sessionId", sessiondId).getResultList();
        this.session.getTransaction().commit();
        System.out.println("get hits from db: "+results.size());
        return results;
    }
    public void addHitResult(HitResult hitresult){
        if (hitresult == null) return;
        createSession();
        this.session.beginTransaction();
        this.session.save(hitresult);
        this.session.getTransaction().commit();
    }
    //TODO: fix DB props and create DB local and helios
    public void markUserHitsRemoved(String sessionId){
        if (sessionId == null) return;
        createSession();
        this.session.beginTransaction();
        this.session.createQuery("UPDATE HitResult SET removed=true WHERE sessionId= :sessionId").setParameter("sessionId", sessionId).executeUpdate();
        this.session.getTransaction().commit();
    }
}
