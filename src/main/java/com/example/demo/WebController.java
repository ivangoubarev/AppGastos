package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private GastoService gastoService;
    @Autowired
    private PresupuestoService presupuestoService;
    @Autowired
    private UsuarioService usuarioService;

    private Usuario usuarioActivo;

    @ModelAttribute
    public void setUserId(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        usuarioActivo = usuarioService.getByUsername(authentication.getName());
        model.addAttribute("usuarioActivo", usuarioActivo);
    }

    @GetMapping("/")
    public String redirectToListado() {
        return "redirect:/listado";
    }

    @GetMapping("/registro")
    public String showRegistroForm(Model model) {
        model.addAttribute("gasto", new Gasto());
        model.addAttribute("presupuestos", presupuestoService.getPresupuestosByUsuarioId(usuarioActivo.getId()));

        return "registro";
    }

    @PostMapping("/registro")
    public String registrarGasto(@ModelAttribute("gasto") Gasto gasto, @RequestParam("presupuesto") Long presupuestoId) {

        Presupuesto presupuesto = presupuestoService.getPresupuestoById(presupuestoId);
        gasto.setPresupuesto(presupuesto);
        gasto.setUsuario(usuarioActivo);
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
        presupuesto.setUsuario(usuarioActivo);
        presupuestoService.savePresupuesto(presupuesto);
        return "redirect:/registroPresupuesto";
    }

    @GetMapping("/ingresarNomina")
    public String showIngresarNominaForm(Model model) {
        model.addAttribute("ingresarNomina", new Nomina());
        return "ingresarNomina";
    }

    @PostMapping("/ingresarNomina")
    public String IngresarNomina(Nomina nomina) {
        gastoService.saveGasto(new Gasto(nomina.getFecha(), nomina.getComida(), "Presupuesto comida fecha " +
                nomina.getFecha().toString(), presupuestoService.getPresupuestoById(1L), usuarioActivo));
        gastoService.saveGasto(new Gasto(nomina.getFecha(), nomina.getAlquiler(), "Presupuesto alquiler fecha " +
                nomina.getFecha().toString(), presupuestoService.getPresupuestoById(2L), usuarioActivo));
        gastoService.saveGasto(new Gasto(nomina.getFecha(), nomina.getOcio(), "Presupuesto ocio fecha " +
                nomina.getFecha().toString(), presupuestoService.getPresupuestoById(3L), usuarioActivo));
        gastoService.saveGasto(new Gasto(nomina.getFecha(), nomina.getOtrosGastos(), "Presupuesto otros gastos fecha " +
                nomina.getFecha().toString(), presupuestoService.getPresupuestoById(4L), usuarioActivo));
        gastoService.saveGasto(new Gasto(nomina.getFecha(), nomina.getCoche(), "Presupuesto coche fecha " +
                nomina.getFecha().toString(), presupuestoService.getPresupuestoById(5L), usuarioActivo));
        gastoService.saveGasto(new Gasto(nomina.getFecha(), nomina.getAhorro(), "Presupuesto ahorro fecha " +
                nomina.getFecha().toString(), presupuestoService.getPresupuestoById(6L), usuarioActivo));
        return "redirect:/ingresarNomina";
    }

    @GetMapping("/listado")
    public String showListado(Model model) {
        model.addAttribute("gastos", gastoService.getGastosByUsuarioId(usuarioActivo.getId()));
        List<Gasto> gastos = gastoService.getGastosByUsuarioId(usuarioActivo.getId());
        double total = gastos.stream().mapToDouble(Gasto::getCantidad).sum();
        model.addAttribute("gastos", gastos);
        model.addAttribute("total", total);
        return "listado";
    }

    @GetMapping("/listadoPresupuesto")
    public String showListadoPresupuesto(Model model) {
        model.addAttribute("presupuestos", presupuestoService.getPresupuestosByUsuarioId(usuarioActivo.getId()));
        List<Gasto> gastos = gastoService.getGastosByUsuarioId(usuarioActivo.getId());
        double total = gastos.stream().mapToDouble(Gasto::getCantidad).sum();
        model.addAttribute("total", total);
        return "listadoPresupuesto";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/logout")
    String logout() {
        return "logout";
    }

    @GetMapping("/crearUsuario")
    public String showCrearUsuarioForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "crearUsuario";
    }

    @PostMapping("/crearUsuario")
    public String crearUsuario(Usuario usuario) {
        usuarioService.saveUsuario(usuario);
        return "redirect:/login";
    }
}
