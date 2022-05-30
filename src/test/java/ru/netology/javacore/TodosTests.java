package ru.netology.javacore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TodosTests {
    private static Todos todos;

    @BeforeAll
    public static void initTodos() {
        todos = new Todos();
    }

    public static Stream<Arguments> methodSourceForAdd() {
        return Stream.of(
                Arguments.of("task1", true), //новые задачи должны добавляться
                Arguments.of("task2", true),
                Arguments.of("task1", false) //повторно задачу добавить нельзя
        );
    }

    public static Stream<Arguments> methodSourceForRemove() {
        return Stream.of(
                Arguments.of("task3", true),
                Arguments.of("task4", false) //нельзя удалить задачу, которой нет в списке
        );
    }

    @ParameterizedTest
    @MethodSource("methodSourceForAdd")
    public void testAddTask(String task, boolean expectedResult) {
        boolean actualResult = todos.addTask(task);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @MethodSource("methodSourceForRemove")
    public void testRemoveTask(String task, boolean expectedResult) {
        todos.addTask("task3");
        boolean actualResult = todos.removeTask(task);
        Assertions.assertEquals(expectedResult, actualResult);
    }


    //проверим, что задачи выводятся в отсортированном лексикографическом порядке.
    @Test
    void testGetAllTasks() {
        Todos todosAll = new Todos();
        todosAll.addTask("B");
        todosAll.addTask("A");

        String expectedResult = "[A, B]";
        String actualResult = todosAll.getAllTasks();
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
