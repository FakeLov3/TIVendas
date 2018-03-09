package com.ticonsultoria.tivendas.tivendas.model;

import java.util.Date;

/**
 * Created by Helder on 27/02/2018.
 */

public class Sumario implements EntidadePersistivel {

    private int id;
    private String nomeTabela;
    private Date lastSync;
    private int emp_codigo;

    public Sumario() {

    }

    public Sumario(int id, String nomeTabla, Date lastSync) {
        this.nomeTabela = nomeTabla;
        this.lastSync = lastSync;
        this.id = id;
    }

    public int getEmp_codigo() {
        return emp_codigo;
    }

    public void setEmp_codigo(int emp_codigo) {
        this.emp_codigo = emp_codigo;
    }

    public String getNomeTabela() {
        return nomeTabela;
    }

    public void setNomeTabela(String nomeTabla) {
        this.nomeTabela = nomeTabla;
    }

    public Date getLastSync() {
        return lastSync;
    }

    public void setLastSync(Date lastSync) {
        this.lastSync = lastSync;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
