package com.example;

import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import dao.ChamisDAO;
//import com.example.DbConnection;
//import com.example.RestServer;
import classes.Chamis;

@RestController
@CrossOrigin
@RequestMapping("/api/chamis") 
public class ChamisCRUD {

    @Autowired
    private DataSource dataSource;
    /* ---- Cherche tous les chamis ---- */
    @GetMapping("/")
    ArrayList<Chamis> allChamis(HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            ChamisDAO chamis = new ChamisDAO(connection);
            ArrayList<Chamis> L = chamis.readAllChamis();
            return L;
        } catch (Exception e) {
            response.setStatus(500);
            try {
                response.getOutputStream().print( e.getMessage() );
            } catch (Exception e2) {
                System.err.println(e2.getMessage());
            }
            System.err.println(e.getMessage());
            return null;
        }
    }

    /* ---- Cherche l'élément voulu ---- */
    @GetMapping("/{chamisId}")
    Chamis read(@PathVariable(value="chamisId") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            ChamisDAO chamisDAO = new ChamisDAO(connection);
            Chamis u = chamisDAO.readWithEmail(id);
            connection.close();
            if(u.getPseudo().equals("null")) {
                throw new Exception("Chamis inexistant");
            } else {
                return u;
            }
        } catch (Exception e) {
            response.setStatus(404);
            try {
                response.getOutputStream().print( e.getMessage() );
            } catch (Exception e2) {
                System.err.println(e2.getMessage());
            }
            System.err.println(e.getMessage());
            return null;
        }
    }

    /* ---- Cherche l'élément voulu avec le pseudo ---- */
    @GetMapping("/pseudo/{chamisId}")
    Chamis readWPseudo(@PathVariable(value="chamisId") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            ChamisDAO chamisDAO = new ChamisDAO(connection);
            Chamis u = chamisDAO.readWithPseudo(id);
            connection.close();
            if(u.getPseudo().equals("null")) {
                throw new Exception("Chamis inexistant");
            } else {
                return u;
            }
        } catch (Exception e) {
            response.setStatus(404);
            try {
                response.getOutputStream().print( e.getMessage() );
            } catch (Exception e2) {
                System.err.println(e2.getMessage());
            }
            System.err.println(e.getMessage());
            return null;
        }
    }

    /* ---- Créé un élement ---- */
    //Renvoyez une erreur 403 si une ressource existe déjà avec le même identifiant.
    //Renvoyer une erreur 412 si l'identifiant du Chamis dans l'URL n'est pas le même que celui du Chamis dans le corp de la requête.
    @PostMapping("/{chamisId}")
    Chamis create(@PathVariable(value="chamisId") String id, @RequestBody Chamis u, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            if(u.getPseudo().equals(id)) {
                ChamisDAO chamisDAO = new ChamisDAO(connection);
                Chamis uNew = chamisDAO.readWithEmail(id);
                if(uNew.getPseudo() == null) {
                    chamisDAO.create(u);
                    uNew = chamisDAO.readWithEmail(id);
                    connection.close();
                    return uNew;
                } else {
                    throw new Exception("ERROR403");
                }
            } else {
                throw new Exception("ERROR412");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if(e.getMessage().equals("ERROR412")) {
                response.setStatus(412);
            } else if(e.getMessage().equals("ERROR403")) {
                response.setStatus(403);
            }
            return null;
        }
    }

    /* ---- Modifie un élément ---- */
    //Renvoyer une erreur 404 si l'identifiant de l'utilisateur ne correspond pas à un utilisateur dans la base.
    //Renvoyer une erreur 412 si l'identifiant du Chamis dans l'URL n'est pas le même que celui du Chamis dans le corp de la requête.
    @PutMapping("/{chamisId}") 
    Chamis update(@PathVariable(value="chamisId") String id, @RequestBody Chamis u, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            if(u.getEmail().equals(id)) {
                ChamisDAO chamisDAO = new ChamisDAO(connection);
                Chamis uNew = chamisDAO.readWithEmail(id);
                if(uNew.getPseudo() == null) {
                    throw new Exception("ERROR404");
                } else {
                    chamisDAO.update(u);
                    uNew = chamisDAO.readWithEmail(id);
                    connection.close();
                    return uNew;
                }
            } else {
                throw new Exception("ERROR412");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if(e.getMessage().equals("ERROR412")) {
                response.setStatus(412);
            } else if(e.getMessage().equals("ERROR404")) {
                response.setStatus(404);
            }
            return null;
        }
    }

    /* ---- Supprime un élément ---- */
    //Renvoyer une erreur 404 si l'identifiant de l'utilisateur ne correspond pas à un utilisateur dans la base.
    @DeleteMapping("/{chamisId}")
    void delete(@PathVariable(value="chamisId") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
                ChamisDAO chamisDAO = new ChamisDAO(connection);
                Chamis uOld = chamisDAO.readWithEmail(id);
                if(uOld.getPseudo() == null) {
                    throw new Exception("ERROR404");
                } else {
                    chamisDAO.delete(uOld);
                    connection.close();
                }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            if(e.getMessage().equals("ERROR404")) {
                response.setStatus(404);
            }
        }

    }
}
