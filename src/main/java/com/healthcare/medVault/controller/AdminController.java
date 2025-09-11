package com.healthcare.medVault.controller;

import com.healthcare.medVault.entity.Doctor;
import com.healthcare.medVault.entity.Patient;
import com.healthcare.medVault.repository.DoctorRepository;
import com.healthcare.medVault.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/doctors")
    public List<Doctor> getDoctors() {
        List<Doctor> doct = doctorRepository.findAll();
        return doct;
    }

    @GetMapping("/patients")
    public List<Patient> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients;
    }

    @DeleteMapping("/doctors/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorRepository.deleteById(id);
    }

    @DeleteMapping("/patients/{id}")
    public void deletePatient(@PathVariable Long id) {
        patientRepository.deleteById(id);
    }
}
