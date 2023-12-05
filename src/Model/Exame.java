package Model;

import java.math.BigDecimal;

public class Exame {
    private int id;
    private String nomeExame;
    private String tipoExame;

    public Exame() {}

    public Exame(int id, String nomeExame, String tipoExame) {
        this.id = id;
        this.nomeExame = nomeExame;
        this.tipoExame = tipoExame;
    }

    public int getId() {
        return id;
    }

    public String getNomeExame() {
        return nomeExame;
    }

    public void setNomeExame(String nomeExame) {
        this.nomeExame = nomeExame;
    }

    public String getTipoExame() {
        return tipoExame;
    }

    public void setTipoExame(String tipoExame) {
        this.tipoExame = tipoExame;
    }
}
