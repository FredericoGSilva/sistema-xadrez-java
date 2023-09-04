package aplicacao;

import java.util.Scanner;

import tabuleiro.Tabuleiro;
import xadrez.Partida;
import xadrez.PecaPartida;
import xadrez.PosicaoXadrez;

public class Programa {
	public static void main(String[] args) {
		
		Partida partida = new Partida();
		
		Scanner scan = new Scanner(System.in);
		
		while (true) {
			UI.printTabuleiro(partida.getPecaPartida());
			System.out.println();
			System.out.println("Origem: ");
			PosicaoXadrez origem = UI.lerPosicaoXadrez(scan);
			System.out.println();
			System.out.println("Destino: ");
			PosicaoXadrez destino = UI.lerPosicaoXadrez(scan);
			PecaPartida capturaPeca = partida.executarMovimentoXadrez(origem, destino);
		}

	}
}
