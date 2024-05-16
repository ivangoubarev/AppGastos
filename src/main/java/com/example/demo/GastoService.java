package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    public List<Gasto> getAllGastos() {
        return gastoRepository.findAll();
    }

    public List<Gasto> getGastosByUsuarioId(Long id) {
        return gastoRepository.findByUsuarioId(id);
    }

    public Gasto saveGasto(Gasto gasto) {
        gasto.getPresupuesto().actualizarBalance(gasto.getCantidad());
        return gastoRepository.save(gasto);
    }

    public void deleteGasto(Long id) {
        Gasto tmp = gastoRepository.getReferenceById(id);
        tmp.getPresupuesto().actualizarBalance(-tmp.getCantidad());
        gastoRepository.deleteById(id);
    }
}
