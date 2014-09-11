/*
    Document   : ActionSupportTestPersistance.java
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
package actions;

import com.opensymphony.xwork2.ActionSupport;
import dbbeans.DBInitializer;
import dbbeans.Roles;
import dbbeans.Users;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;

/**
 *
 * @author Hammad
 */
public class ActionSupportTestPersistance extends ActionSupport {

    private static Logger logger = null;
    private String username = "";
    private String password = "";
    private boolean mailinglist = false;
    private String email = "";
    
    public ActionSupportTestPersistance() {
        super();
        logger = Logger.getLogger(actions.ActionSupportTestPersistance.class.getName());
        logger.log(Level.INFO, "ActionSupportTestHibernate - Startup");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMailinglist() {
        return mailinglist;
    }

    public void setMailinglist(boolean mailinglist) {
        this.mailinglist = mailinglist;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
    
    /**
     * Test if webuser role exists. If not persist webuser role. persist the new
     * user. begin trans. relate new user to webuser. commit trans.
     *
     * @return
     * @throws Exception
     */
    @Override
    public String execute() throws Exception {
        Users user;
        Roles role;
        if (!checkIfRoleExists("webuser")) {
            createRole("webuser", "Web-site users role");
        }
        try {
            DBInitializer.tranxBegin();
            user = createUserEntityAndRelateRole("webuser");
            role = updateRoleEntityRelationship("webuser", user);
            DBInitializer.tranxPersist(user);
            DBInitializer.tranxUpdate(role);
            DBInitializer.tranxEnd();
        } catch (EntityExistsException eee) {
            return ("failure");
        } catch (PersistenceException pe) {
            return ("failure");
        } catch (Exception e) {
            return ("failure");
        }
        return ("success");
    }

    private boolean checkIfRoleExists(String role) {
        List<Roles> webUserRoleList;
        try {
            webUserRoleList = DBInitializer.getRoleByID(role);
            if(webUserRoleList.isEmpty())
                return false;
        } catch (Exception e) {
            logger.log(Level.INFO, "Role Name: " + role + " webuser = Exception or not found.");
            return false;
        }
        return (true);
    }

    private void createRole(String role1, String description) {
        Roles role = new Roles();
        role.setIdroles(role1);
        role.setDescription(description);
        try {
            DBInitializer.persist(role);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Role: " + role1 +" role creation failed. "+e.getMessage());            
        }
    }

    private Users createUserEntityAndRelateRole(String role) {
        List<Roles> webUserRoleList=null;
        logger.log(Level.INFO, "Parameters input are = {0}, {1}, {2}, {3}", new Object[]{username, password, mailinglist, email});
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        boolean b = false;
        try {
        webUserRoleList = DBInitializer.getRoleByID(role);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Role: WebUser retrival failed. {0}", e.getMessage());                        
        }
        user.setRolesList(webUserRoleList);
        if (mailinglist == true) {
            user.setMailinglist(Character.valueOf('Y'));
        } else {
            user.setMailinglist(Character.valueOf('N'));
        }
        logger.log(Level.INFO, "User Role List: {0}", user.getRolesList().listIterator().next().toString());
        return user;
    }

    private Roles updateRoleEntityRelationship(String rolename, Users user) throws Exception {
        Roles role=null;
        List<Roles> webUserRoleList = null;
        List<Users> roleRelatedUserList = null;
        webUserRoleList = DBInitializer.getRoleByID(rolename);
        role = (Roles) webUserRoleList.listIterator().next();
        roleRelatedUserList = role.getUsersList();
        roleRelatedUserList.add(user);
        role.setUsersList(roleRelatedUserList);
        return role;
    }
}
