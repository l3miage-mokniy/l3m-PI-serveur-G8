package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import classes.Defis;

public class DefisDAO extends DAO<Defis> {

    public DefisDAO(Connection conn) {
        super(conn);
    }

    /* ---- Création d'un nouveau defi ---- */
    @Override
    public boolean create(Defis obj){
        int nb = 0;
        try {
            nb = this.connect.createStatement().executeUpdate("INSERT INTO defi VALUES ('"+obj.getDefi()+"','"+obj.getTitre()+"','"+obj.getDateDeCreation()+"','"+obj.getDescription()+"','"+obj.getAuteur()+"','"+obj.getCode_arret()+"','"+obj.getType()+"','"+obj.getDateDeModification()+"',"+obj.getVersion()+",'"+obj.getArret()+"',"+obj.getPoints()+",'"+obj.getDuree()+"','"+obj.getPrologue()+"','"+obj.getEpilogue()+"','"+obj.getCommentaire()+"')");
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
    public Defis read(int id) {
        return null;
    }

    /* ---- Modification d'un defi ---- */
    @Override
    public boolean update(Defis obj) {
        int nb = 0;
        try {
            nb = this.connect.createStatement().executeUpdate("UPDATE defi SET titre = '"+obj.getTitre()+"', dateDeCreation = '"+obj.getDateDeCreation()+"',  description = '"+obj.getDescription()+"', auteur = '"+obj.getAuteur()+"', code_arret = '"+obj.getCode_arret()+"', type = '"+obj.getType()+"', dateDeModification = '"+obj.getDateDeModification()+"', version = '"+obj.getVersion()+"',  arret = '"+obj.getArret()+"', points = '"+obj.getPoints()+"', duree = '"+obj.getDuree()+"', prologue = '"+obj.getPrologue()+"', epilogue = '"+obj.getEpilogue()+"', commentaire = '"+obj.getCommentaire()+"' WHERE defi = '"+obj.getDefi()+"'");   
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(nb==1) {
            return true;
        } else {
            return false;
        }
    }

    /* ---- Suppression d'un defi ---- */
    @Override
    public boolean delete(Defis obj) {
        int nb = 0;
        try {
        nb = this.connect.createStatement().executeUpdate("delete from defi where defi ='"+obj.getDefi()+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(nb==1) {
            return true;
        } else {
            return false;
        }
    }

    /* ---- Affichage de tous les defis voulus ---- */
    public Defis readWithId(String id) {
        Defis d = new Defis();
        try {
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM defi WHERE defi = '"+id+"'");
        if (rs.next()) {
            d.setDefi(rs.getString("defi"));
            d.setTitre(rs.getString("titre"));
            d.setDateDeCreation(rs.getString("dateDeCreation"));
            d.setDescription(rs.getString("description"));
            d.setAuteur(rs.getString("auteur"));
            d.setCode_arret(rs.getString("code_arret"));
            d.setType(rs.getString("type"));
            d.setDateDeModification(rs.getString("dateDeModification"));
            d.setVersion(rs.getInt("version"));
            d.setArret(rs.getString("arret"));
            d.setPoints(rs.getInt("points"));
            d.setDuree(rs.getString("duree"));
            d.setPrologue(rs.getString("prologue"));
            d.setEpilogue(rs.getString("epilogue"));
            d.setCommentaire(rs.getString("commentaire"));
            
        }
        stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return d;
    }
    
    /* ---- Affichage de la liste de tous les defis ---- */
    public ArrayList<Defis> readAllDefis() {
        ArrayList<Defis> L = new ArrayList<Defis>();
        try {
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM defi");
            while (rs.next()) {
                Defis d = new Defis();
                d.setDefi(rs.getString("defi"));
                d.setTitre(rs.getString("titre"));
                d.setDateDeCreation(rs.getString("dateDeCreation"));
                d.setDescription(rs.getString("description"));
                d.setAuteur(rs.getString("auteur"));
                d.setCode_arret(rs.getString("code_arret"));
                d.setType(rs.getString("type"));
                d.setDateDeModification(rs.getString("dateDeModification"));
                d.setVersion(rs.getInt("version"));
                d.setArret(rs.getString("arret"));
                d.setPoints(rs.getInt("points"));
                d.setDuree(rs.getString("duree"));
                d.setPrologue(rs.getString("prologue"));
                d.setEpilogue(rs.getString("epilogue"));
                d.setCommentaire(rs.getString("commentaire"));
                L.add(d);
            }
            stmt.close();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return L;
    }

    public ArrayList<Defis> readAllDefisByAuteur(String auteur) {
        ArrayList<Defis> L = new ArrayList<Defis>();
        try {
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM defi WHERE auteur = '"+auteur+"'");
            while (rs.next()) {
                Defis d = new Defis();
                d.setDefi(rs.getString("defi"));
                d.setTitre(rs.getString("titre"));
                d.setDateDeCreation(rs.getString("dateDeCreation"));
                d.setDescription(rs.getString("description"));
                d.setAuteur(rs.getString("auteur"));
                d.setCode_arret(rs.getString("code_arret"));
                d.setType(rs.getString("type"));
                d.setDateDeModification(rs.getString("dateDeModification"));
                d.setVersion(rs.getInt("version"));
                d.setArret(rs.getString("arret"));
                d.setPoints(rs.getInt("points"));
                d.setDuree(rs.getString("duree"));
                d.setPrologue(rs.getString("prologue"));
                d.setEpilogue(rs.getString("epilogue"));
                d.setCommentaire(rs.getString("commentaire"));
                L.add(d);
            }
            stmt.close();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return L;
    }
}
