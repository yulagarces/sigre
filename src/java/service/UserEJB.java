/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import model.Centro;
import model.Modulo;
import model.Persona;
import model.Usuario;
import model.dto.PersonaUsuarioDTO;
import model.dto.UsuarioPersonaDTO;
import org.primefaces.model.DualListModel;
import service.utils.exception.BusinessAppException;
import service.utils.security.utils.UtilsEncrypt;

/**
 *
 * @author danny
 */
@Stateless
public class UserEJB {

    @PersistenceContext
    private EntityManager em; // pone a disposicion JPA

    public int createUser(UsuarioPersonaDTO dto) {
        Usuario u = new Usuario();
        u.setNombreCompleto(dto.getPersonas().getNombre() + " " + dto.getPersonas().getApellido());
        u.setUsername(dto.getUsuarios().getUsername().toUpperCase());
        u.setPassword(dto.getUsuarios().getPassword());
        u.setRol(dto.getUsuarios().getRol().toUpperCase());
        u.setActivo(0);
        em.persist(u);
        int idpersona = buscarUsuario(dto.getUsuarios().getUsername());

        Persona a = new Persona();

        a.setPersonaIdusuario(idpersona);
        a.setDocumento(dto.getPersonas().getDocumento());
        a.setNombre(dto.getPersonas().getNombre());
        a.setApellido(dto.getPersonas().getApellido());
        a.setTelefono(dto.getPersonas().getTelefono());
        a.setEmail(dto.getPersonas().getEmail());
        a.setCentroIdCentro(dto.getCentros());

        try {
            em.persist(a);
            return 1;
        } catch (PersistenceException e) {

            return 0;
        }

    }

    public int buscarUsuario(String nombre) {

        return ((int) em.createQuery("SELECT a.idusuario FROM Usuario a WHERE a.username =:username").setParameter("username", nombre).getSingleResult());
    }
    public String getByCode(String nombre) {

        return ((String) em.createQuery("SELECT a.codActivacion FROM Usuario a WHERE a.username =:username").setParameter("username", nombre).getSingleResult());
    }

    public String searchEmail(int id) {

        return ((String) em.createQuery("SELECT p.email FROM Persona p WHERE p.personaIdusuario =:id").setParameter("id", id).getSingleResult());
    }

    public String getByRol(int id) {

        return ((String) em.createQuery("SELECT u.rol FROM Usuario u WHERE u.idusuario =:id").setParameter("id", id).getSingleResult());
    }

    public Persona getByIdUser(int id) {

        return ((Persona) em.createQuery("SELECT a FROM Persona a WHERE a.personaIdusuario =:id").setParameter("id", id).getSingleResult());
    }

    public List<Centro> listarCentros() {
        return em.createQuery("Select p from Centro p", Centro.class).getResultList();
    }

    public boolean verifyUsername(String username) {
        return ((Usuario) em.createNamedQuery("Usuario.findByUsername", Usuario.class).setParameter("username", username).getSingleResult()).getIdusuario() > 0;
    }

    public boolean countUsername(String username) {
        Number n = (Number) em.createQuery("Select count(u.idusuario) from Usuario u where u.username= :username").setParameter("username", username).getSingleResult();
        return (n).intValue() > 0;
    }

    public boolean updateCode(String codActivacion, int id) {

        Query q = em.createQuery("Update Usuario u SET u.codActivacion = :cod WHERE u.idusuario= :id")
                .setParameter("cod", codActivacion)
                .setParameter("id", id);
        return q.executeUpdate() > 0;
    }

    public Usuario getUserbyName(String username) {
        return (Usuario) em.createNamedQuery("Usuario.findByUsername", Usuario.class).setParameter("username", username).getSingleResult();
    }

    public boolean activateUserByCode(String codActivate, int id) {

        
        Query q = em.createQuery("Update Usuario u Set u.activo = 1 WHERE u.idusuario= :id");
        q.setParameter("id", id);

        return q.executeUpdate() > 0;
    }

    public boolean updatePass(String code, int id) {

        String pass = UtilsEncrypt.getInstance().encryptPassword(code);

        Query q = em.createQuery("Update Usuario u SET u.password = :pass WHERE u.idusuario= :id")
                .setParameter("pass", pass)
                .setParameter("id", id);
        return q.executeUpdate() > 0;
    }

    public Usuario getUserById(int id) {
        return (Usuario) em.createNamedQuery("Usuario.findByIdusuario", Usuario.class).setParameter("idusuario", id).getSingleResult();
    }

    /*Prueba 1*/
    public Persona getPerById(int id) {
        return (Persona) em.createNamedQuery("Persona.findByPersonaIdusuario", Persona.class).setParameter("personaIdusuario", id).getSingleResult();
    }

    /*Fin prueba*/
    public boolean updateImage(byte[] image, int id) {
        Query q = em.createQuery("Update Usuario u SET u.imagen = :image WHERE u.idusuario= :id")
                .setParameter("image", image)
                .setParameter("id", id);
        return q.executeUpdate() > 0;
    }

    public String getFullName(Integer idusuario) {
        return getUserById(idusuario).getNombreCompleto();
    }

    public List<Usuario> getAllActiveUsers() {
        List<Usuario> lisAll = em.createQuery("SELECT u From Usuario u where u.activo = :activo").setParameter("activo", 1).getResultList();
        List<Usuario> listResult = new ArrayList<>();
        for (Usuario u : lisAll) {
            if (!u.getUsername().equals("ADMIN")) {
                listResult.add(u);
            }
        }
        return listResult;
    }

