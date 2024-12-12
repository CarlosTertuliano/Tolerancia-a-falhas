package com.ft.ecommerce.domain;

public class BuyRequest {

    /* product – id do produto a ser comprado
        • user – id do usuário que está executando a compra
        • ft – parâmetro que vai indicar se a tolerância a falhas está ativada ou não (true ou false)
     */

    private int idProduct;

    private int idUsuario;

    private boolean ft;

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isFt() {
        return ft;
    }

    public void setFt(boolean ft) {
        this.ft = ft;
    }
}
