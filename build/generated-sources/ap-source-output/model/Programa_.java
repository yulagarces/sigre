package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Programa.class)
public abstract class Programa_ {

	public static volatile SingularAttribute<Programa, Integer> idPrograma;
	public static volatile SingularAttribute<Programa, String> tipoPrograma;
	public static volatile ListAttribute<Programa, Ficha> fichaList;
	public static volatile SingularAttribute<Programa, String> nombre;

}

