package com.fortum.nokid.entities;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by yuriyarabskyy on 22/05/16.
 */

@Repository
public class VorlesungRepositoryImpl implements VorlesungPDFDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public VorlesungRepositoryImpl() { }

    public VorlesungRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void insertPdf(VorlesungPdf pdf) {

        sessionFactory.getCurrentSession().save(pdf);

    }

    @Transactional
    public VorlesungPdf getPdfByName(String name) {

        String sql = "select * from Vorlesungen where name = :name";

        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        query.addEntity(VorlesungPdf.class);

        query.setParameter("name", name);

        List<VorlesungPdf> list = query.list();

        if (!list.isEmpty()) return list.get(0);

        return null;

    }

}
