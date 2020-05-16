package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Centro.class)
public abstract class Centro_ {

	public static volatile SingularAttribute<Centro, Integer> idCentro;
	public static volatile SingularAttribute<Centro, String> ubicacion;
	public static volatile ListAttribute<Centro, Persona> personaList;
	public static volatile SingularAttribute<Centro, String> nombre;

}

