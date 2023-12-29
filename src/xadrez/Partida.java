package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.PosicaoTabuleiro;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class Partida {

	// Regras do jogo.
	
	private int turno;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean xeque;
	// controle de peças
	private List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();
	
	public Partida() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadorAtual = Cor.BRANCA;
		configuracaoInicial();
	}
	
	public int getTurno() {
		return turno;
	}
	
	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getXeque() {
		return xeque;
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
		if (testXeque(jogadorAtual)) {
			desfazerMovimento(origemMatriz, destinoMatriz, capturaPeca);
			throw new XadrezExcecao("Você não pode se colocar em xeque.");
		}
		xeque = (testXeque(oponente(jogadorAtual))) ? true : false;
		proximoTurno();
		return (PecaPartida) capturaPeca;
	}
	
	public Peca movimentarPeca(PosicaoTabuleiro posicaoOrigem, PosicaoTabuleiro posicaoDestino) {
		Peca peca = tabuleiro.removerPeca(posicaoOrigem);
		Peca pecaCapturada = tabuleiro.removerPeca(posicaoDestino);
		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		tabuleiro.colocarPecaPosicao(peca, posicaoDestino);
		return pecaCapturada;
	}
	
	public void desfazerMovimento(PosicaoTabuleiro origem, PosicaoTabuleiro destino, Peca capturada) {
		Peca peca = tabuleiro.removerPeca(destino);
		tabuleiro.colocarPecaPosicao(peca, origem);
		if (capturada != null) {
			tabuleiro.colocarPecaPosicao(capturada, destino);
			pecasCapturadas.remove(capturada);
			pecasNoTabuleiro.add(capturada);
		}
	}
	
	public void validarPosicaoOrigem(PosicaoTabuleiro posicao) {
		if (!tabuleiro.existePeca(posicao)) {
			throw new XadrezExcecao("Não existe peça na posição de origem.");
		}
		if (jogadorAtual != ((PecaPartida) tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezExcecao("A peça escolhida não é sua.");
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
	
	public void proximoTurno() {
		turno++;
		jogadorAtual = jogadorAtual == Cor.BRANCA ? Cor.PRETA : Cor.BRANCA;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCA) ? Cor.PRETA : Cor.BRANCA;
	}
	
	private PecaPartida rei(Cor cor) {
		List<Peca> lista = pecasNoTabuleiro.stream().filter(
				x -> ((PecaPartida) x).getCor() == cor).collect(Collectors.toList());
		for (Peca p : lista) {
			if (p instanceof Rei) {
				return (PecaPartida) p;
			}
		}
		throw new IllegalStateException("Não existe rei na cor " + cor + " no tabuleiro.");
	}
	
	private boolean testXeque(Cor cor) {
		PosicaoTabuleiro posicaoRei = rei(cor).getPosicaoXadrez().posicaoMatriz();
		List<Peca> pecasOponente = pecasNoTabuleiro.stream().filter(x -> (
				(PecaPartida) x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : pecasOponente) {
			boolean[][] matriz = p.movimentosPossiveis();
			if (matriz[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	
	private void colocarNovaPeca(char coluna, int linha, PecaPartida peca) {
		tabuleiro.colocarPecaPosicao(peca, new PosicaoXadrez(coluna, linha).posicaoMatriz());
		pecasNoTabuleiro.add(peca);
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
