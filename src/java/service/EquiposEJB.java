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
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import model.Equipo;
import model.dto.EquiposDTO;
import model.Ambiente;

/**
 *
 * @author fabian
 */
@Stateless
public class EquiposEJB {

    @PersistenceContext
    private EntityManager em; // pone a disposicion JPA

    public List<Equipo> getAllAvailableEquipos() {
        return em.createNamedQuery("Equipo.findAll", Equipo.class).getResultList();
    }

    public Equipo getUserById(Integer id) {
        return em.createNamedQuery("Equipo.findByIdEquipo", Equipo.class).setParameter("idEquipo", id).getSingleResult();
    }

//    public void createEquipo(Equipo e) {
//        em.persist(e);
//    }
    
    
    
    public int createEquipo(EquiposDTO dto) {

        Equipo a = new Equipo();
        
        a.setNombre(dto.getEquipo().getNombre().toUpperCase());
        a.setCodigo(dto.getEquipo().getCodigo().toUpperCase());
        a.setDescripcion(dto.getEquipo().getDescripcion().toUpperCase());
        a.setAmbienteIdAmbiente(dto.getAmbiente());


        try {

            em.persist(a);
            return 1;
        } catch (PersistenceException f) {

            return 0;
        }

    }
    
    

    public List<Equipo> findAll() {
        return em.createNamedQuery("Equipo.findAll").getResultList();
    }

    public Equipo updateEquipo(Equipo newEquipo) {

        Equipo oldEquipo = findEquipo(newEquipo.getIdEquipo());

        Equipo result = compareEquipo(oldEquipo, newEquipo);

        if (result.getIdEquipo() != null) {
            return em.merge(result);
        } else {
            return result;
        }

    }

    public Equipo findEquipo(int idEquipo) {
        return (Equipo) em.createNamedQuery("Equipo.findByIdEquipo").setParameter("idEquipo", idEquipo).getSingleResult();
    }

    private Equipo compareEquipo(Equipo oldEquipo, Equipo newEquipo) {

        if (oldEquipo.getIdEquipo() == newEquipo.getIdEquipo()) {
            Equipo result = new Equipo();
            result.setIdEquipo(newEquipo.getIdEquipo());
            // comprar los demas datos
            if (!oldEquipo.getNombre().equals(newEquipo.getNombre())) {
                result.setNombre(newEquipo.getNombre());
            } else {
                result.setNombre(oldEquipo.getNombre());
            }

            if (!oldEquipo.getCodigo().equals(newEquipo.getCodigo())) {
                result.setCodigo(newEquipo.getCodigo());
            } else {
                result.setCodigo(oldEquipo.getCodigo());
            }
           
            if (!oldEquipo.getDescripcion().equals(newEquipo.getDescripcion())) {
                result.setDescripcion(newEquipo.getDescripcion());
            } else {
                result.setDescripcion(oldEquipo.getDescripcion());
            }
            
            if (oldEquipo.getAmbienteIdAmbiente()!= newEquipo.getAmbienteIdAmbiente()) {
                result.setAmbienteIdAmbiente(newEquipo.getAmbienteIdAmbiente());
            } else {
                result.setAmbienteIdAmbiente(oldEquipo.getAmbienteIdAmbiente());
            }
           

            return result;
        } else {
            return new Equipo();
        }

    }

//    public boolean deleteEquipo(Equipo e) {
//        
//      System.out.println("---->EJB Equipo " + e.getNombre());
//        Equipo oldEquipo = findEquipo(e.getIdEquipo());
//        System.out.println("---->EJB despues Equipo " + e.getNombre());
//
//        em.remove(oldEquipo);
//        System.out.println("---->EJB estado " + oldEquipo.getNombre());
//
//        return countEquipo(e.getIdEquipo()) == 0;
//
//    }
    
    public boolean deleteEquipo(Equipo e) {
        Equipo oldEquipo = findEquipo(e.getIdEquipo());

//        em.remove(oldEquipo);

        Query q = em.createQuery("DELETE FROM Equipo f where f.idEquipo = :id ");
        q.setParameter("id", oldEquipo.getIdEquipo());
        return q.executeUpdate()>0;

    }

    public int countEquipo(int idEquipo) {

        return ((Number) em.createQuery("SELECT COUNT(a.idEquipo) FROM Equipo a WHERE a.idEquipo = :idEquipo").setParameter("idEquipo", idEquipo).getSingleResult()).intValue();
//        return ((Number) em.createQuery("Select Count(e.idEquipo) from Equipo e where e.idEquipo = :idEquipo").setParameter("idEquipo", idEquipo).getSingleResult()).intValue();
    }

    public int countEquipo() {

        return ((Number) em.createQuery("Select count(a.idEquipo)from Equipo a").getSingleResult()).intValue();
    }

    public List<Ambiente> listarAmbientes() {
        return em.createQuery("Select a from Ambiente a", Ambiente.class).getResultList();
    }
}
