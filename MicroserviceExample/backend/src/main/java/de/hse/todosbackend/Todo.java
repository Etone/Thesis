package de.hse.todosbackend;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name="todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String text;
    private boolean isActive;
    private LocalDateTime finishUntil;


    protected Todo(){}

    public Todo(String text, boolean isActive, LocalDateTime finishUntil) {
        this.text = text;
        this.isActive = isActive;
        this.finishUntil = finishUntil;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public void setFinishUntil(LocalDateTime finishUntil) {
        this.finishUntil = finishUntil;
    }

    public int getId() {

        return id;
    }

    public LocalDateTime getFinishUntil() {
        return finishUntil;
    }
}
