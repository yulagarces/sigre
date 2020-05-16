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
import model.Ficha;
import model.Programa;
import model.Reserva;
import model.dto.FichaDTO;

@Stateless
public class FichaEJB {

    @PersistenceContext
    private EntityManager em; // pone a disposicion JPA

    public List<Ficha> findAll() {
        return em.createNamedQuery("Ficha.findAll").getResultList();
    }

    public Ficha updateFicha(Ficha newFicha) {

        Ficha oldFicha = findFicha(newFicha.getIdFicha());

        Ficha result = compareFicha(oldFicha, newFicha);

        if (result.getIdFicha() != null) {
            return em.merge(result);
        } else {
            return result;
        }
    }

    public Ficha findFicha(int idficha) {
        return (Ficha) em.createNamedQuery("Ficha.findByIdFicha").setParameter("idFicha", idficha).getSingleResult();
    }

    private Ficha compareFicha(Ficha oldFicha, Ficha newFicha) {

        if (oldFicha.getIdFicha() == newFicha.getIdFicha()) {
            Ficha result = new Ficha();
            result.setIdFicha(newFicha.getIdFicha());
            // comprar los demas datos
            if (!oldFicha.getFechaInicio().equals(newFicha.getFechaInicio())) {
                result.setFechaInicio(newFicha.getFechaInicio());
            } else {
                result.setFechaInicio(oldFicha.getFechaInicio());
            }

            if (oldFicha.getFechaFin() != newFicha.getFechaFin()) {
                result.setFechaFin(newFicha.getFechaFin());
            } else {
                result.setFechaFin(oldFicha.getFechaFin());
            }

            if (oldFicha.getHoraInicio() != newFicha.getHoraInicio()) {
                result.setHoraInicio(newFicha.getHoraInicio());
            } else {
                result.setHoraInicio(oldFicha.getHoraInicio());
            }

            if (oldFicha.getHoraFin() != newFicha.getHoraFin()) {
                result.setHoraFin(newFicha.getHoraFin());
            } else {
                result.setHoraFin(oldFicha.getHoraFin());
            }

            if (oldFicha.getNombreFicha() != newFicha.getNombreFicha()) {
                result.setNombreFicha(newFicha.getNombreFicha());
            } else {
                result.setNombreFicha(oldFicha.getNombreFicha());
            }

            if (oldFicha.getHorarioList() != newFicha.getHorarioList()) {
                result.setHorarioList(newFicha.getHorarioList());
            } else {
                result.setHorarioList(oldFicha.getHorarioList());
            }

            if (oldFicha.getProgramaIdPrograma() != newFicha.getProgramaIdPrograma()) {
                result.setProgramaIdPrograma(newFicha.getProgramaIdPrograma());
            } else {
                result.setProgramaIdPrograma(oldFicha.getProgramaIdPrograma());
            }

            if (oldFicha.getMatriculaList() != newFicha.getMatriculaList()) {
                result.setMatriculaList(newFicha.getMatriculaList());
            } else {
                result.setMatriculaList(oldFicha.getMatriculaList());
            }

            return result;
        } else {
            return new Ficha();
        }

    }

    public boolean deleteFicha(Ficha f) {

        System.out.println("---->EJB ficha " + f.getNombreFicha());
        Ficha oldFicha = findFicha(f.getIdFicha());
        System.out.println("---->EJB despues ficha " + f.getNombreFicha());

//        em.remove(oldFicha);

        Query q = em.createQuery("DELETE FROM Ficha f where f.idFicha = :id ");
        q.setParameter("id", oldFicha.getIdFicha());
        
        
//        Cystem.out.println("---->EJB estado " + oldFicha.getNombreFicha());

        return q.executeUpdate()>0;
    }

    public int countFicha(int idFicha) {

        return ((Number) em.createQuery("Select Count(d.idFicha) from Ficha d where d.idFicha = :idFicha").setParameter("idFicha", idFicha).getSingleResult()).intValue();
    }


    public List<Programa> listarProgramas() {
        return em.createQuery("Select p from Programa p", Programa.class).getResultList();
    }

    public int crearFicha(FichaDTO dto) {

        Ficha a = new Ficha();

        a.setFechaInicio(dto.getFicha().getFechaInicio());
        a.setFechaFin(dto.getFicha().getFechaFin());
        a.setHoraInicio(dto.getFicha().getHoraInicio());
        a.setHoraFin(dto.getFicha().getHoraFin());
        a.setNombreFicha(dto.getFicha().getNombreFicha());
        a.setProgramaIdPrograma(dto.getPrograma());

        try {

            em.persist(a);
            return 1;
        } catch (PersistenceException f) {

            return 0;
        }

    }

    public Ficha GetByNameFicha(String nombre) {

        return (Ficha) em.createNamedQuery("Ficha.findByNombreFicha").setParameter("nombreFicha", nombre).getSingleResult();

    }

    public void createReserva(FichaDTO dto) {

        Reserva re = new Reserva();
        re.setEstado(1);
        re.setFechaInicio(dto.getFicha().getFechaInicio());
        re.setHoraInicio(dto.getFicha().getHoraInicio());
        re.setFechaFin(dto.getFicha().getFechaFin());
        re.setHoraFin(dto.getFicha().getHoraFin());
        re.setAmbienteIdAmbiente(dto.getAmbiente());
        re.setFichaIdFicha(GetByNameFicha(dto.getFicha().getNombreFicha()));

        em.persist(re);

    }

    public int countFichas() {
        return ((Number) em.createQuery("Select count(d.idFicha)from Ficha d").getSingleResult()).intValue();
    }
}
