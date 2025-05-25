/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easycooper;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author Lucas Teran
 */
@Entity
@Table(name = "reservations")
@NamedQueries({
    @NamedQuery(name = "Reservations.findAll", query = "SELECT r FROM Reservations r"),
    @NamedQuery(name = "Reservations.findById", query = "SELECT r FROM Reservations r WHERE r.id = :id"),
    @NamedQuery(name = "Reservations.findByDateCreation", query = "SELECT r FROM Reservations r WHERE r.dateCreation = :dateCreation"),
    @NamedQuery(name = "Reservations.findByDateReservation", query = "SELECT r FROM Reservations r WHERE r.dateReservation = :dateReservation"),
    @NamedQuery(name = "Reservations.findByInitHour", query = "SELECT r FROM Reservations r WHERE r.initHour = :initHour"),
    @NamedQuery(name = "Reservations.findByFinalHour", query = "SELECT r FROM Reservations r WHERE r.finalHour = :finalHour"),
    @NamedQuery(name = "Reservations.findByState", query = "SELECT r FROM Reservations r WHERE r.state = :state")})
public class Reservations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date_creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    @Column(name = "date_reservation")
    @Temporal(TemporalType.DATE)
    private Date dateReservation;
    @Column(name = "initHour")
    @Temporal(TemporalType.TIME)
    private Date initHour;
    @Column(name = "finalHour")
    @Temporal(TemporalType.TIME)
    private Date finalHour;
    @Basic(optional = false)
    @Column(name = "state")
    private String state;
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne
    private Userec idUser;
    @JoinColumn(name = "id_vehicle", referencedColumnName = "id")
    @ManyToOne
    private Vehicle idVehicle;

    public Reservations() {
    }

    public Reservations(Integer id) {
        this.id = id;
    }

    public Reservations(Integer id, String state) {
        this.id = id;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public Date getInitHour() {
        return initHour;
    }

    public void setInitHour(Date initHour) {
        this.initHour = initHour;
    }

    public Date getFinalHour() {
        return finalHour;
    }

    public void setFinalHour(Date finalHour) {
        this.finalHour = finalHour;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Userec getIdUser() {
        return idUser;
    }

    public void setIdUser(Userec idUser) {
        this.idUser = idUser;
    }

    public Vehicle getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(Vehicle idVehicle) {
        this.idVehicle = idVehicle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservations)) {
            return false;
        }
        Reservations other = (Reservations) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "easycooper.Reservations[ id=" + id + " ]";
    }
    
}
