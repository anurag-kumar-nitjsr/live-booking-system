package com.ak.live.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ak.live.request.LiveRequest;
import com.ak.live.services.LiveService;

@RestController
@RequestMapping("/live")
public class LiveController {

	@Autowired
    private  LiveService liveService;

   

    @PostMapping("/add")
    public ResponseEntity<String> addLiveShow(@RequestBody LiveRequest liveRequest) {
        try {
            String result = liveService.addLive(liveRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
