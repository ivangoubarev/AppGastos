package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PresupuestoService {

    @Autowired
    private PresupuestoRepository presupuestoRepository;

    public List<Presupuesto> getPresupuestosByUsuarioId(Long id) {
        return presupuestoRepository.findByUsuarioId(id);
    }

    public List<Presupuesto> getAllPresupuestos() {
        return presupuestoRepository.findAll();
    }

    public Presupuesto savePresupuesto(Presupuesto presupuesto) {
        return presupuestoRepository.save(presupuesto);
    }

    public Presupuesto getPresupuestoById(Long id) {
        return presupuestoRepository.getReferenceById(id);
    }
}