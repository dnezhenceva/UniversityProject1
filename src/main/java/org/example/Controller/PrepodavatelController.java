package org.example.Controller;

import org.example.model.Prepodavatel;
import org.example.model.Prepodavateli;
import org.example.view.PrepodView;

import java.sql.SQLException;
import java.util.List;

public class PrepodavatelController {
    private final PrepodView view;
    private final Prepodavateli model;

    public PrepodavatelController(PrepodView view) {
        this.view = view;
        this.model = new Prepodavateli();
    }

    public List<String> getNagruzkaForPrepod(int prepodId) throws SQLException {
        return model.getNagruzkaByPrepod(prepodId);
    }

    public List<Prepodavatel> getAllPrepodavateli() throws SQLException {
        return model.getAll();
    }

    public void addPrepodavatel(String familiya, String imya, String otchestvo,
                                String stepen, String dolzhnost, int stazh) throws SQLException {
        model.add(familiya, imya, otchestvo, stepen, dolzhnost, stazh);
    }

    public void deletePrepodavatel(int id) throws SQLException {
        model.delete(id);
    }
}
