package org.SpringBoot_GitHub.GerenciamentoErros;

public class RecursosNaoEncontradosException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RecursosNaoEncontradosException(String message) {
		super(message);
	}
}