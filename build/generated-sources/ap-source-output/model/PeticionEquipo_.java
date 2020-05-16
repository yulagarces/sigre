package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PeticionEquipo.class)
public abstract class PeticionEquipo_ {

	public static volatile SingularAttribute<PeticionEquipo, Date> horaFin;
	public static volatile SingularAttribute<PeticionEquipo, Ficha> fichaIdficha;
	public static volatile SingularAttribute<PeticionEquipo, String> estado;
	public static volatile SingularAttribute<PeticionEquipo, Integer> disponibilidad;
	public static volatile SingularAttribute<PeticionEquipo, Integer> plazoVencido;
	public static volatile SingularAttribute<PeticionEquipo, Integer> idPeticionEquipo;
	public static volatile SingularAttribute<PeticionEquipo, Date> fechaInicio;
	public static volatile SingularAttribute<PeticionEquipo, Date> fechaFinal;
	public static volatile SingularAttribute<PeticionEquipo, Persona> personaIdusuario;
	public static volatile SingularAttribute<PeticionEquipo, Equipo> equipoIdEquipo;
	public static volatile SingularAttribute<PeticionEquipo, Date> horaInicio;

}

