package com.ticonsultoria.tivendas.tivendas.model;

/**
 * Created by Helder on 16/02/2018.
 */

public class Empresa {

    private String emp_razao;
    private String emp_fantazia;
    private String emp_cnpj;
    private String emp_chave;
    private int emp_codigo;

    public Empresa(String emp_razao, String emp_fantazia, String emp_cnpj, String emp_chave, int emp_codigo) {
        this.emp_razao = emp_razao;
        this.emp_fantazia = emp_fantazia;
        this.emp_cnpj = emp_cnpj;
        this.emp_chave = emp_chave;
        this.emp_codigo = emp_codigo;
    }

    public String getEmp_razao() {
        return emp_razao;
    }

    public void setEmp_razao(String emp_razao) {
        this.emp_razao = emp_razao;
    }

    public String getEmp_fantazia() {
        return emp_fantazia;
    }

    public void setEmp_fantazia(String emp_fantazia) {
        this.emp_fantazia = emp_fantazia;
    }

    public String getEmp_cnpj() {
        return emp_cnpj;
    }

    public void setEmp_cnpj(String emp_cnpj) {
        this.emp_cnpj = emp_cnpj;
    }

    public String getEmp_chave() {
        return emp_chave;
    }

    public void setEmp_chave(String emp_chave) {
        this.emp_chave = emp_chave;
    }

    public int getEmp_codigo() {
        return emp_codigo;
    }

    public void setEmp_codigo(int emp_codigo) {
        this.emp_codigo = emp_codigo;
    }
}
