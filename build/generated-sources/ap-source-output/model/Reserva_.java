package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Reserva.class)
public abstract class Reserva_ {

	public static volatile SingularAttribute<Reserva, Date> horaFin;
	public static volatile SingularAttribute<Reserva, Integer> estado;
	public static volatile SingularAttribute<Reserva, Date> fechaInicio;
	public static volatile SingularAttribute<Reserva, Ficha> fichaIdFicha;
	public static volatile SingularAttribute<Reserva, Persona> personaIdusuario;
	public static volatile SingularAttribute<Reserva, Date> fechaFin;
	public static volatile SingularAttribute<Reserva, Ambiente> ambienteIdAmbiente;
	public static volatile SingularAttribute<Reserva, Integer> idReserva;
	public static volatile SingularAttribute<Reserva, Date> horaInicio;

}

