package com.ft.fidelity.domain;

public class BonusRequest {

    private int idUsuario;
    private int value;

    public BonusRequest(int idUsuario, int value) {
        this.idUsuario = idUsuario;
        this.value = value;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
