package tabuleiro;

public class Posicao {
	
	private int linha;
	private int coluna;
	
	public Posicao(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	public int getColuna() {
		return coluna;
	}
	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	//Imprimir posição na tela.
	@Override
	public String toString() {
		return linha + ", " + coluna;
	}
	

}
