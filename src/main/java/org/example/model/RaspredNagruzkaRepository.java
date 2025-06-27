package org.example.model;

import org.example.db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RaspredNagruzkaRepository {

    public List<RaspredNagruzka> getAll() throws SQLException {
        List<RaspredNagruzka> list = new ArrayList<>();

        String sql = """
        SELECT rn.id, rn.prepod_id, rn.vid, rn.chasy, rn.god, rn.kolichestvo,
               CONCAT(p.familiya, ' ', p.imya, ' ', p.otchestvo) AS prepod_name
        FROM raspred_nagruzka rn
        JOIN prepodavatel p ON rn.prepod_id = p.id
    """;

        List<Integer> ids = new ArrayList<>();
        List<Integer> prepodIds = new ArrayList<>();
        List<String> vids = new ArrayList<>();
        List<Integer> chasys = new ArrayList<>();
        List<String> gods = new ArrayList<>();
        List<Integer> kolichestvas = new ArrayList<>();
        List<String> prepodNames = new ArrayList<>();

        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ids.add(rs.getInt("id"));
                prepodIds.add(rs.getInt("prepod_id"));
                vids.add(rs.getString("vid"));
                chasys.add(rs.getInt("chasy"));
                gods.add(rs.getString("god"));
                kolichestvas.add(rs.getInt("kolichestvo"));
                prepodNames.add(rs.getString("prepod_name"));
            }
        }

        for (int i = 0; i < ids.size(); i++) {
            int nagruzkaId = ids.get(i);
            List<String> predmety = getPredmetyForNagruzka(nagruzkaId);
            List<String> gruppy = getGruppyForNagruzka(nagruzkaId);

            RaspredNagruzka r = new RaspredNagruzka(
                    nagruzkaId,
                    prepodIds.get(i),
                    vids.get(i),
                    chasys.get(i),
                    gods.get(i),
                    kolichestvas.get(i),
                    prepodNames.get(i),
                    predmety,
                    gruppy
            );
            list.add(r);
        }

        return list;
    }


    public int add(int prepodId, String vid, int chasy, int kolichestvo,
                   ArrayList<Object> predmetIds, List<Integer> gruppaIds) throws SQLException {

        String insertNagruzkaSQL = """
            INSERT INTO raspred_nagruzka (prepod_id, vid, chasy, god, kolichestvo)
            VALUES (?, ?, ?, YEAR(CURDATE()), ?)
        """;

        try (Connection conn = DatabaseManager.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(insertNagruzkaSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, prepodId);
                stmt.setString(2, vid);
                stmt.setInt(3, chasy);
                stmt.setInt(4, kolichestvo);
                stmt.executeUpdate();

                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int nagruzkaId = generatedKeys.getInt(1);

                    for (Object predId : predmetIds) {
                        addPredmetToNagruzka(conn, (Integer) predId, nagruzkaId);
                    }
                    for (int groupId : gruppaIds) {
                        addGruppaToNagruzka(conn, groupId, nagruzkaId);
                    }

                    conn.commit();
                    return nagruzkaId;
                } else {
                    conn.rollback();
                    throw new SQLException("Не удалось получить ID добавленной нагрузки.");
                }
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    private void addPredmetToNagruzka(Connection conn, int predmetId, int nagruzkaId) throws SQLException {
        String sql = "INSERT INTO predmet_nagruzka (id_predmeta, id_nagruzki) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, predmetId);
            stmt.setInt(2, nagruzkaId);
            stmt.executeUpdate();
        }
    }

    private void addGruppaToNagruzka(Connection conn, int gruppaId, int nagruzkaId) throws SQLException {
        String sql = "INSERT INTO gruppa_nagruzka (id_gruppy, id_nagruzki) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gruppaId);
            stmt.setInt(2, nagruzkaId);
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection conn = DatabaseManager.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM predmet_nagruzka WHERE id_nagruzki = ?")) {
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                }
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM gruppa_nagruzka WHERE id_nagruzki = ?")) {
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                }
                try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM raspred_nagruzka WHERE id = ?")) {
                    stmt.setInt(1, id);
                    stmt.executeUpdate();
                }
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    public List<String> getGruppyForNagruzka(int nagruzkaId) throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = """
            SELECT g.name
            FROM gruppa_nagruzka gn
            JOIN gruppa g ON gn.id_gruppy = g.id
            WHERE gn.id_nagruzki = ?
        """;
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nagruzkaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("name"));
                }
            }
        }
        return list;
    }

    public List<String> getPredmetyForNagruzka(int nagruzkaId) throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = """
            SELECT p.name
            FROM predmet_nagruzka pn
            JOIN predmet p ON pn.id_predmeta = p.id
            WHERE pn.id_nagruzki = ?
        """;
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nagruzkaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("name"));
                }
            }
        }
        return list;
    }
}