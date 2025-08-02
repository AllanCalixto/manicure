package br.com.calixto.manicure.entity;

public enum TipoServico {
    MAO(23.00),
    PE(23.00),
    MAO_E_PE(45.00);

    private final double preco;

    TipoServico(double preco) {
        this.preco = preco;
    }

    public double getPreco() {
        return preco;
    }
}
