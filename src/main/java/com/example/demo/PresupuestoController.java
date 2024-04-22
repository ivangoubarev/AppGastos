package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/presupuestos")
public class PresupuestoController {

    @Autowired
    private PresupuestoService presupuestoService;

    @GetMapping
    public List<Presupuesto> getAllPresupuestos() {
        return presupuestoService.getAllPresupuestos();
    }

    @PostMapping
    public Presupuesto addPresupuesto(@RequestBody Presupuesto presupuesto) {
        return presupuestoService.savePresupuesto(presupuesto);
    }
}