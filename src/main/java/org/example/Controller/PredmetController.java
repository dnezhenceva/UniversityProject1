package org.example.Controller;

import org.example.model.Predmet;
import org.example.model.Predmety;
import org.example.view.PredmetView;

import java.sql.SQLException;
import java.util.List;

public class PredmetController {
    private final PredmetView view;
    private final Predmety model;

    public PredmetController(PredmetView view) {
        this.view = view;
        this.model = new Predmety();
    }

    public List<Predmet> getAllPredmeti() throws SQLException {
        return model.getAll();
    }

    public void addPredmet(String name) throws SQLException {
        model.add(name);
    }

    public void deletePredmet(int id) throws SQLException {
        model.delete(id);
    }
}
