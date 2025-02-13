package com.medicine.Medicine.Reminder.controller;



import com.medicine.Medicine.Reminder.entity.Medicine;
import com.medicine.Medicine.Reminder.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/medicines")
@CrossOrigin("*") // Allows frontend calls
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    // Fetch all medicines
    @GetMapping
    public ResponseEntity<List<Medicine>> getAllMedicines() {
        List<Medicine> medicines = medicineService.getAllMedicines();
        return new ResponseEntity<>(medicines, HttpStatus.OK);
    }

    // Add new medicine
    @PostMapping
    public ResponseEntity<Medicine> addMedicine(@RequestBody Medicine medicine) {
        Medicine savedMedicine = medicineService.addMedicine(medicine);
        return new ResponseEntity<>(savedMedicine, HttpStatus.CREATED);
    }

    // Update medicine
    @PutMapping("/{id}")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable Long id, @RequestBody Medicine updatedMedicine) {
        Medicine medicine = medicineService.updateMedicine(id, updatedMedicine);
        if (medicine != null) {
            return new ResponseEntity<>(medicine, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Acknowledge medicine reminder
    @PutMapping("/{id}/acknowledge")
    public ResponseEntity<String> acknowledgeReminder(@PathVariable Long id) {
        boolean acknowledged = medicineService.acknowledgeReminder(id);
        if (acknowledged) {
            return ResponseEntity.ok("✅ Medicine reminder acknowledged!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Medicine not found!");
    }

    // Delete medicine
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedicine(@PathVariable Long id) {
        boolean deleted = medicineService.deleteMedicine(id);
        if (deleted) {
            return ResponseEntity.ok("🗑️ Medicine deleted successfully!");
        }
        return new ResponseEntity<>("❌ Medicine not found!", HttpStatus.NOT_FOUND);
    }
}
