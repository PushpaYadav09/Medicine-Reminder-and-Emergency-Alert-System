package com.medicine.Medicine.Reminder.repository;



import com.medicine.Medicine.Reminder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
