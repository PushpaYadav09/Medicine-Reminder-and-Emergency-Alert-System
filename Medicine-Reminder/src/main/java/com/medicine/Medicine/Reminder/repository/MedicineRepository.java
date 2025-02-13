package com.medicine.Medicine.Reminder.repository;

import com.medicine.Medicine.Reminder.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findByReminderTimeBefore(LocalDateTime now);
}
