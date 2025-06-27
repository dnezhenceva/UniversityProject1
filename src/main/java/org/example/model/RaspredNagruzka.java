package org.example.model;

import java.util.List;

public class RaspredNagruzka {
    private int id;
    private int prepodId;
    private String vid;
    private int chasy;
    private String uchebniyGod;
    private int kolichestvo;
    private String prepodName;
    private List<String> predmetNames;
    private List<String> gruppaNames;

    public RaspredNagruzka(int id, int prepodId, String vid, int chasy, String uchebniyGod,
                           int kolichestvo, String prepodName,
                           List<String> predmetNames, List<String> gruppaNames) {
        this.id = id;
        this.prepodId = prepodId;
        this.vid = vid;
        this.chasy = chasy;
        this.uchebniyGod = uchebniyGod;
        this.kolichestvo = kolichestvo;
        this.prepodName = prepodName;
        this.predmetNames = predmetNames;
        this.gruppaNames = gruppaNames;
    }

    public int getId() { return id; }
    public int getPrepodId() { return prepodId; }
    public String getVid() { return vid; }
    public int getChasy() { return chasy; }
    public String getUchebniyGod() { return uchebniyGod; }
    public int getKolichestvo() { return kolichestvo; }

    public String getPrepodName() { return prepodName; }
    public List<String> getPredmetNames() { return predmetNames; }
    public List<String> getGruppaNames() { return gruppaNames; }

    @Override
    public String toString() {
        return String.format("Преподаватель: %s | Вид: %s | Часы: %d | Группы: %s | Предметы: %s",
                prepodName, vid, chasy,
                String.join(", ", gruppaNames),
                String.join(", ", predmetNames));
    }

}