    public List<PersonaUsuarioDTO> getAllPersonUsers() {
        List<Persona> lisAll = em.createQuery("SELECT p from Persona p JOIN p.usuario u where u.activo = :activo").setParameter("activo", 1).getResultList();
        List<PersonaUsuarioDTO> listResult = new ArrayList<>();
        for (Persona p : lisAll) {
            if (!p.getUsuario().getUsername().equals("ADMIN")) {
                PersonaUsuarioDTO dto = new PersonaUsuarioDTO();
                dto.setPersona(p);
                dto.setUsuario(p.getUsuario());
                listResult.add(dto);
            }
        }
        return listResult;
    }

    public boolean assignModules(Usuario userModule, DualListModel<Modulo> modules) {

        List<Modulo> target = modules.getTarget();
        target.removeAll(new HashSet(userModule.getModuloList()));
        String delete = "delete from modulo_usuario where (id_modulo = ? ) and (id_usuario = ?)";
        Query q1 = em.createNativeQuery(delete);

        for (Modulo modulo : modules.getSource()) {
            q1.setParameter(1, modulo.getIdmodulo());
            q1.setParameter(2, userModule.getIdusuario());
            q1.executeUpdate();
        }

        String insert = "insert into modulo_usuario (id_modulo, id_usuario) values (?,?)";

        Query q2 = em.createNativeQuery(insert);

        int countInserts = 0;

        for (Modulo modulo : target) {
            q2.setParameter(1, modulo.getIdmodulo());
            q2.setParameter(2, userModule.getIdusuario());
            countInserts += q2.executeUpdate();
        }

        return countInserts > 0;

    }

    public int updatePersona(PersonaUsuarioDTO dto) {

        Usuario newUser = dto.getUsuario();
        newUser.setNombreCompleto(dto.getPersona().getNombre() + " " + dto.getPersona().getApellido());
        Usuario oldUser = findUsuario(dto.getUsuario().getIdusuario());

        oldUser = compareObjectUser(newUser, oldUser);

        Persona newPer = dto.getPersona();
        Persona oldPer = findPersona(dto.getPersona().getPersonaIdusuario());

        oldPer = compareObjectPer(newPer, oldPer);

        if (em.merge(oldUser).getIdusuario() > 0 & em.merge(oldPer).getPersonaIdusuario() > 0) {
            // algun mensaje de exito o que retorn true o 1
            return 1;

        } else {
            // no actualizo, retorne error o 0 o false
            return 0;
        }

    } // cierra metodo anterior

    public Usuario compareObjectUser(Usuario newUsuario, Usuario oldUsuario) {
        if (!newUsuario.getUsername().equals(oldUsuario.getUsername())) {
            oldUsuario.setUsername(newUsuario.getUsername());
        }
        if (!newUsuario.getRol().equals(oldUsuario.getRol())) {
            oldUsuario.setRol(newUsuario.getRol());
        }
        if (!newUsuario.getNombreCompleto().equals(oldUsuario.getNombreCompleto())) {
            oldUsuario.setNombreCompleto(newUsuario.getNombreCompleto());
        }
        //	asi par alos demas campos

        return oldUsuario;
    }

    public Usuario findUsuario(int id) {
        return (Usuario) em.createNamedQuery("Usuario.findByIdusuario").setParameter("idusuario", id).getSingleResult();
    }

    public Persona findPersona(int id) {
        return (Persona) em.createNamedQuery("Persona.findByPersonaIdusuario").setParameter("personaIdusuario", id).getSingleResult();
    }

    private Persona compareObjectPer(Persona newPersona, Persona oldPersona) {
        if (!newPersona.getDocumento().equals(oldPersona.getDocumento())) {
            oldPersona.setDocumento(newPersona.getDocumento());
        }
        if (!newPersona.getNombre().equals(oldPersona.getNombre())) {
            oldPersona.setNombre(newPersona.getNombre());
        }
        if (!newPersona.getApellido().equals(oldPersona.getApellido())) {
            oldPersona.setApellido(newPersona.getApellido());
        }
        if (!newPersona.getTelefono().equals(oldPersona.getTelefono())) {
            oldPersona.setTelefono(newPersona.getTelefono());
        }
        if (!newPersona.getEmail().equals(oldPersona.getEmail())) {
            oldPersona.setEmail(newPersona.getEmail());
        }
        //	asi par alos demas campos

        return oldPersona;

    }

    public int deletePersona(PersonaUsuarioDTO p) {
        int id = p.getUsuario().getIdusuario();
        Persona oldPersona = findPersona(p.getPersona().getPersonaIdusuario());
        Usuario oldUsuario = findUsuario(p.getPersona().getPersonaIdusuario());

        em.remove(oldPersona);
        em.flush();
        em.remove(oldUsuario);

        if (countUsuario(id) == 0) {
            return 1;

        } else {
            return 0;
        }

    }

    public int countUsuario(int id) {

        return ((Number) em.createQuery("SELECT COUNT(a.idusuario) FROM Usuario a WHERE a.idusuario =:id").setParameter("id", id).getSingleResult()).intValue();
    }

    public List<Persona> findAllPersona() {
        return em.createNamedQuery("Persona.findAll").getResultList();
    }

    public int countUsers() {
        return ((Number) em.createQuery("Select count(u.idusuario) from Usuario u").getSingleResult()).intValue();
    }

}
