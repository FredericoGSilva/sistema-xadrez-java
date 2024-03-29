package aplicacao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import xadrez.Cor;
import xadrez.Partida;
import xadrez.PecaPartida;
import xadrez.PosicaoXadrez;

public class UI {

	// stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	
	// cores do texto
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	// cores do fundo
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m"; 
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static void limparTela() {
		System.out.println("\033[H\033[2J");
		System.out.flush();
	}
	
	public static PosicaoXadrez lerPosicaoXadrez(Scanner scan) {
		try {
			String texto = scan.nextLine();
			char coluna = texto.charAt(0);
			int linha = Integer.parseInt(texto.substring(1));
			return new PosicaoXadrez(coluna, linha);
		} catch (RuntimeException e) {
			throw new InputMismatchException
			("Erro lendo PosicaoXadrez. Valores válidos são de a1 até h8.");
		}
	}
	
	public static void imprimirPartida(Partida partida, List<PecaPartida> capturadas) {
		printTabuleiro(partida.getPecaPartida());
		System.out.println();
		printPecasCapturadas(capturadas);
		System.out.println();
		System.out.println("Turno: " + partida.getTurno());
		
		if (!partida.getXequeMate()) {
			System.out.println("Jogador esperando: " + partida.getJogadorAtual());
			if (partida.getXeque()) {
				System.out.println("Xeque!");
			}
		} else {
			System.out.println("Xeque-Mate!");
			System.out.println("Vencedor " + partida.getJogadorAtual());
		}
	}

	public static void printTabuleiro(PecaPartida[][] pecas) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	public static void printTabuleiro(PecaPartida[][] pecas, boolean[][] movimentosPossiveis) {
		for (int i = 0; i < pecas.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j], movimentosPossiveis[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	public static void printPeca(PecaPartida peca, boolean fundo) {
		if (fundo) { 
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (peca == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (peca.getCor() == Cor.BRANCA) {
				System.out.print(ANSI_WHITE + peca + ANSI_RESET);
			}
			//System.out.print(peca);
			else {
				System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}
	
	public static void printPecasCapturadas(List<PecaPartida> capturadas) {
		List<PecaPartida> brancas = capturadas.stream().filter(
				x -> x.getCor() == Cor.BRANCA).collect(Collectors.toList());
		List<PecaPartida> pretas = capturadas.stream().filter(
				x -> x.getCor() == Cor.PRETA).collect(Collectors.toList());
		System.out.println("Pecas Capturadas: ");
		System.out.print("Brancas: " + ANSI_WHITE);
		System.out.println(Arrays.toString(brancas.toArray()));
		System.out.print(ANSI_RESET);
		System.out.print("Pretas: " + ANSI_YELLOW);
		System.out.println(Arrays.toString(pretas.toArray()));
		System.out.print(ANSI_RESET);
	}

}
