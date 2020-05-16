package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Equipo.class)
public abstract class Equipo_ {

	public static volatile SingularAttribute<Equipo, String> descripcion;
	public static volatile SingularAttribute<Equipo, Integer> idEquipo;
	public static volatile SingularAttribute<Equipo, String> codigo;
	public static volatile SingularAttribute<Equipo, String> nombre;
	public static volatile SingularAttribute<Equipo, Ambiente> ambienteIdAmbiente;

}

