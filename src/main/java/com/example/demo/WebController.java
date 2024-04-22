package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private GastoService gastoService;

    @GetMapping("/registro")
    public String showRegistroForm(Model model) {
        model.addAttribute("gasto", new Gasto());

        return "registro";
    }

    @PostMapping("/registro")
    public String registrarGasto(Gasto gasto) {
        gastoService.saveGasto(gasto);
        return "redirect:/registro";
    }

    @GetMapping("/listado")
    public String showListado(Model model) {
        model.addAttribute("gastos", gastoService.getAllGastos());
        List<Gasto> gastos = gastoService.getAllGastos();
        double total = gastos.stream().mapToDouble(Gasto::getCantidad).sum();
        model.addAttribute("gastos", gastos);
        model.addAttribute("total", total);
        return "listado";
    }
}
