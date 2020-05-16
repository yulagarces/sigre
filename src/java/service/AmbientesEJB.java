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

/**
 *
 * @author fabian
 */
@Stateless
public class AmbientesEJB {

    @PersistenceContext
    private EntityManager em; // pone a disposicion JPA

    public List<Ambiente> getAllAvailableAmbientes() {
        return em.createNamedQuery("Ambiente.findAll", Ambiente.class).getResultList();
    }

    public Ambiente getUserById(Integer id) {
        return em.createNamedQuery("Ambiente.findByIdAmbiente", Ambiente.class).setParameter("idAmbiente", id).getSingleResult();
    }

    public void createAmbiente(Ambiente a) {
        em.persist(a);

    }

    public List<Ambiente> findAll() {
        return em.createNamedQuery("Ambiente.findAll").getResultList();
    }

    public Ambiente updateAmbiente(Ambiente newAmbiente) {

        Ambiente oldAmbiente = findAmbiente(newAmbiente.getIdAmbiente());

        Ambiente result = compareAmbiente(oldAmbiente, newAmbiente);

        if (result.getIdAmbiente() != null) {
            return em.merge(result);
        } else {
            return result;
        }

    }

    public Ambiente findAmbiente(int id) {
        return (Ambiente) em.createNamedQuery("Ambiente.findByIdAmbiente").setParameter("idAmbiente", id).getSingleResult();
    }

    private Ambiente compareAmbiente(Ambiente oldAmbiente, Ambiente newAmbiente) {

        if (oldAmbiente.getIdAmbiente() == newAmbiente.getIdAmbiente()) {
            Ambiente result = new Ambiente();
            result.setIdAmbiente(newAmbiente.getIdAmbiente());
            // comprar los demas datos
            if (!oldAmbiente.getNombre().equals(newAmbiente.getNombre())) {
                result.setNombre(newAmbiente.getNombre());
            } else {
                result.setNombre(oldAmbiente.getNombre());
            }

            if (oldAmbiente.getCapacidad() == newAmbiente.getCapacidad()) {
                result.setCapacidad(newAmbiente.getCapacidad());
            } else {
                result.setCapacidad(oldAmbiente.getCapacidad());
            }

            if (!oldAmbiente.getTipo().equals(newAmbiente.getTipo())) {
                result.setTipo(newAmbiente.getTipo());
            } else {
                result.setTipo(oldAmbiente.getTipo());
            }
            if (!oldAmbiente.getDescripcion().equals(newAmbiente.getDescripcion())) {
                result.setDescripcion(newAmbiente.getDescripcion());
            } else {
                result.setDescripcion(oldAmbiente.getDescripcion());
            }
            if (!oldAmbiente.getEquipos().equals(newAmbiente.getEquipos())) {
                result.setEquipos(newAmbiente.getEquipos());
            } else {
                result.setEquipos(oldAmbiente.getEquipos());
            }

            return result;
        } else {
            return new Ambiente();
        }

    }

    public boolean deleteAmbiente(Ambiente a) {
        Ambiente oldAmbiente = findAmbiente(a.getIdAmbiente());

        em.remove(oldAmbiente);

        return countAmbiente(a.getIdAmbiente()) == 0;

    }

    public int countAmbiente(int id) {

        return ((Number) em.createQuery("SELECT COUNT(a.idAmbiente) FROM Ambiente a WHERE a.idAmbiente =:id").setParameter("id", id).getSingleResult()).intValue();
    }

    public int countAmbientes() {

        return ((Number) em.createQuery("Select count(a.idAmbiente)from Ambiente a").getSingleResult()).intValue();
    }

}
