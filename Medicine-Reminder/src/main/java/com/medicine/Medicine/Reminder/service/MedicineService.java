package com.medicine.Medicine.Reminder.service;
import com.medicine.Medicine.Reminder.entity.Medicine;
import com.medicine.Medicine.Reminder.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
@Service
public class MedicineService {

    private static final Logger logger = Logger.getLogger(MedicineService.class.getName());

    @Autowired
    private MedicineRepository medicineRepository;

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    public Medicine addMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public Medicine updateMedicine(Long id, Medicine updatedMedicine) {
        return medicineRepository.findById(id)
                .map(medicine -> {
                    medicine.setName(updatedMedicine.getName());
                    medicine.setDose(updatedMedicine.getDose());
                    medicine.setTime(updatedMedicine.getTime());
                    medicine.setReminderTime(updatedMedicine.getReminderTime());
                    medicine.setReminderAcknowledged(updatedMedicine.isReminderAcknowledged());
                    return medicineRepository.save(medicine);
                }).orElse(null);
    }

    public boolean deleteMedicine(Long id) {
        if (medicineRepository.existsById(id)) {
            medicineRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Scheduled task: Runs every minute to check for medicine reminders
    @Scheduled(fixedRate = 60000) // Runs every 60 seconds
    public void sendMedicineReminders() {
        LocalDateTime now = LocalDateTime.now();
        List<Medicine> medicines = medicineRepository.findByReminderTimeBefore(now);

        for (Medicine medicine : medicines) {
            if (!medicine.isReminderAcknowledged()) {
                logger.info("🔔 Reminder: Take your medicine - " + medicine.getName() +
                        " at " + medicine.getReminderTime());

                // Check after 10 minutes if acknowledgment is missing
                checkForEmergencyAlert(medicine);
            }
        }
    }

    // Check for emergency alert if the reminder is ignored
    private void checkForEmergencyAlert(Medicine medicine) {
        LocalDateTime alertTime = medicine.getReminderTime().plusMinutes(10);
        if (LocalDateTime.now().isAfter(alertTime) && !medicine.isReminderAcknowledged()) {
            if (medicine.getUser() != null && medicine.getUser().getEmergencyContact() != null) {
                String emergencyContact = medicine.getUser().getEmergencyContact();
                logger.warning("🚨 ALERT: " + medicine.getUser().getName() +
                        " has NOT taken their medicine: " + medicine.getName() +
                        "! Contacting emergency: " + emergencyContact);
            } else {
                logger.warning("⚠️ No emergency contact found for " + medicine.getName());
            }
        }
    }
    public Medicine getMedicineById(Long id) {
        return medicineRepository.findById(id).orElse(null);
    }



    // Mark a reminder as acknowledged
    public boolean acknowledgeReminder(Long medicineId) {
        return medicineRepository.findById(medicineId).map(medicine -> {
            medicine.setReminderAcknowledged(true);
            medicineRepository.save(medicine);
            return true;
        }).orElse(false);
    }
}