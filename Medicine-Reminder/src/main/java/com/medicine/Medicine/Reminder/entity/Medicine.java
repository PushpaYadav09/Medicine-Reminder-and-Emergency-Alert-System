package com.medicine.Medicine.Reminder.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "medicines")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String dose;
    private String time;
    private LocalDateTime reminderTime;
    private boolean reminderAcknowledged = false; // Track if user acknowledged the reminder

    // Default Constructor
    public Medicine() {}

    // Constructor with fields
    public Medicine(String name, String dose, String time) {
        this.name = name;
        this.dose = dose;
        this.time = time;
        this.reminderTime = reminderTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;

    }
    public LocalDateTime getReminderTime() {
        return reminderTime;
    }
    public void setReminderTime(LocalDateTime reminderTime) {
        this.reminderTime = reminderTime;
    }
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Link Medicine to a User

    // Getters and Setters
    public boolean isReminderAcknowledged() {
        return reminderAcknowledged;
    }

    public void setReminderAcknowledged(boolean reminderAcknowledged) {
        this.reminderAcknowledged = reminderAcknowledged;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Optional: toString, equals, hashCode (for debugging purposes)
    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dose='" + dose + '\'' +
                ", time='" + time + '\'' +
                ", Reminder='" + reminderTime +'\''+
                '}';
    }
}
