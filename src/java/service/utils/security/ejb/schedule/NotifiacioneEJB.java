/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.utils.security.ejb.schedule;

import java.util.Date;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.PeticionEquipo;

/**
 *
 * @author santi
 */
@Singleton
public class NotifiacioneEJB {
    
    @PersistenceContext
    private EntityManager em;
    
    @Schedule(second = "*", minute = "*", hour = "*/24", persistent = true)
    public void updatePlazoVencido() throws InterruptedException {
//        System.out.println("----------->");
        List<PeticionEquipo> reservasEquipos = em.createNamedQuery("PeticionEquipo.findAll", PeticionEquipo.class).getResultList();
        
        for (PeticionEquipo reservasEquipo : reservasEquipos) {
            if(reservasEquipo.getFechaFinal().before(new Date())){
                
                Query q = em.createQuery("UPDATE PeticionEquipo pe set pe.plazoVencido = 0 where pe.idPeticionEquipo = :id");
                
                q.setParameter("id", reservasEquipo.getIdPeticionEquipo());
//                System.out.println("*");
                q.executeUpdate();
            }
        }
        
    }
}