package ru_practicum.task_tracker;

import ru_practicum.task_tracker.manager.TaskManager;
import ru_practicum.task_tracker.task.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        testTasks();
    }

    private static void testTasks() {
        TaskManager taskManager = new TaskManager();

        System.out.println("Тест 1: Пустой список");
        List<Task> tasks = taskManager.getTasks();
        List<Epic> epics = taskManager.getEpics();
        List<Subtask> subtasks = taskManager.getSubtasks();
        System.out.println("Проверка на пустые таски: " + tasks.isEmpty());
        System.out.println();
        System.out.println("Проверка на пустые эпики: " + epics.isEmpty());
        System.out.println();
        System.out.println("Проверка на пустые сабтаски: " + subtasks.isEmpty());
        System.out.println();

        System.out.println("Тест 2: Создание таск");
        Task task1 = new Task("Имя таска 1", "Описание таска 1");
        Task task1Created = taskManager.createTask(task1);
        Task task2 = new Task("Имя таска 2", "Описание таска 2");
        Task task2Created = taskManager.createTask(task2);
        System.out.println("Созданный таск 1 содержит айди : " + (task1Created.getId() != null));
        System.out.println("Созданный таск 2 содержит айди : " + (task2Created.getId() != null));
        System.out.println("Список тасков содержит таски: " + (taskManager.getTasks()));
        System.out.println();

        System.out.println("Тест 3: Создание эпика с 2 подзадачами");
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Epic epic1Created = taskManager.createEpic(epic1);
        Subtask subtask1_1 = new Subtask("Подзадача 1-1", "Описание подзадачи 1-1",
                epic1Created.getId());
        Subtask subtask1_1Created = taskManager.createSubtask(subtask1_1);
        Subtask subtask1_2 = new Subtask("Подзадача 1-2", "Описание подзадачи 1-2",
                epic1Created.getId());
        Subtask subtask1_2Created = taskManager.createSubtask(subtask1_2);
        System.out.println("Созданный эпик содержит айди: " + (epic1Created.getId() != null));
        System.out.println("Созданные подзадачи содержат айди: " + ((subtask1_1Created.getId() != null)
                && (subtask1_2Created.getId() != null)));
        System.out.println("Список эпиков содержит добавленный эпик: " + taskManager.getEpics());
        System.out.println("Список подзадач содержит добавленные подзадачи: " + taskManager.getSubtasks());
        System.out.println();

        System.out.println("Тест 4: Создание эпика с одной подзадачей");
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        Epic epic2Created = taskManager.createEpic(epic2);
        Subtask subtask2_1 = new Subtask("Подзадача 2-1", "Описание подзадачи 2-1",
                epic2Created.getId());
        Subtask subtask2_1Created = taskManager.createSubtask(subtask2_1);
        System.out.println("Созданный эпик содержит айди: " + (epic2Created.getId() != null));
        System.out.println("Созданная подзача содержит айди: " + (subtask2_1Created.getId() != null));
        System.out.println("Список эпиков содержит добавленный эпик: " + taskManager.getEpics());
        System.out.println("Список подзадач содержит добавленную подзадачу: " + taskManager.getSubtasks());
        System.out.println();

        System.out.println("Тест 5: Обновление задачи");
        task1Created.setName("Обновленное имя задачи 1");
        task1Created.setDescription("Обновленное описание задачи 1");
        task1Created.setStatus(Status.IN_PROGRESS);
        System.out.println("Задача 1 обновлена: " + taskManager.updateTask(task1Created));
        System.out.println();

        System.out.println("Тест 6: Обновление эпика");
        epic1Created.setName("Обновленное имя эпика 1");
        epic1Created.setDescription("Обновленное описание эпика 1");
        System.out.println("Обновленный эпик: " + taskManager.updateEpic(epic1Created));
        System.out.println();

        System.out.println("Тест 7: Обновление подзадачи");
        subtask1_1Created.setStatus(Status.DONE);
        System.out.println("Подзадача 1-1 обновлена: " + taskManager.updateSubtask(subtask1_1Created));
        System.out.println("В списке подзадач эпика 1 подзадача 1-1 обновлена: ");
        System.out.println(taskManager.getSubtasksByEpic(epic1Created.getId()));
        System.out.println("Статус эпика 1: " + epic1Created.getStatus());
        System.out.println();

        System.out.println("Тест 8: Удаление таска по айди");
        taskManager.deleteTaskById(task1Created.getId());
        System.out.println("Таск 1 удален из списка задач: " + taskManager.getTasks());
        System.out.println();

        System.out.println("Тест 9: Удаление подзадачи по айди");
        taskManager.deleteSubtaskById(subtask1_1Created.getId());
        System.out.println("Подзадача 1-1 удалена из списка подзадач: " + taskManager.getSubtasks());
        System.out.print("Подзадача удалена из списка задач эпика 1: ");
        System.out.println(taskManager.getSubtasksByEpic(subtask1_1Created.getEpicId()));
        System.out.println("Статус эпика 1: " + epic1Created.getStatus());
        System.out.println();

        System.out.println("Тест 10: Удаление эпика по айди");
        taskManager.deleteEpicById(epic1Created.getId());
        System.out.println("Эпик 1 удалён из списка эпиков: " + taskManager.getEpics());

        System.out.println("Тест 11: Удаление всех тасков");
        taskManager.deleteAllTask();
        System.out.println("Список тасков: " + (taskManager.getTasks()));

        System.out.println("Тест 12: Удаление всех подзадач");
        taskManager.deleteAllSubtasks();
        System.out.println("Список подзадач: " + (taskManager.getSubtasks()));

        System.out.println("Тест 13: Удаление всех эпиков");
        taskManager.deleteAllEpics();
        System.out.println("Список эпиков: " + (taskManager.getEpics()));

    }
}
