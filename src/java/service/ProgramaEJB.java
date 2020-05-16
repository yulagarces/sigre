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
import model.Programa;

/**
 *
 * @author Yulieth
 */
@Stateless
public class ProgramaEJB {

    @PersistenceContext
    private EntityManager em;

    public List<Programa> listarProgramas() {
        return em.createNamedQuery("Programa.findAll", Programa.class).getResultList();
    }

    public boolean crearPrograma(Programa programa) {
        int countOld = contarProgramas();
        em.persist(programa);
        int countNew = contarProgramas();
        if (countNew > countOld) {
            return true;
        } else {
            return false;
        }

    }

    public List<Programa> consultarProgramas() {
        return em.createQuery("Select p from Programa p", Programa.class).getResultList();
    }

    public List<Programa> findAll() {
        return em.createNamedQuery("Programa.findAll").getResultList();
    }

    public Programa updatePrograma(Programa newPrograma) {
        Programa oldPrograma = findPrograma(newPrograma.getIdPrograma());
        Programa result = comparePrograma(oldPrograma, newPrograma);

        if (result.getIdPrograma() != null) {
            return em.merge(result);
        } else {
            return result;
        }
    }

    public Programa findPrograma(int idPrograma) {
        return (Programa) em.createNamedQuery("Programa.findByIdPrograma").setParameter("idPrograma", idPrograma).getSingleResult();
    }

    private Programa comparePrograma(Programa oldPrograma, Programa newPrograma) {
        if (oldPrograma.getIdPrograma() == newPrograma.getIdPrograma()) {
            Programa result = new Programa();
            result.setIdPrograma(newPrograma.getIdPrograma());
            // comparar los demas datos

            if (!oldPrograma.getNombre().equals(newPrograma.getNombre())) {
                result.setNombre(newPrograma.getNombre());
            } else {
                result.setNombre(oldPrograma.getNombre());
            }

            if (!oldPrograma.getTipoPrograma().equals(newPrograma.getTipoPrograma())) {
                result.setTipoPrograma(newPrograma.getTipoPrograma());
            } else {
                result.setTipoPrograma(oldPrograma.getTipoPrograma());
            }

            return result;
        } else {
            return new Programa();
        }

    }

    public boolean deletePrograma(Programa p) {
        Programa oldPrograma = findPrograma(p.getIdPrograma());

        em.remove(oldPrograma);

        return countProgramas(p.getIdPrograma()) == 0;
    }

    public int countProgramas(int idPrograma) {
        return ((Number) em.createQuery("Select Count( p.idPrograma) from Programa p where p.idPrograma = :idPrograma").setParameter("idPrograma", idPrograma).getSingleResult()).intValue();
    }

    public int contarProgramas() {
        return ((Number) em.createQuery("Select count(p.idPrograma)from Programa p").getSingleResult()).intValue();
    }

}
