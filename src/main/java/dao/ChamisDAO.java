package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import classes.Chamis;

public class ChamisDAO extends DAO<Chamis> {

    public ChamisDAO(Connection conn) {
        super(conn);
    }
    /*-------creation d'un nouveau chamis ---------------*/
    @Override
    public boolean create(Chamis obj){
        int nb = 0;
        try {
            nb = this.connect.createStatement().executeUpdate("INSERT INTO chamis VALUES ('"+obj.pseudo+"',"+obj.age+",'"+obj.ville+"','"+obj.description+"','"+obj.email+"' )");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        if(nb==1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Chamis read(int id) {
        return null;
    }
    /*-------Mise à jour d'un chamis ---------------*/
    @Override
    public boolean update(Chamis obj) {
        int nb = 0;
        try {
            nb = this.connect.createStatement().executeUpdate("UPDATE chamis SET age = "+obj.age+",ville = "+obj.ville+", description = "+obj.age+",email = "+obj.email+"  where pseudo = '"+obj.pseudo+"'");   
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(nb==1) {
            return true;
        } else {
            return false;
        }
    }
    /*-------suppression d'un chamis ---------------*/
    @Override
    public boolean delete(Chamis obj) {
        int nb = 0;
        try {
        nb = this.connect.createStatement().executeUpdate("delete from chamis where pseudo ='"+obj.pseudo+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(nb==1) {
            return true;
        } else {
            return false;
        }
    }

    /*------- Lecture d'un chamis selon son login ---------------*/
    public Chamis readWithLogin(String id) {
        Chamis u = new Chamis();
        try {
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM chamis WHERE pseudo = '"+id+"'");
        if (rs.next()) {
            u.pseudo = rs.getString("pseudo");
            u.age   = rs.getInt("age");
            u.ville   = rs.getString("ville");
            u.description   = rs.getString("description");
            u.email   = rs.getString("email");
        }
        stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }
    /*-------Affichage de la liste des chamis---------------*/
    public ArrayList<Chamis> readAllChamis() {
        ArrayList<Chamis> L = new ArrayList<Chamis>();
        try {
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM chamis");
            while (rs.next()) {
                Chamis u = new Chamis();
                u.pseudo = rs.getString("pseudo");
                u.age   = rs.getInt("age");
                u.ville   = rs.getString("ville");
                u.description   = rs.getString("description");
                u.email   = rs.getString("email");
                L.add(u);
            }
            stmt.close();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return L;
    }
}
