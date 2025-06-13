package com.seubanco.banco;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class BancoApplication {

    private static Scanner scanner = new Scanner(System.in);
    private static BancoService bancoService = new BancoService();
    private static final String ARQUIVO = "contas.txt";

    public static void main(String[] args) {
        carregarContas();

        while (true) {
            System.out.println("\n=== Sistema Bancário ===");
            System.out.println("1 - Criar conta");
            System.out.println("2 - Listar contas");
            System.out.println("3 - Depositar");
            System.out.println("4 - Sacar");
            System.out.println("5 - Transferir");
            System.out.println("6 - Excluir conta");
            System.out.println("7 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> criarConta();
                case 2 -> listarContas();
                case 3 -> depositar();
                case 4 -> sacar();
                case 5 -> transferir();
                case 6 -> excluirConta();
                case 7 -> {
                    System.out.println("Saindo...");
                    salvarContas();
                    System.exit(0);
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void criarConta() {
        System.out.print("Nome do titular: ");
        String titular = scanner.nextLine();
        System.out.print("Número da conta: ");
        String numero = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());
        System.out.print("Sexo (M/F): ");
        String sexo = scanner.nextLine();

        bancoService.criarConta(titular, numero, cpf, idade, sexo);
        salvarContas();
        System.out.println("Conta criada com sucesso!");
    }

    private static void listarContas() {
        List<Conta> contas = bancoService.listarContas();
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
            return;
        }
        for (Conta conta : contas) {
            System.out.printf("Conta: %s | Titular: %s | CPF: %s | Idade: %d | Sexo: %s | Saldo: R$ %.2f%n",
                    conta.getNumeroConta(), conta.getTitular(), conta.getCpf(),
                    conta.getIdade(), conta.getSexo(), conta.getSaldo());
        }
    }

    private static void depositar() {
        System.out.println("⚠️  ATENÇÃO: Nunca faça transações enquanto está ao telefone com supostos atendentes!");
        System.out.print("Você está recebendo alguma ligação agora? (s/n): ");
        String emLigacao = scanner.nextLine().trim().toLowerCase();
        if (emLigacao.equals("s")) {
            System.out.println("Transação cancelada por segurança. Desligue a ligação suspeita e tente novamente.");
            return;
        }

        System.out.print("Número da conta: ");
        String numero = scanner.nextLine();

        int codigo = (int) (Math.random() * 9000) + 1000;
        System.out.println("CÓDIGO ENVIADO POR LIGAÇÃO (simulado): " + codigo);
        System.out.print("Digite o código de verificação: ");
        int verificado = Integer.parseInt(scanner.nextLine());
        if (verificado != codigo) {
            System.out.println("Código incorreto. Operação cancelada.");
            return;
        }

        System.out.print("Valor para depositar: ");
        double valor = Double.parseDouble(scanner.nextLine());

        if (bancoService.depositar(numero, valor)) {
            salvarContas();
            System.out.println("Depósito realizado com sucesso.");
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void sacar() {
        System.out.println("⚠️  AVISO DE SEGURANÇA:");
        System.out.println("NUNCA faça transações enquanto está em ligação com supostos atendentes de banco!");
        System.out.print("Você está recebendo alguma ligação agora? (s/n): ");
        String emLigacao = scanner.nextLine().trim().toLowerCase();
        if (emLigacao.equals("s")) {
            System.out.println("Transação cancelada por segurança. Tente novamente mais tarde.");
            return;
        }

        System.out.print("Número da conta: ");
        String numero = scanner.nextLine();

        int codigo = (int) (Math.random() * 9000) + 1000;
        System.out.println("CÓDIGO ENVIADO POR LIGAÇÃO (simulado): " + codigo);
        System.out.print("Digite o código de verificação: ");
        int verificado = Integer.parseInt(scanner.nextLine());
        if (verificado != codigo) {
            System.out.println("Código incorreto. Operação cancelada.");
            return;
        }

        System.out.print("Valor para sacar: ");
        double valor = Double.parseDouble(scanner.nextLine());

        if (bancoService.sacar(numero, valor)) {
            salvarContas();
            System.out.println("Saque realizado com sucesso.");
        } else {
            System.out.println("Erro: conta inexistente ou saldo insuficiente.");
        }
    }

    private static void transferir() {
        System.out.println("⚠️  ALERTA DE GOLPES:");
        System.out.println("Nunca realize transferências sob pressão ou enquanto está em ligação com terceiros.");
        System.out.print("Você está sendo instruído por alguém no telefone agora? (s/n): ");
        String emLigacao = scanner.nextLine().trim().toLowerCase();
        if (emLigacao.equals("s")) {
            System.out.println("Transferência bloqueada por segurança. Encerrando operação.");
            return;
        }

        System.out.print("Conta de origem: ");
        String origem = scanner.nextLine();
        System.out.print("Conta de destino: ");
        String destino = scanner.nextLine();

        int codigo = (int) (Math.random() * 9000) + 1000;
        System.out.println("CÓDIGO ENVIADO POR LIGAÇÃO (simulado): " + codigo);
        System.out.print("Digite o código de verificação: ");
        int verificado = Integer.parseInt(scanner.nextLine());
        if (verificado != codigo) {
            System.out.println("Código incorreto. Operação cancelada.");
            return;
        }

        System.out.print("Valor para transferir: ");
        double valor = Double.parseDouble(scanner.nextLine());

        if (bancoService.transferir(origem, destino, valor)) {
            salvarContas();
            System.out.println("Transferência realizada com sucesso.");
        } else {
            System.out.println("Erro: verifique contas e saldo.");
        }
    }

    private static void excluirConta() {
        System.out.print("Número da conta para excluir: ");
        String numero = scanner.nextLine();

        if (bancoService.removerConta(numero)) {
            salvarContas();
            System.out.println("Conta excluída com sucesso.");
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    private static void salvarContas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Conta conta : bancoService.listarContas()) {
                // Salvar formato: titular;numeroConta;cpf;idade;sexo;saldo
                writer.write(String.format("%s;%s;%s;%d;%s;%.2f",
                        conta.getTitular(),
                        conta.getNumeroConta(),
                        conta.getCpf(),
                        conta.getIdade(),
                        conta.getSexo(),
                        conta.getSaldo()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar contas: " + e.getMessage());
        }
    }

    private static void carregarContas() {
        System.out.println("Carregando contas do arquivo...");
        File arquivo = new File(ARQUIVO);
        if (!arquivo.exists()) {
            System.out.println("Arquivo de contas não encontrado, iniciando novo banco.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 6) {
                    String titular = partes[0];
                    String numero = partes[1];
                    String cpf = partes[2];
                    int idade = Integer.parseInt(partes[3]);
                    String sexo = partes[4];
                    double saldo = Double.parseDouble(partes[5].replace(",", "."));
                    bancoService.criarConta(titular, numero, cpf, idade, sexo);
                    bancoService.definirSaldo(numero, saldo);
                }
            }
            System.out.println("Contas carregadas com sucesso.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao carregar contas: " + e.getMessage());
        }
    }
}
