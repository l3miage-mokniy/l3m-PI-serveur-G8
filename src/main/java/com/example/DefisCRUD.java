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
import dao.DefisDAO;

@RestController
@CrossOrigin
@RequestMapping("/api/defis") 
public class DefisCRUD {

    @Autowired
    private DataSource dataSource;
    
    @GetMapping("/")
    ArrayList<Defis> allDefis(HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            DefisDAO defis = new DefisDAO(connection);
            ArrayList<Defis> L = defis.readAllDefis();
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

    @GetMapping("/{defiId}")
    Defis read(@PathVariable(value="defiId") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            DefisDAO defisDAO = new DefisDAO(connection);
            Defis d = defisDAO.readWithId(id);
            connection.close();
            if(d.id.equals("null")) {
                throw new Exception("Defi inexistant");
            } else {
                return d;
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

    //Renvoyez une erreur 403 si une ressource existe déjà avec le même identifiant.
    //Renvoyer une erreur 412 si l'identifiant du Defi dans l'URL n'est pas le même que celui du Defi dans le corp de la requête.
    @PostMapping("/{defiId}")
    Defis create(@PathVariable(value="defiId") String id, @RequestBody Defis d, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            if(d.id.equals(id)) {
                DefisDAO defisDAO = new DefisDAO(connection);
                Defis dNew = defisDAO.readWithId(id);
                if(dNew.id == null) {
                    defisDAO.create(d);
                    dNew = defisDAO.readWithId(id);
                    connection.close();
                    return dNew;
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

    //Renvoyer une erreur 404 si l'identifiant de l'utilisateur ne correspond pas à un utilisateur dans la base.
    //Renvoyer une erreur 412 si l'identifiant du Defis dans l'URL n'est pas le même que celui du Defis dans le corp de la requête.
    @PutMapping("/{defiId}") 
    Defis update(@PathVariable(value="defiId") String id, @RequestBody Defis d, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
            if(d.id.equals(id)) {
                DefisDAO defisDAO = new DefisDAO(connection);
                Defis dNew = defisDAO.readWithId(id);
                if(dNew.id == null) {
                    throw new Exception("ERROR404");
                } else {
                    defisDAO.update(u);
                    dNew = defisDAO.readWithId(id);
                    connection.close();
                    return dNew;
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

    //Renvoyer une erreur 404 si l'identifiant de l'utilisateur ne correspond pas à un utilisateur dans la base.
    @DeleteMapping("/{defiId}")
    void delete(@PathVariable(value="defiId") String id, HttpServletResponse response) {
        try (Connection connection = dataSource.getConnection()) {
                DefisDAO defisDAO = new DefisDAO(connection);
                Defis dOld = DefisDAO.readWithLogin(id);
                if(dOld.id == null) {
                    throw new Exception("ERROR404");
                } else {
                    DefisDAO.delete(dOld);
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
