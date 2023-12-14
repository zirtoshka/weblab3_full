package com.zirtoshka.zirtoshka.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class dbController {
    private SessionFactory factory;
    private Session session;
    //TODO: fix sout

    public dbController(){
        try {
            this.factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(HitRRResult.class).buildSessionFactory();
            this.createSession();
        } catch (Exception e) {
            System.out.println("db error");
        }
    }
//    private void createSession() {this.session = factory.openSession();}
    private void createSession() {this.session = factory.getCurrentSession();}


    public List<HitRRResult> getUserHits(String sessionId){
        if (sessionId == null) return  null;
        createSession();
        this.session.beginTransaction();
//        String sqlRequest = "FROM HitRRResult hit WHERE hit.sessionId= :sessionId AND hit.removed=false";
        List<HitRRResult> results = this.session.createQuery("from HitRRResult hit", HitRRResult.class).getResultList();

//        List<HitRRResult> results = this.session.createQuery("FROM HitRRResult hit WHERE hit.sessionId= :sessionId AND hit.removed=false", HitRRResult.class).setParameter("sessionId", sessionId).getResultList();
        this.session.getTransaction().commit();
        System.out.println("get hits from db: "+results.size());
        return results;
    }
    public void addHitResult(HitRRResult hitresult){
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
        this.session.createQuery("update HitRRResult set removed=true where sessionId= :sessionId").setParameter("sessionId", sessionId).executeUpdate();
        this.session.getTransaction().commit();
    }
}
