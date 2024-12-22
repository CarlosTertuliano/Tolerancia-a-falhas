package com.ft.ecommerce.domain;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BonusRequest that = (BonusRequest) o;
        return idUsuario == that.idUsuario && value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, value);
    }
}
