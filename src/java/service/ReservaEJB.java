/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Ambiente;
import model.Persona;
import model.Reserva;


/**
 *
 * @author danny
 */
@Stateless
public class ReservaEJB {

    @PersistenceContext
    private EntityManager em;

    public List<Reserva> getAllAvailableSchedules() {
        return em.createNamedQuery("Reserva.findAll", Reserva.class).getResultList();
    }

    public List<Reserva> getAllScheduleById(Persona per) {

        return em.createQuery("SELECT r FROM Reserva r WHERE r.personaIdusuario = :id").setParameter("id", per).getResultList();

    }
    public List<Reserva> getAllScheduleByIdAmbiente(Ambiente a) {

        return em.createQuery("SELECT r FROM Reserva r WHERE r.ambienteIdAmbiente = :id").setParameter("id", a).getResultList();

    }

    public boolean save(Reserva r) {
        em.persist(r);
        em.flush();

        return r.getIdReserva() > 0;
    }

    public boolean delete(Reserva r) {
        Reserva oldReserva = findReserva(r.getIdReserva());
        em.remove(oldReserva);
        return countReserva(r.getIdReserva()) == 0;

    }

    public Reserva findReserva(int id) {
        return (Reserva) em.createNamedQuery("Reserva.findByIdReserva").setParameter("idReserva", id).getSingleResult();
    }

    public int countReserva(int id) {

        return ((Number) em.createQuery("SELECT COUNT(r.idReserva) FROM Reserva r WHERE r.idReserva =:id").setParameter("id", id).getSingleResult()).intValue();
    }

}
