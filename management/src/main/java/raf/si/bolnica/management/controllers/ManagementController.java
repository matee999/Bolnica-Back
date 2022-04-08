package raf.si.bolnica.management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.si.bolnica.management.entities.Pregled;
import raf.si.bolnica.management.entities.ZakazaniPregled;
import raf.si.bolnica.management.interceptors.LoggedInUser;
import raf.si.bolnica.management.requests.CreateScheduledAppointmentRequestDTO;
import raf.si.bolnica.management.requests.UpdateAppointmentStatusDTO;
import raf.si.bolnica.management.requests.UpdateArrivalStatusDTO;
import raf.si.bolnica.management.service.ScheduledAppointmentService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ManagementController {

    @Autowired
    private LoggedInUser loggedInUser;

    @Autowired
    private ScheduledAppointmentService appointmentService;

    @PostMapping(value = "/set-appointment")
    public ResponseEntity setAppointment(@RequestBody CreateScheduledAppointmentRequestDTO requestDTO) {
        List<String> acceptedRoles = new ArrayList<>();
        acceptedRoles.add("ROLE_VISA_MED_SESTRA");
        acceptedRoles.add("ROLE_MED_SESTRA");
        if(requestDTO.getAppointmentEmployeeId() == 0 || requestDTO.getDateAndTimeOfAppointment() == null
                || requestDTO.getExaminationEmployeeId() == 0){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
       if (loggedInUser.getRoles().stream().anyMatch(acceptedRoles::contains)) {

           ZakazaniPregled appointmentToReturn = appointmentService.setAppointment(requestDTO);
            return ResponseEntity.ok(appointmentToReturn);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping(value = "/update-appointment-status")
    public ResponseEntity updateAppointmentStatus(@RequestBody UpdateAppointmentStatusDTO requestDTO) {
        List<String> acceptedRoles = new ArrayList<>();
        acceptedRoles.add("ROLE_DR_SPEC_ODELJENJA");
        acceptedRoles.add("ROLE_DR_SPEC");
        if(requestDTO.getAppointmentStatus() == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
            if(loggedInUser.getRoles().stream().anyMatch(acceptedRoles::contains)) {
                ZakazaniPregled appointmentToReturn = appointmentService.updateAppointment(requestDTO);
                return ResponseEntity.ok(appointmentToReturn);
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping(value = "/update-arrival-status")
    public ResponseEntity updateArrivalStatus(@RequestBody UpdateArrivalStatusDTO requestDTO) {
        List<String> acceptedRoles = new ArrayList<>();
        acceptedRoles.add("ROLE_VISA_MED_SESTRA");
        acceptedRoles.add("ROLE_MED_SESTRA");
        if(requestDTO.getArrivalStatus() == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if(loggedInUser.getRoles().stream().anyMatch(acceptedRoles::contains)) {
            ZakazaniPregled appointmentToReturn = appointmentService.updateArrival(requestDTO);
            return ResponseEntity.ok(appointmentToReturn);
        }
          return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
