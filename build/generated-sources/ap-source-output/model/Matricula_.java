package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Matricula.class)
public abstract class Matricula_ {

	public static volatile SingularAttribute<Matricula, Date> fecha;
	public static volatile SingularAttribute<Matricula, Persona> persona;
	public static volatile SingularAttribute<Matricula, MatriculaPK> matriculaPK;
	public static volatile SingularAttribute<Matricula, Ficha> ficha;

}

