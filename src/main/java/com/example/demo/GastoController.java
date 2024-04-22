package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gastos")
public class GastoController {

    @Autowired
    private GastoService gastoService;

    @GetMapping
    public List<Gasto> getAllGastos() {
        return gastoService.getAllGastos();
    }

    @PostMapping
    public Gasto addGasto(@RequestBody Gasto gasto) {
        return gastoService.saveGasto(gasto);
    }
}
