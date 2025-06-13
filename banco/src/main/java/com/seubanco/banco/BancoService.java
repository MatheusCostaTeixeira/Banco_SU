package com.seubanco.banco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BancoService {
    private Map<String, Conta> contas = new HashMap<>();

    public Conta criarConta(String titular, String numeroConta, String cpf, int idade, String sexo) {
        Conta conta = new Conta(titular, numeroConta, cpf, idade, sexo);
        contas.put(numeroConta, conta);
        return conta;
    }

    public List<Conta> listarContas() {
        return new ArrayList<>(contas.values());
    }

    public boolean depositar(String numeroConta, double valor) {
        Conta conta = contas.get(numeroConta);
        if (conta != null) {
            conta.depositar(valor);
            return true;
        }
        return false;
    }

    public boolean sacar(String numeroConta, double valor) {
        Conta conta = contas.get(numeroConta);
        if (conta != null) {
            return conta.sacar(valor);
        }
        return false;
    }

    public boolean transferir(String origem, String destino, double valor) {
        Conta contaOrigem = contas.get(origem);
        Conta contaDestino = contas.get(destino);
        if (contaOrigem != null && contaDestino != null && contaOrigem.sacar(valor)) {
            contaDestino.depositar(valor);
            return true;
        }
        return false;
    }

    public boolean removerConta(String numeroConta) {
        return contas.remove(numeroConta) != null;
    }

    public void definirSaldo(String numeroConta, double saldo) {
        Conta conta = contas.get(numeroConta);
        if (conta != null) {
            conta.setSaldo(saldo);
        }
    }
}
