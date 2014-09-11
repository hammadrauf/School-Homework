/*
    Document   : DBInitializer.java
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

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

/**
 * This class contains various utility methods used with the data model of the
 * application. - JPA EntityManager and Persist method. - ResetAllIDGenerators
 * method - Individual Generator methods (getNextXXXXXX) for each Persistant
 * Object (Entity) type.
 *
 * @author Hammad Rauf
 */
public class DBInitializer {
    /*
     * public static Preferences preferences = null;
     *
     * static { preferences =
     * Preferences.systemNodeForPackage(DBInitializer.class); }
     */

    private java.util.HashMap propMap = null;
    private static javax.persistence.EntityManager eManager;

    public DBInitializer() throws Exception {
        try {
//use this if properties given in persistence.xml
            eManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("StrutsSchoolWebPU").createEntityManager();
//use this section if properties given in properties file
/*
             * propMap = new HashMap();
             *
             * Preferences preferences =
             * com.hrauf.software.tailoring.model.DBInitializer.preferences;
             *
             * String url = preferences.get("db.url",null);
             * propMap.put("javax.persistence.jdbc.url", url);
             *
             * String pwd = preferences.get("db.pwd",null);
             * propMap.put("javax.persistence.jdbc.password", pwd);
             *
             * String eclipseDB =
             * preferences.get("eclipselink.target-database",null);
             * propMap.put("eclipselink.target-database", eclipseDB);
             *
             * String driver = preferences.get("db.driver",null);
             * propMap.put("javax.persistence.jdbc.driver", driver);
             *
             * String usr = preferences.get("db.usr",null);
             * propMap.put("javax.persistence.jdbc.user", usr);
             *
             *
             * eManager = java.beans.Beans.isDesignTime() ? null :
             * javax.persistence.Persistence.createEntityManagerFactory("StrutsSchoolWebPU",
             * propMap).createEntityManager();
             */
        } catch (Exception e) {
            Logger.getLogger(DBInitializer.class.getName()).log(Level.SEVERE, "Failed: Enitity Manager initialization", e);
            throw new Exception(e);
        }
    }

    public static void flushClear() {
        eManager.flush();
        eManager.clear();
    }

    public static Object refreshThis(Object o) {
        eManager.refresh(o);
        return (o);
    }

    public static void persist(Object object) throws EntityExistsException, PersistenceException, Exception {
        eManager.getTransaction().begin();
        eManager.persist(object);
        eManager.getTransaction().commit();
    }

    public static void update(Object object) throws IllegalArgumentException, TransactionRequiredException {
        eManager.getTransaction().begin();
        eManager.merge(object);
        eManager.getTransaction().commit();
    }

    public static void tranxBegin() throws Exception {
        try {
            if(!eManager.getTransaction().isActive()) { 
                eManager.getTransaction().begin();
            } else
                eManager.joinTransaction();
        } catch (Exception e) {
            Logger.getLogger(DBInitializer.class.getName()).log(Level.SEVERE, "Failed: During transaction begin.", e);
            throw new Exception(e);
        } finally {
        }
    }

    /**
     * This method starts entity manager transaction. It should be used with
     * tranxEnd(), tranxCancel and tranxBegin().
     *
     * @param object
     * @throws Exception
     */
    public static void tranxPersist(Object object) throws Exception {
        try {
            eManager.persist(object);
        } catch (Exception e) {
            Logger.getLogger(DBInitializer.class.getName()).log(Level.SEVERE, "Failed: During transaction persist.", e);
            throw new Exception(e);
        } finally {
        }
    }

    public static void tranxUpdate(Object object) throws Exception {
        try {
            eManager.merge(object);
        } catch (Exception e) {
            Logger.getLogger(DBInitializer.class.getName()).log(Level.SEVERE, "Failed: During transaction persist.", e);
            throw new Exception(e);
        } finally {
        }
    }

    public static void tranxEnd() throws Exception {
        try {
            eManager.getTransaction().commit();
        } catch (Exception ex) {
            Logger.getLogger(DBInitializer.class.getName()).log(Level.SEVERE, "Failed: During transaction commit.", ex);
            throw new Exception(ex);
        } finally {
        }
    }

    public static void tranxCancel() {
        try {
            eManager.clear();
            eManager.getTransaction().rollback();
        } catch (Exception e) {
            Logger.getLogger(DBInitializer.class.getName()).log(Level.SEVERE, "Failed: During transaction rollback.", e);
        }
    }

    public static boolean tranxStatus() {
        boolean s = false;
        s = eManager.getTransaction().isActive();
        return s;
    }

    public static void close() {
        if (eManager.isOpen()) {
            try {
                eManager.close();
            } catch (Exception e) {
                Logger.getLogger(DBInitializer.class.getName()).log(Level.SEVERE, "Failed: During transaction close.", e);
            }
        }
    }

    public static void deleteEntity(Object o) {
        eManager.remove(o);
    }

    public static List<Roles> getRoleByID(String id) throws Exception {
        Roles role;
        Query queryRoleByID = eManager.createNamedQuery("Roles.findByIdroles");
        queryRoleByID.setParameter("idroles", id);
        List matchedRoles = queryRoleByID.getResultList();
        //role = (Roles) matchedRoles.get(0);
        return matchedRoles;
    }

    public static Users getUsersByID(String uname) throws Exception {
        Users u = null;
        Query queryUsersByID = eManager.createNamedQuery("Users.findByUsername");
        queryUsersByID.setParameter("username", uname);
        List matchedUser = queryUsersByID.getResultList();
        if (matchedUser.size() > 0) {
            u = (Users) matchedUser.get(0);
        }
        Logger.getLogger(DBInitializer.class.getName()).log(Level.INFO, "getUserByID: USER-ID: "+uname+", MATCHED-USER-LIST: "+matchedUser.toString());     
        return u;
    }

    public static Drawings getDrawingsByUsernameCaption(String uname, String caption) throws Exception {
        Drawings d = null;
        Query query = eManager.createNamedQuery("Drawings.findByUsernameCaption");
        query.setParameter("username", uname);
        query.setParameter("caption", caption);
        List matchedDrawings = query.getResultList();
        if (matchedDrawings.size() > 0) {
            d = (Drawings) matchedDrawings.get(0);
        }
        Logger.getLogger(DBInitializer.class.getName()).log(Level.INFO, "getDrawingsByUsernameCaption: USER-ID: "+uname+" ,CAPTION:"+caption+", MATCHED-USER-LIST: "+matchedDrawings.toString());     
        return d;
    }

}
