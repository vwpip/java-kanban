package ru_practicum.task_tracker.manager;

import ru_practicum.task_tracker.task.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private int nextId;

    public List<Task> getTasks(){
        return new ArrayList<>(tasks.values());
    }

    public List<Epic> getEpics(){
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> getSubtasks(){
        return new ArrayList<>(subtasks.values());
    }

    public  List<Subtask> getSubtasksByEpic(Integer epicId) {
        Epic epic = epics.get(epicId);
        return epic.getSubtaskList();
    }
    public  void  deleteAllTask() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            updateEpicStatus(epic);
        }
    }
    public Task createTask(Task task) {
        task.setId(getNextId());
        tasks.put(task.getId(), task);
        return task;
    }
    public Epic createEpic(Epic epic) {
        epic.setId(getNextId());
        epics.put(epic.getId(), epic);
        return epic;
    }
    public Subtask createSubtask(Subtask subtask) {
        subtask.setId(getNextId());
        Epic epic = epics.get(subtask.getEpicId());
        epic.addSubtasks(subtask);
        subtasks.put(subtask.getId(),subtask);
        updateEpicStatus(epic);
        return subtask;
    }
    public Task updateTask(Task task) {
        if (task.getId() == null || !tasks.containsKey(task.getId())) {
            return null;
        }
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic updateEpic(Epic epic) {
        if (epic.getId() == null || !epics.containsKey(epic.getId())) {
            return null;
        }
        epic.setSubtaskList(epics.get(epic.getId()).getSubtaskList());
        updateEpicStatus(epic);
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Subtask updateSubtask(Subtask subtask) {
        if (subtask.getId() == null || !subtasks.containsKey(subtask.getId())) {
            return null;
        }
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getEpicId());
        ArrayList<Subtask> subtasksUp = epic.getSubtaskList();
        for (Subtask previousSubtask : subtasksUp) {
            if (previousSubtask.getId().equals(subtask.getId())) {
                int index = subtasksUp.indexOf(previousSubtask);
                subtasksUp.set(index, subtask);
            }
        }
        epic.setSubtaskList(subtasksUp);
        updateEpicStatus(epic);
        return subtask;
    }
    public boolean deleteTask(int taskId) {
        return tasks.remove(taskId) != null;
    }
    public Task getTaskById (Integer taskId) {
        return tasks.get(taskId);
    }

    public void deleteTaskById(Integer taskId) {
        tasks.remove(taskId);
    }

    public void deleteEpicById(Integer epicId) {
        Epic epic = epics.get(epicId);
        for (Subtask subtask : epic.getSubtaskList()) {
            subtasks.remove(subtask.getId());
        }
        epics.remove(epicId);
    }

    public void deleteSubtaskById(Integer subtaskId) {
        Subtask subtask = subtasks.get(subtaskId);
        Integer epicId = subtask.getEpicId();
        subtasks.remove(subtaskId);
        Epic epic = epics.get(epicId);
        ArrayList<Subtask> subtasksList = epic.getSubtaskList();
        subtasksList.remove(subtask);
        epic.setSubtaskList(subtasksList);
        updateEpicStatus(epic);
    }
    public Epic getEpicById (Integer epicId) {
        return epics.get(epicId);
    }

    public Subtask getSubtaskById (Integer subtaskId) {
        return subtasks.get(subtaskId);
    }

    public void updateEpicStatus(Epic epic) {
        List<Subtask> epicSubtasksList = epic.getSubtaskList();
        boolean isAllNew = true;
        boolean isAllDone = true;

        if (epicSubtasksList.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        for (Subtask subtask : epicSubtasksList) {
            if (subtask.getStatus() == Status.NEW) {
                isAllDone = false;
            }
            if (subtask.getStatus() == Status.DONE) {
                isAllNew = false;
            }
        }

        if (isAllNew) {
            epic.setStatus(Status.NEW);
        } else if (isAllDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }

    }
    private int getNextId() {
        return nextId++;
    }
}
