package easycooper;

import easycooper.Userec;
import easycooper.Vehicle;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-05-27T20:41:24", comments="EclipseLink-4.0.2.v20230616-r3bfa6ac6ddf76d7909adc5ea7ecaa47c02c007ed")
@StaticMetamodel(Reservations.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Reservations_ { 

    public static volatile SingularAttribute<Reservations, Userec> idUser;
    public static volatile SingularAttribute<Reservations, Date> dateCreation;
    public static volatile SingularAttribute<Reservations, Vehicle> idVehicle;
    public static volatile SingularAttribute<Reservations, Integer> id;
    public static volatile SingularAttribute<Reservations, String> state;
    public static volatile SingularAttribute<Reservations, Date> dateReservation;
    public static volatile SingularAttribute<Reservations, Date> finalHour;
    public static volatile SingularAttribute<Reservations, Date> initHour;

}