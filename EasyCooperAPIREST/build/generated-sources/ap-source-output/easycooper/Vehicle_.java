package easycooper;

import easycooper.Reservations;
import jakarta.persistence.metamodel.CollectionAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-05-27T20:41:24", comments="EclipseLink-4.0.2.v20230616-r3bfa6ac6ddf76d7909adc5ea7ecaa47c02c007ed")
@StaticMetamodel(Vehicle.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Vehicle_ { 

    public static volatile CollectionAttribute<Vehicle, Reservations> reservationsCollection;
    public static volatile SingularAttribute<Vehicle, String> typeVehicle;
    public static volatile SingularAttribute<Vehicle, String> model;
    public static volatile SingularAttribute<Vehicle, String> details;
    public static volatile SingularAttribute<Vehicle, Integer> id;
    public static volatile SingularAttribute<Vehicle, String> availability;
    public static volatile SingularAttribute<Vehicle, String> maxSpeed;

}