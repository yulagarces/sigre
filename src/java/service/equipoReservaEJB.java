/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.PeticionEquipo;
import model.Persona;
import model.Equipo;

/**
 *
 * @author santi
 */
@Stateless
public class equipoReservaEJB {

    @PersistenceContext
    private EntityManager em;

    public List<PeticionEquipo> getAllAvailableSchedules() {
        return em.createNamedQuery("PeticionEquipo.findAll", PeticionEquipo.class).getResultList();
    }

    public List<PeticionEquipo> getAllScheduleById() {

        Query q = em.createQuery("SELECT r FROM PeticionEquipo r " );
        return q.getResultList();
    }

    public List<PeticionEquipo> getAllScheduleByIdEquipo(Equipo a) {

        return em.createQuery("SELECT r FROM PeticionEquipo r WHERE r.equipoIdEquipo = :id").setParameter("id", a).getResultList();

    }

    public boolean save(PeticionEquipo r) {
        em.persist(r);
        em.flush();

        return r.getIdPeticionEquipo() > 0;
    }

    public boolean delete(PeticionEquipo r) {
        PeticionEquipo oldPeticion = findPeticionEquipo(r.getIdPeticionEquipo());
        Query q = em.createQuery("DELETE from PeticionEquipo p where p.idPeticionEquipo = :id ");
        q.setParameter("id", oldPeticion.getIdPeticionEquipo());
        return q.executeUpdate() > 0;

    }

    public PeticionEquipo findPeticionEquipo(int id) {
        return (PeticionEquipo) em.createNamedQuery("PeticionEquipo.findByIdPeticionEquipo").setParameter("idPeticionEquipo", id).getSingleResult();
    }

    public int countPeticionEquipo(int id) {

        return ((Number) em.createQuery("SELECT COUNT(r.idPeticionEquipo) FROM PeticionEquipo r WHERE r.idPeticionEquipo =:id").setParameter("id", id).getSingleResult()).intValue();
    }

}
