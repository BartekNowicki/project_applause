package com.example.demo.controller;

import com.example.demo.message.ResponseMessage;
import com.example.demo.service.CSVService;
import com.example.demo.utils.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin("http://localhost:8081")
@Controller
@RequestMapping("/api/csv")
public class CSVController {

    @Autowired
    CSVService csvFileService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {

            try {

                switch(file.getOriginalFilename()) {
                    case "bugs.csv":
                        csvFileService.saveBugs(file);
                        break;
                    case "devices.csv":
                        csvFileService.saveDevices(file);
                        break;
                    case "tester_device.csv":
                        csvFileService.saveTesterDevices(file);
                        break;
                    case "testers.csv":
                        csvFileService.saveTesters(file);
                        break;
                    case "things.csv":
                        csvFileService.saveThings(file);
                        break;
                    default: {}
                }

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }
}
