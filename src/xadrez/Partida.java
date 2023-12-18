package xadrez;

import tabuleiro.Peca;
import tabuleiro.PosicaoTabuleiro;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class Partida {

	// Regras do jogo.
	
	private Tabuleiro tabuleiro;
	
	public Partida() {
		tabuleiro = new Tabuleiro(8, 8);
		configuracaoInicial();
	}
	
	public PecaPartida[][] getPecaPartida() {
		PecaPartida[][] matriz = new PecaPartida[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				matriz[i][j] = (PecaPartida) tabuleiro.peca(i, j);
			}
		}
		return matriz;
	}
	
	// Ao escolher uma peça -> indicar para quais posições a peça pode mexer
	public boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem) {
		PosicaoTabuleiro posicao = posicaoOrigem.posicaoMatriz();
		validarPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}
	
	public PecaPartida executarMovimentoXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		// converter posicaoOrigem e posicaoDestino p/ posição da matriz.
		PosicaoTabuleiro origemMatriz = posicaoOrigem.posicaoMatriz();
		PosicaoTabuleiro destinoMatriz = posicaoDestino.posicaoMatriz();
		validarPosicaoOrigem(origemMatriz);
		validarPosicaoDestino(origemMatriz, destinoMatriz);
		Peca capturaPeca = movimentarPeca(origemMatriz, destinoMatriz);
		return (PecaPartida) capturaPeca;
	}
	
	public void validarPosicaoOrigem(PosicaoTabuleiro posicao) {
		if (!tabuleiro.existePeca(posicao)) {
			throw new XadrezExcecao("Não existe peça na posição de origem.");
		}
		if (!tabuleiro.peca(posicao).existeAlgumMovimentoPossivel()) {
			throw new XadrezExcecao("Não existe movimentos possíveis para a peça escolhida.");
		}
	}
	
	private void validarPosicaoDestino(PosicaoTabuleiro posicaoOrigem, PosicaoTabuleiro posicaoDestino) {
		if (!tabuleiro.peca(posicaoOrigem).possiveisMovimentos(posicaoDestino)) {
			throw new XadrezExcecao("A peça escolhida não pode se mover para a posição de destino."); 
		}
	}
	
	public Peca movimentarPeca(PosicaoTabuleiro posicaoOrigem, PosicaoTabuleiro posicaoDestino) {
		Peca peca = tabuleiro.removerPeca(posicaoOrigem);
		Peca pecaCapturada = tabuleiro.removerPeca(posicaoDestino);
		tabuleiro.colocarPecaPosicao(peca, posicaoDestino);
		return pecaCapturada;
	}
	
	private void colocarNovaPeca(char coluna, int linha, PecaPartida peca) {
		tabuleiro.colocarPecaPosicao(peca, new PosicaoXadrez(coluna, linha).posicaoMatriz());
	}
	
	public void configuracaoInicial() {
		colocarNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('d', 1, new Rei(tabuleiro, Cor.BRANCA));
		colocarNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCA));

		colocarNovaPeca('c', 7, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeca('c', 8, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeca('d', 7, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeca('e', 7, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeca('e', 8, new Torre(tabuleiro, Cor.PRETA));
		colocarNovaPeca('d', 8, new Rei(tabuleiro, Cor.PRETA));
		/*
		tabuleiro.colocarPecaPosicao(new Torre(tabuleiro, Cor.BRANCA), new PosicaoTabuleiro(2, 1));
		tabuleiro.colocarPecaPosicao(new Rei(tabuleiro, Cor.PRETA), new PosicaoTabuleiro(0, 4));
		tabuleiro.colocarPecaPosicao(new Rei(tabuleiro, Cor.BRANCA), new PosicaoTabuleiro(7, 4));
		*/
	}
	
}
