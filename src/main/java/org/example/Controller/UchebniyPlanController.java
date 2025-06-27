package org.example.Controller;

import org.example.model.UchebniyPlan;
import org.example.model.UchebniyPlanRepository;

import java.sql.SQLException;
import java.util.List;

public class UchebniyPlanController {
    private final UchebniyPlanRepository repository;

    public UchebniyPlanController() {
        this.repository = new UchebniyPlanRepository();
    }

    public List<UchebniyPlan> getAllPlans() throws SQLException {
        return repository.getAll();
    }

    public void addPlan(int predmetId, int semestr, boolean obyazatelniy) throws SQLException {
        repository.add(predmetId, semestr, obyazatelniy);
    }

    public void deletePlan(int id) throws SQLException {
        repository.delete(id);
    }
}
