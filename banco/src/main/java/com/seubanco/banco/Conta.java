package com.seubanco.banco;

public class Conta {
    private String titular;
    private String numeroConta;
    private String cpf;
    private int idade;
    private String sexo;
    private double saldo;

    public Conta(String titular, String numeroConta, String cpf, int idade, String sexo) {
        this.titular = titular;
        this.numeroConta = numeroConta;
        this.cpf = cpf;
        this.idade = idade;
        this.sexo = sexo;
        this.saldo = 0.0;
    }

    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }

    public String getNumeroConta() { return numeroConta; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public void depositar(double valor) { saldo += valor; }

    public boolean sacar(double valor) {
        if (saldo >= valor) {
            saldo -= valor;
            return true;
        }
        return false;
    }
}
