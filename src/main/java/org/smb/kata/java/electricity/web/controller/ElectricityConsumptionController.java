package org.smb.kata.java.electricity.web.controller;

import org.smb.kata.java.electricity.model.dto.ElectricityConsumptionView;
import org.smb.kata.java.electricity.service.ElectricityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/electricity/consumption")
public class ElectricityConsumptionController {

    @Autowired
    private ElectricityService service;

    @GetMapping({"","/"})
    public Page<ElectricityConsumptionView> list (
            @RequestParam(required = false) String meterId,
            @PageableDefault(size = 10, sort = {"timestamp", "meterId"},
                    direction = Sort.Direction.DESC) Pageable pagination) {

            // TODO: deal with the validations
            return service.list(meterId, pagination);
    }

}


