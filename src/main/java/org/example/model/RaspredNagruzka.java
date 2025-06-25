package org.example.model;

public class RaspredNagruzka {
    private int id;
    private int prepodId;
    private int gruppaId;
    private int predmetId;
    private String vid;
    private int chasy;
    private String uchebniyGod;
    private int kolichestvo;

    // Новые отображаемые поля
    private String prepodName;
    private String gruppaName;
    private String predmetName;

    // Конструктор для вывода с именами
    public RaspredNagruzka(int id, int prepodId, int gruppaId, int predmetId,
                           String vid, int chasy, String uchebniyGod, int kolichestvo,
                           String prepodName, String gruppaName, String predmetName) {
        this.id = id;
        this.prepodId = prepodId;
        this.gruppaId = gruppaId;
        this.predmetId = predmetId;
        this.vid = vid;
        this.chasy = chasy;
        this.uchebniyGod = uchebniyGod;
        this.kolichestvo = kolichestvo;
        this.prepodName = prepodName;
        this.gruppaName = gruppaName;
        this.predmetName = predmetName;
    }

    // Конструктор базовый (если нужно использовать без имён)
    public RaspredNagruzka(int id, int prepodId, int gruppaId, int predmetId,
                           String vid, int chasy, String uchebniyGod, int kolichestvo) {
        this(id, prepodId, gruppaId, predmetId, vid, chasy, uchebniyGod, kolichestvo, null, null, null);
    }

    // Геттеры
    public int getId() { return id; }
    public int getPrepodId() { return prepodId; }
    public int getGruppaId() { return gruppaId; }
    public int getPredmetId() { return predmetId; }
    public String getVid() { return vid; }
    public int getChasy() { return chasy; }
    public String getUchebniyGod() { return uchebniyGod; }
    public int getKolichestvo() { return kolichestvo; }

    public String getPrepodName() { return prepodName; }
    public String getGruppaName() { return gruppaName; }
    public String getPredmetName() { return predmetName; }

    // toString для отображения в ListView
    @Override
    public String toString() {
        return "Преподаватель: " + (prepodName != null ? prepodName : "ID=" + prepodId) +
                " | Группа: " + (gruppaName != null ? gruppaName : "ID=" + gruppaId) +
                " | Предмет: " + (predmetName != null ? predmetName : "ID=" + predmetId) +
                " | Вид: " + vid +
                " | Часы: " + chasy +
                " | Кол-во занятий: " + kolichestvo +
                " | Год: " + uchebniyGod;
    }
}
