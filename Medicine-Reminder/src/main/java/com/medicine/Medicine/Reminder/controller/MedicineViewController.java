package com.medicine.Medicine.Reminder.controller;

import com.medicine.Medicine.Reminder.entity.Medicine;
import com.medicine.Medicine.Reminder.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/medicines-view")
public class MedicineViewController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping
    public String getAllMedicines(Model model) {
        List<Medicine> medicines = medicineService.getAllMedicines();
        model.addAttribute("medicines", medicines);
        return "medicine-list";  // Name of Thymeleaf template
    }

    @PostMapping
    public String addMedicine(@ModelAttribute Medicine medicine) {
        medicineService.addMedicine(medicine);
        return "redirect:/medicines-view"; // Redirect to updated list
    }

    @PostMapping("/{id}/update")
    public String updateMedicine(@PathVariable Long id, @ModelAttribute Medicine updatedMedicine) {
        medicineService.updateMedicine(id, updatedMedicine);
        return "redirect:/medicines-view";
    }

    @PostMapping("/{id}/delete")
    public String deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return "redirect:/medicines-view";
    }
}

