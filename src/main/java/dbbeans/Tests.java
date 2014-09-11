/*
    Document   : Tests.java
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
@Table(name = "tests", catalog = "schoolwebdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tests.findAll", query = "SELECT t FROM Tests t"),
    @NamedQuery(name = "Tests.findByUsername", query = "SELECT t FROM Tests t WHERE t.testsPK.username = :username"),
    @NamedQuery(name = "Tests.findByTitle", query = "SELECT t FROM Tests t WHERE t.testsPK.title = :title"),
    @NamedQuery(name = "Tests.findByLessoncode", query = "SELECT t FROM Tests t WHERE t.lessoncode = :lessoncode"),
    @NamedQuery(name = "Tests.findByDatetime", query = "SELECT t FROM Tests t WHERE t.datetime = :datetime"),
    @NamedQuery(name = "Tests.findByMaxscore", query = "SELECT t FROM Tests t WHERE t.maxscore = :maxscore"),
    @NamedQuery(name = "Tests.findByScore", query = "SELECT t FROM Tests t WHERE t.score = :score"),
    @NamedQuery(name = "Tests.findByDuration", query = "SELECT t FROM Tests t WHERE t.duration = :duration")})
public class Tests implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TestsPK testsPK;
    @Basic(optional = false)
    @Column(name = "lessoncode", nullable = false, length = 50)
    private String lessoncode;
    @Basic(optional = false)
    @Column(name = "datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;
    @Basic(optional = false)
    @Column(name = "maxscore", nullable = false)
    private int maxscore;
    @Basic(optional = false)
    @Column(name = "score", nullable = false)
    private int score;
    @Basic(optional = false)
    @Column(name = "duration", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date duration;
    @JoinColumn(name = "username", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public Tests() {
    }

    public Tests(TestsPK testsPK) {
        this.testsPK = testsPK;
    }

    public Tests(TestsPK testsPK, String lessoncode, Date datetime, int maxscore, int score, Date duration) {
        this.testsPK = testsPK;
        this.lessoncode = lessoncode;
        this.datetime = datetime;
        this.maxscore = maxscore;
        this.score = score;
        this.duration = duration;
    }

    public Tests(String username, String title) {
        this.testsPK = new TestsPK(username, title);
    }

    public TestsPK getTestsPK() {
        return testsPK;
    }

    public void setTestsPK(TestsPK testsPK) {
        this.testsPK = testsPK;
    }

    public String getLessoncode() {
        return lessoncode;
    }

    public void setLessoncode(String lessoncode) {
        this.lessoncode = lessoncode;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getMaxscore() {
        return maxscore;
    }

    public void setMaxscore(int maxscore) {
        this.maxscore = maxscore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getDuration() {
        return duration;
    }

    public void setDuration(Date duration) {
        this.duration = duration;
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
        hash += (testsPK != null ? testsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tests)) {
            return false;
        }
        Tests other = (Tests) object;
        if ((this.testsPK == null && other.testsPK != null) || (this.testsPK != null && !this.testsPK.equals(other.testsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dbbeans.Tests[ testsPK=" + testsPK + " ]";
    }
    
}
