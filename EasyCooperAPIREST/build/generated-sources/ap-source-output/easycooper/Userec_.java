package easycooper;

import easycooper.Reservations;
import jakarta.persistence.metamodel.CollectionAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-05-27T20:41:23", comments="EclipseLink-4.0.2.v20230616-r3bfa6ac6ddf76d7909adc5ea7ecaa47c02c007ed")
@StaticMetamodel(Userec.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Userec_ { 

    public static volatile SingularAttribute<Userec, String> nameUser;
    public static volatile CollectionAttribute<Userec, Reservations> reservationsCollection;
    public static volatile SingularAttribute<Userec, String> phone;
    public static volatile SingularAttribute<Userec, String> typeUser;
    public static volatile SingularAttribute<Userec, Integer> id;
    public static volatile SingularAttribute<Userec, String> passwordUser;
    public static volatile SingularAttribute<Userec, String> email;
    public static volatile SingularAttribute<Userec, String> dni;

}