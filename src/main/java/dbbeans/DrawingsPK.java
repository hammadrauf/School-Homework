/*
    Document   : DrawingsPK.java
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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Hammad
 */
@Embeddable
public class DrawingsPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "username", nullable = false, length = 16)
    private String username;
    @Basic(optional = false)
    @Column(name = "caption", nullable = false, length = 50)
    private String caption;

    public DrawingsPK() {
    }

    public DrawingsPK(String username, String caption) {
        this.username = username;
        this.caption = caption;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        hash += (caption != null ? caption.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DrawingsPK)) {
            return false;
        }
        DrawingsPK other = (DrawingsPK) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        if ((this.caption == null && other.caption != null) || (this.caption != null && !this.caption.equals(other.caption))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dbbeans.DrawingsPK[ username=" + username + ", caption=" + caption + " ]";
    }
    
}
