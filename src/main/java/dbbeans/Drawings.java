/*
    Document   : Drawings.java
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
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hammad
 */
@Entity
@Table(name = "drawings", catalog = "schoolwebdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Drawings.findAll", query = "SELECT d FROM Drawings d"),
    @NamedQuery(name = "Drawings.findByUsername", query = "SELECT d FROM Drawings d WHERE d.drawingsPK.username = :username"),
    @NamedQuery(name = "Drawings.findByCaption", query = "SELECT d FROM Drawings d WHERE d.drawingsPK.caption = :caption"),
    @NamedQuery(name = "Drawings.findByUsernameCaption", query = "SELECT d FROM Drawings d WHERE d.drawingsPK.username = :username and d.drawingsPK.caption = :caption"),
    @NamedQuery(name = "Drawings.findByVXML", query = "SELECT d FROM Drawings d WHERE d.vXML = :vXML"),
    @NamedQuery(name = "Drawings.findByDatetime", query = "SELECT d FROM Drawings d WHERE d.datetime = :datetime")})
public class Drawings implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DrawingsPK drawingsPK;
    @Basic(optional = false)
    @Column(name = "vXML", nullable = false, length = 11000)
    private String vXML;
    @Basic(optional = false)
    @Column(name = "datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public Drawings() {
    }

    public Drawings(DrawingsPK drawingsPK) {
        this.drawingsPK = drawingsPK;
    }

    public Drawings(DrawingsPK drawingsPK, String vXML, Date datetime) {
        this.drawingsPK = drawingsPK;
        this.vXML = vXML;
        this.datetime = datetime;
    }

    public Drawings(String username, String caption) {
        this.drawingsPK = new DrawingsPK(username, caption);
    }

    public DrawingsPK getDrawingsPK() {
        return drawingsPK;
    }

    public void setDrawingsPK(DrawingsPK drawingsPK) {
        this.drawingsPK = drawingsPK;
    }

    public String getVXML() {
        return vXML;
    }

    public void setVXML(String vXML) {
        this.vXML = vXML;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (drawingsPK != null ? drawingsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Drawings)) {
            return false;
        }
        Drawings other = (Drawings) object;
        if ((this.drawingsPK == null && other.drawingsPK != null) || (this.drawingsPK != null && !this.drawingsPK.equals(other.drawingsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dbbeans.Drawings[ drawingsPK=" + drawingsPK + " ]";
    }
    
}
