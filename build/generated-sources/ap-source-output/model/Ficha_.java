package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Ficha.class)
public abstract class Ficha_ {

	public static volatile SingularAttribute<Ficha, Integer> idFicha;
	public static volatile SingularAttribute<Ficha, Date> horaFin;
	public static volatile SingularAttribute<Ficha, Date> fechaInicio;
	public static volatile ListAttribute<Ficha, PeticionEquipo> peticionEquipoList;
	public static volatile ListAttribute<Ficha, Matricula> matriculaList;
	public static volatile SingularAttribute<Ficha, Programa> programaIdPrograma;
	public static volatile SingularAttribute<Ficha, Date> fechaFin;
	public static volatile SingularAttribute<Ficha, Date> horaInicio;
	public static volatile SingularAttribute<Ficha, String> nombreFicha;
	public static volatile ListAttribute<Ficha, Horario> horarioList;

}

