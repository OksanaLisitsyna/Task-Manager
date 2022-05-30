package ru.netology.javacore;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TodoServer {
    private Todos todos;
    private int port;

    public TodoServer(int port, Todos todos) {
        this.port = port;
        this.todos = todos;

    }

    public void start() throws IOException {
        System.out.println("Starting server at " + port + "...");
        try (ServerSocket serverSocket = new ServerSocket(8989);) { // стартуем сервер один(!) раз
            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ) {
                    // обработка одного подключения
                    String input = in.readLine();
                    JsonObject jsonObject = new Gson().fromJson(input, JsonObject.class).getAsJsonObject();

                    String operation = jsonObject.get("type").getAsString();
                    String task = jsonObject.get("task").getAsString();
                    String result = applyOperationToTask(operation, task);

                    out.println(result + ": " + todos.getAllTasks());
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }

    public String applyOperationToTask(String operation, String task) {
        String result;
        if (operation.equals("ADD")) {
            if (todos.addTask(task)) {
                result = "Задача \"" + task + "\" успешно добавлена в список";
            } else {
                result = "Задача \"" + task + "\" была добавлена в список ранее";
            }
        } else if (operation.equals("REMOVE")) {
            if (todos.removeTask(task)) {
                result = "Задача \"" + task + "\" успешно удалена из списка";
            } else {
                result = "Задачи \"" + task + "\" нет в списке";
            }
        } else {
            result = "unknown command";
        }
        return result;
    }
}
