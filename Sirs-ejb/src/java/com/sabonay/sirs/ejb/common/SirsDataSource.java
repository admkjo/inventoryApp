/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sabonay.sirs.ejb.common;

import com.sabonay.sirs.ejb.sessionbeans.SirsSessionBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * 
 * @author emma
 */
public class SirsDataSource {
    public static SirsSessionBean sirsSession;
    static String JNDI_APPL_SESSION = "java:global/Sirs/Sirs-ejb/SirsSessionBean";
    static String APPL_SESSION_BEAN_FULL_PATH = "com.sabonay.sirs.ejb.sessionbeans.SirsSessionBean";

    public static SirsSessionBean dataSource() {

        if (sirsSession != null) {
            return sirsSession;
        }
        try {
            Context context = new InitialContext();
            sirsSession = (SirsSessionBean) context.lookup(JNDI_APPL_SESSION+"!"+APPL_SESSION_BEAN_FULL_PATH);
            return sirsSession;
        } catch (NamingException ne) {
            //dataSource();
            ne.printStackTrace();
            System.out.println("Error During Invocation "+ne.toString());
            dataSource();
            Logger.getLogger(SirsSessionBean.class.getName()).log(Level.SEVERE, "exception caught during invocation", ne);
            throw new RuntimeException(ne);

        }

    }
}
