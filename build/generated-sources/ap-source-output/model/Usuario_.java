package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Usuario.class)
public abstract class Usuario_ {

	public static volatile SingularAttribute<Usuario, String> password;
	public static volatile SingularAttribute<Usuario, String> codActivacion;
	public static volatile SingularAttribute<Usuario, Persona> persona;
	public static volatile SingularAttribute<Usuario, byte[]> imagen;
	public static volatile SingularAttribute<Usuario, Date> ultimoAcceso;
	public static volatile SingularAttribute<Usuario, String> nombreCompleto;
	public static volatile SingularAttribute<Usuario, String> rol;
	public static volatile SingularAttribute<Usuario, Integer> idusuario;
	public static volatile SingularAttribute<Usuario, String> username;
	public static volatile SingularAttribute<Usuario, Integer> activo;
	public static volatile ListAttribute<Usuario, Modulo> moduloList;

}

