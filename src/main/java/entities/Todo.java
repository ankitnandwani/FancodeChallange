package entities;

import lombok.Getter;

@Getter
public class Todo {
    private int userId;
    private int id;
    private boolean completed;
}
