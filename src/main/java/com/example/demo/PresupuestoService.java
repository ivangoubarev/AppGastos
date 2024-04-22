package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PresupuestoService {

    @Autowired
    private PresupuestoRepository presupuestoRepository;

    public List<Presupuesto> getAllPresupuestos() {
        return presupuestoRepository.findAll();
    }

    public Presupuesto savePresupuesto(Presupuesto presupuesto) {
        return presupuestoRepository.save(presupuesto);
    }
}