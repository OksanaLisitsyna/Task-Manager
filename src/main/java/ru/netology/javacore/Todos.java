package ru.netology.javacore;

import java.util.TreeSet;

public class Todos {
    private TreeSet<String> tasks;

    Todos() {
        tasks = new TreeSet<>();
    }

    public boolean addTask(String task) {
        if (tasks.contains(task)) {
            return false;
        } else {
            tasks.add(task);
            return true;
        }
    }

    public boolean removeTask(String task) {
        if (!tasks.contains(task)) {
            return false;
        } else {
            tasks.remove(task);
            return true;
        }
    }

    public String getAllTasks() {
        return tasks.toString();
    }
}
