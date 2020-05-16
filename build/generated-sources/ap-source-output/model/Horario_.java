package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Horario.class)
public abstract class Horario_ {

	public static volatile SingularAttribute<Horario, Date> horaFin;
	public static volatile SingularAttribute<Horario, Persona> persona;
	public static volatile SingularAttribute<Horario, Date> fechaInicio;
	public static volatile SingularAttribute<Horario, Ambiente> ambiente;
	public static volatile SingularAttribute<Horario, Ficha> ficha;
	public static volatile SingularAttribute<Horario, Date> fechaFin;
	public static volatile SingularAttribute<Horario, String> dia;
	public static volatile SingularAttribute<Horario, HorarioPK> horarioPK;
	public static volatile SingularAttribute<Horario, Date> horaInicio;

}

