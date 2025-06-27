package org.example.Controller;

import org.example.model.Gruppa;
import org.example.model.Gruppy;
import org.example.view.GruppaView;

import java.sql.SQLException;
import java.util.List;

public class GruppaController {
    private final GruppaView view;
    private final Gruppy model;

    public GruppaController(GruppaView view) {
        this.view = view;
        this.model = new Gruppy();
    }

    public List<Gruppa> getAllGruppy() throws SQLException {
        return model.getAll();
    }

    public void addGruppa(String name) throws SQLException {
        model.add(name);
    }

    public void deleteGruppa(int id) throws SQLException {
        model.delete(id);
    }
}
