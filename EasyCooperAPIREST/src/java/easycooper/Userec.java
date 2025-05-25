/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package easycooper;

import jakarta.json.bind.annotation.JsonbTransient;
import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author Lucas Teran
 */
@Entity
@Table(name = "userec")
@NamedQueries({
    @NamedQuery(name = "Userec.findAll", query = "SELECT u FROM Userec u"),
    @NamedQuery(name = "Userec.findById", query = "SELECT u FROM Userec u WHERE u.id = :id"),
    @NamedQuery(name = "Userec.findByNameUser", query = "SELECT u FROM Userec u WHERE u.nameUser = :nameUser"),
    @NamedQuery(name = "Userec.findByEmail", query = "SELECT u FROM Userec u WHERE u.email = :email"),
    @NamedQuery(name = "Userec.findByDni", query = "SELECT u FROM Userec u WHERE u.dni = :dni"),
    @NamedQuery(name = "Userec.findByPasswordUser", query = "SELECT u FROM Userec u WHERE u.passwordUser = :passwordUser"),
    @NamedQuery(name = "Userec.findByPhone", query = "SELECT u FROM Userec u WHERE u.phone = :phone"),
    @NamedQuery(name = "Userec.findByTypeUser", query = "SELECT u FROM Userec u WHERE u.typeUser = :typeUser")})
public class Userec implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name_user")
    private String nameUser;
    @Column(name = "email")
    private String email;
    @Column(name = "dni")
    private String dni;
    @Column(name = "password_user")
    private String passwordUser;
    @Column(name = "phone")
    private String phone;
    @Column(name = "type_user")
    private String typeUser;
    @OneToMany(mappedBy = "idUser")
    @JsonbTransient
    private Collection<Reservations> reservationsCollection;

    public Userec() {
    }

    public Userec(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public Collection<Reservations> getReservationsCollection() {
        return reservationsCollection;
    }

    public void setReservationsCollection(Collection<Reservations> reservationsCollection) {
        this.reservationsCollection = reservationsCollection;
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
        if (!(object instanceof Userec)) {
            return false;
        }
        Userec other = (Userec) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "easycooper.Userec[ id=" + id + " ]";
    }
    
}
