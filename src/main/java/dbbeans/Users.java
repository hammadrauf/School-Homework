/*
    Document   : Users.java
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
@Table(name = "users", catalog = "schoolwebdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email"),
    @NamedQuery(name = "Users.findByMailinglist", query = "SELECT u FROM Users u WHERE u.mailinglist = :mailinglist")})
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "username", nullable = false, length = 16)
    private String username;
    @Basic(optional = false)
    @Column(name = "password", nullable = false, length = 50)
    private String password;
    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "mailinglist")
    private Character mailinglist;
    @JoinTable(name = "users_roles", joinColumns = {
        @JoinColumn(name = "username", referencedColumnName = "username", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "idroles", referencedColumnName = "idroles", nullable = false)})
    @ManyToMany
    private List<Roles> rolesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Drawings> drawingsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Tests> testsList;

    public Users() {
    }

    public Users(String username) {
        this.username = username;
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Character getMailinglist() {
        return mailinglist;
    }

    public void setMailinglist(Character mailinglist) {
        this.mailinglist = mailinglist;
    }

    @XmlTransient
    public List<Roles> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Roles> rolesList) {
        this.rolesList = rolesList;
    }

    @XmlTransient
    public List<Drawings> getDrawingsList() {
        return drawingsList;
    }

    public void setDrawingsList(List<Drawings> drawingsList) {
        this.drawingsList = drawingsList;
    }

    @XmlTransient
    public List<Tests> getTestsList() {
        return testsList;
    }

    public void setTestsList(List<Tests> testsList) {
        this.testsList = testsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dbbeans.Users[ username=" + username + " ]";
    }
    
}
