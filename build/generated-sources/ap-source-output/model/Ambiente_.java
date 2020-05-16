package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Ambiente.class)
public abstract class Ambiente_ {

	public static volatile SingularAttribute<Ambiente, String> descripcion;
	public static volatile SingularAttribute<Ambiente, String> tipo;
	public static volatile ListAttribute<Ambiente, Reserva> reservaList;
	public static volatile SingularAttribute<Ambiente, Integer> idAmbiente;
	public static volatile ListAttribute<Ambiente, Equipo> equipoList;
	public static volatile SingularAttribute<Ambiente, String> nombre;
	public static volatile SingularAttribute<Ambiente, Integer> capacidad;
	public static volatile SingularAttribute<Ambiente, String> equipos;
	public static volatile ListAttribute<Ambiente, Horario> horarioList;

}

