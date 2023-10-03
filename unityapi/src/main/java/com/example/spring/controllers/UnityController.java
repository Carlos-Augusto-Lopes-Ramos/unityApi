package com.example.spring.controllers;

import com.example.spring.dtos.UnityRecordDto;
import com.example.spring.models.UnityModel;
import com.example.spring.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UnityController {

    @Autowired
    ProductRepository unityRepository;

    @PostMapping("/robbery")
    public ResponseEntity<UnityModel> saveRobbery(@RequestBody @Valid UnityRecordDto unityRecordDto) {
        var robberyModel = new UnityModel();
        BeanUtils.copyProperties(unityRecordDto, robberyModel);
        List<UnityModel> robberyList = unityRepository.findAll();
        for (UnityModel rob: robberyList) {
            if (rob.getName().equals(robberyModel.getName()) && rob.getIp().equals(robberyModel.getIp())) {
                return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(null);
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(unityRepository.save(robberyModel));
    }

    @GetMapping("/robbery")
    public ResponseEntity<List<UnityModel>> getAllEvidences() {
        List<UnityModel> robberyList = unityRepository.findAll();
        if(!robberyList.isEmpty()) {
            for(UnityModel robbery : robberyList) {
                robbery.add(linkTo(methodOn(UnityController.class).getOneEvidence(robbery.getIdRobbery())).withSelfRel());
            }
        }
        //return ResponseEntity.status(HttpStatus.OK).body(unityRepository.findAll());
        return ResponseEntity.status(HttpStatus.OK).body(robberyList);
    }

    @GetMapping("/robbery/{id}")
    public ResponseEntity<Object> getOneEvidence(@PathVariable(value="id") UUID id) {
        Optional<UnityModel> robbery = unityRepository.findById(id);
        if(robbery.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Robbery not done (yet).");
        }
        robbery.get().add(linkTo(methodOn(UnityController.class).getAllEvidences()).withRel("Robbery_List"));
        return ResponseEntity.status(HttpStatus.OK).body(robbery.get());
    }

    @DeleteMapping("/robbery/{id}")
    public ResponseEntity<Object> deleteEvidence(@PathVariable(value="id") UUID id) {
        Optional<UnityModel> robbery = unityRepository.findById(id);
        if(robbery.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Robbery not done (yet).");
        }
        unityRepository.delete(robbery.get());
        return ResponseEntity.status(HttpStatus.OK).body("evidence destroyed successfully.");
    }
}
