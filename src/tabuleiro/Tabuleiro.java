package tabuleiro;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}
	
	//retorna matriz pecas na linha e na coluna.
	public Peca peca(int linha, int coluna) {
		return pecas[linha][coluna];
	}
	
	// sobrecarga -> retornar a peça pela posição.
	public Peca peca(Posicao posicao) {
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}

	public int getLinhas() {
		return linhas;
	}

	public void setLinhas(int linhas) {
		this.linhas = linhas;
	}

	public int getColunas() {
		return colunas;
	}

	public void setColunas(int colunas) {
		this.colunas = colunas;
	}

	
}
