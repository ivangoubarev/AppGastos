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

    public Gasto saveGasto(Gasto gasto) {
        return gastoRepository.save(gasto);
    }
}
