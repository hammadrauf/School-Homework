/*
    Document   : Roles.java
    Created on : 
    Author     : Hammad Rauf (rauf.hammad@gmail.com)

    Copyright (C) 2013 Hammad Rauf

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see &lt;http://www.gnu.org/licenses/&gt;.
 */
package dbbeans;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hammad
 */
@Entity
@Table(name = "roles", catalog = "schoolwebdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Roles.findAll", query = "SELECT r FROM Roles r"),
    @NamedQuery(name = "Roles.findByIdroles", query = "SELECT r FROM Roles r WHERE r.idroles = :idroles"),
    @NamedQuery(name = "Roles.findByDescription", query = "SELECT r FROM Roles r WHERE r.description = :description")})
public class Roles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idroles", nullable = false, length = 16)
    private String idroles;
    @Column(name = "description", length = 50)
    private String description;
    @ManyToMany(mappedBy = "rolesList")
    private List<Users> usersList;

    public Roles() {
    }

    public Roles(String idroles) {
        this.idroles = idroles;
    }

    public String getIdroles() {
        return idroles;
    }

    public void setIdroles(String idroles) {
        this.idroles = idroles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idroles != null ? idroles.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roles)) {
            return false;
        }
        Roles other = (Roles) object;
        if ((this.idroles == null && other.idroles != null) || (this.idroles != null && !this.idroles.equals(other.idroles))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dbbeans.Roles[ idroles=" + idroles + " ]";
    }
    
}
