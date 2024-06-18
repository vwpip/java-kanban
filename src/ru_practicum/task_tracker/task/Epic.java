package ru_practicum.task_tracker.task;
import java.util.ArrayList;
public class Epic extends Task {
    private ArrayList<Subtask> subtaskList = new ArrayList<>();
    public Epic(String name, String description) {
        super(name, description);
    }
    public ArrayList<Subtask> getSubtaskList() {
        return subtaskList;
    }
    public void clearSubtasksInEpic() {
        subtaskList.clear();
    }
    public void setSubtaskList(ArrayList<Subtask> subtaskList) {
        this.subtaskList = subtaskList;
    }

    public void addSubtasks(Subtask subtask) {
        subtaskList.add(subtask);
    }

    public void clear(){
        subtaskList.clear();
    }
    @Override
    public String toString() {
        return "Epic{" +
                "name= " + getName() + '\'' +
                ", description = " + getDescription() + '\'' +
                ", id=" + getId() +
                ", subtaskList.size = " + subtaskList.size() +
                ", status = " + getStatus() +
                '}';
    }
}
