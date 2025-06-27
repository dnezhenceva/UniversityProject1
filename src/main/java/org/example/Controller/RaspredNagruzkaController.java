package org.example.Controller;

import org.example.model.RaspredNagruzka;
import org.example.model.RaspredNagruzkaRepository;
import org.example.view.RaspredNagruzkaView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RaspredNagruzkaController {
    private final RaspredNagruzkaRepository raspredNagruzkaRepo = new RaspredNagruzkaRepository();
    private final RaspredNagruzkaView view;

    public RaspredNagruzkaController(RaspredNagruzkaView view) {
        this.view = view;
    }

    public List<RaspredNagruzka> getAllZanyatia() throws SQLException {
        return raspredNagruzkaRepo.getAll();
    }

    public void addZanyatie(int prepodId, String vid, int chasy, int kolichestvo,
                            ArrayList<Object> predmetIds, List<Integer> gruppaIds) throws SQLException {
        raspredNagruzkaRepo.add(prepodId, vid, chasy, kolichestvo, predmetIds, gruppaIds);
    }

    public void deleteZanyatie(int id) throws SQLException {
        raspredNagruzkaRepo.delete(id);
    }
}
