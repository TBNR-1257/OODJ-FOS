package com.mycompany.fos_app.GUI;

public class SessionManager {
    private static SessionManager instance;
    private String idInput;

    private SessionManager() { }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setidInput(String idInput) {
        this.idInput = idInput;
        // Print the value to verify the ID
        System.out.println("idInput set to: " + idInput);
    }

    public String getidInput() {
        return idInput;
    }
}


