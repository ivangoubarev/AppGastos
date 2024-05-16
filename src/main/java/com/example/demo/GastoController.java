package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gastos")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping
    public List<Gasto> getAllGastos() {
        return gastoService.getAllGastos();
    }

    @PostMapping
    public Gasto addGasto(@RequestBody Gasto gasto) {
        return gastoService.saveGasto(gasto);
    }
}
