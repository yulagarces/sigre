package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Modulo.class)
public abstract class Modulo_ {

	public static volatile SingularAttribute<Modulo, String> descripcion;
	public static volatile SingularAttribute<Modulo, String> icono;
	public static volatile ListAttribute<Modulo, Usuario> usuarioList;
	public static volatile SingularAttribute<Modulo, Integer> idmodulo;
	public static volatile SingularAttribute<Modulo, String> nombre;
	public static volatile SingularAttribute<Modulo, String> url;
	public static volatile SingularAttribute<Modulo, Integer> activo;

}

