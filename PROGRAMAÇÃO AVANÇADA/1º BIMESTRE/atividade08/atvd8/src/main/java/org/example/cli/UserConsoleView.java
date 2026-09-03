package org.example.cli;

import org.example.entities.User;
import org.example.services.UserService;
import org.example.utils.ValidationUtils;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class UserConsoleView {
    private final UserService userService;

    public UserConsoleView(UserService userService) {
        this.userService = userService;
    }

    public void runMenu(Scanner scanner) {
        int option;
        do {
            printUserMenu();
            option = ConsoleInputUtils.readInt(scanner);

            try {
                switch (option) {
                    case 1 -> registerUser(scanner);
                    case 2 -> listUsers();
                    case 3 -> searchUserById(scanner);
                    case 4 -> updateUser(scanner);
                    case 5 -> deleteUser(scanner);
                    case 0 -> System.out.println("Voltando ao menu principal...");
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (option != 0);
    }

    private void printUserMenu() {
        System.out.println("\n--- GESTÃO DE USUÁRIOS ---");
        System.out.println("1 - Cadastrar Usuário");
        System.out.println("2 - Listar Todos os Usuários");
        System.out.println("3 - Buscar Usuário por ID");
        System.out.println("4 - Atualizar Usuário");
        System.out.println("5 - Remover Usuário");
        System.out.println("0 - Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
    }

    private void registerUser(Scanner scanner) {
        System.out.print("Nome: ");
        String name = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String password = scanner.nextLine();
        System.out.print("Perfil/Cargo (ex: ADMIN, CLIENTE): ");
        String role = scanner.nextLine();

        User user = userService.registerUser(name, email, password, role);
        System.out.println("Usuário cadastrado com sucesso!");
        System.out.println(user);
    }

    private void listUsers() {
        List<User> users = userService.listAllUsers();
        if (users.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            System.out.println("\n--- Lista de Usuários ---");
            for (User user : users) {
                System.out.println(user);
            }
        }
    }

    private void searchUserById(Scanner scanner) {
        System.out.print("Digite o UUID do usuário: ");
        String uuidStr = scanner.nextLine();
        UUID uuid = ValidationUtils.parseUuid(uuidStr);
        User user = userService.getUserById(uuid);
        System.out.println(user);
    }

    private void updateUser(Scanner scanner) {
        System.out.print("Digite o UUID do usuário a ser atualizado: ");
        String uuidStr = scanner.nextLine();
        UUID uuid = ValidationUtils.parseUuid(uuidStr);

        System.out.print("Novo nome (pressione Enter para manter o atual): ");
        String name = scanner.nextLine();
        System.out.print("Novo e-mail (pressione Enter para manter o atual): ");
        String email = scanner.nextLine();
        System.out.print("Nova senha (pressione Enter para manter a atual): ");
        String password = scanner.nextLine();
        System.out.print("Novo perfil (pressione Enter para manter o atual): ");
        String role = scanner.nextLine();

        User updated = userService.updateUser(uuid, name, email, password, role);
        System.out.println("Usuário atualizado com sucesso!");
        System.out.println(updated);
    }

    private void deleteUser(Scanner scanner) {
        System.out.print("Digite o UUID do usuário a ser removido: ");
        String uuidStr = scanner.nextLine();
        UUID uuid = ValidationUtils.parseUuid(uuidStr);
        userService.deleteUser(uuid);
        System.out.println("Usuário removido com sucesso.");
    }
}
