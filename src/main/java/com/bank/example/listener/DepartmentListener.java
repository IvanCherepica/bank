package com.bank.example.listener;

import com.bank.example.model.Department;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class DepartmentListener {

    @PrePersist
    public void prePersist(Department department) {
        System.out.println();
        System.out.println("Перед сохранением Department с name = " + department.getName());
        System.out.println();
    }

    @PostPersist
    public void postPersist(Department department) {
        System.out.println();
        System.out.println("После сохранения Department с id = "+ department.getId() + " с name = " + department.getName());
        System.out.println();
    }

    @PreUpdate
    public void preUpdate(Department department) {
        System.out.println();
        System.out.println("Перед обновлением Department с id = "+ department.getId() + " с name = " + department.getName());
        System.out.println();
    }

    @PostUpdate
    public void postUpdate(Department department) {
        System.out.println();
        System.out.println("После обновления Department с id = "+ department.getId() + " с name = " + department.getName());
        System.out.println();
    }

    @PreRemove
    public void preRemove(Department department) {
        System.out.println();
        System.out.println("Перед удалением Department с id = "+ department.getId() + " с name = " + department.getName());
        System.out.println();
    }

    @PostRemove
    public void postRemove(Department department) {
        System.out.println();
        System.out.println("После удаления Department с id = "+ department.getId() + " с name = " + department.getName());
        System.out.println();
    }
}