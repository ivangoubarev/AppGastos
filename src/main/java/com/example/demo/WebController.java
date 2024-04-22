package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private GastoService gastoService;
    @Autowired
    private PresupuestoService presupuestoService;

    @GetMapping("/registro")
    public String showRegistroForm(Model model) {
        model.addAttribute("gasto", new Gasto());
        model.addAttribute("presupuestos", presupuestoService.getAllPresupuestos());

        return "registro";
    }

    @PostMapping("/registro")
    public String registrarGasto(@ModelAttribute("gasto") Gasto gasto, @RequestParam("presupuesto") Long presupuestoId) {

        Presupuesto presupuesto = presupuestoService.getPresupuestoById(presupuestoId);
        gasto.setPresupuesto(presupuesto);
        gastoService.saveGasto(gasto);
        return "redirect:/registro";
    }

    @GetMapping("/registroPresupuesto")
    public String showRegistroPresupuestoForm(Model model) {
        model.addAttribute("presupuesto", new Presupuesto());
        return "registroPresupuesto";
    }

    @PostMapping("/registroPresupuesto")
    public String registrarPresupuesto(Presupuesto presupuesto) {
        presupuestoService.savePresupuesto(presupuesto);
        return "redirect:/registroPresupuesto";
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
