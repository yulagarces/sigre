package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Persona.class)
public abstract class Persona_ {

	public static volatile ListAttribute<Persona, Reserva> reservaList;
	public static volatile SingularAttribute<Persona, String> apellido;
	public static volatile SingularAttribute<Persona, Integer> personaIdusuario;
	public static volatile ListAttribute<Persona, Matricula> matriculaList;
	public static volatile ListAttribute<Persona, PeticionEquipo> peticionEquipoList;
	public static volatile SingularAttribute<Persona, String> documento;
	public static volatile SingularAttribute<Persona, Usuario> usuario;
	public static volatile SingularAttribute<Persona, String> telefono;
	public static volatile SingularAttribute<Persona, Centro> centroIdCentro;
	public static volatile SingularAttribute<Persona, String> nombre;
	public static volatile SingularAttribute<Persona, String> email;
	public static volatile ListAttribute<Persona, Horario> horarioList;

}

