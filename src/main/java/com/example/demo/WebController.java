package com.example.demo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
        model.addAttribute("presupuestos", presupuestoService.getPresupuestosByUsuarioId(usuarioActivo.getId()));
        return "ingresarNomina";
    }

    @PostMapping("/ingresarNomina")
    public String IngresarNomina(Nomina nomina) {
        double totalFijo = 0;
        for(ComponenteNomina componenteNomina : nomina.getComponentes()) {
            System.out.println(componenteNomina.getValor());
            System.out.println(componenteNomina.getTipoComponenteNomina());
            System.out.println(componenteNomina.getPresupuesto().getNombre());
            System.out.println(componenteNomina.getTipoComponenteNomina() == TipoComponenteNomina.FIJO);
            if(componenteNomina.getValor() != 0 && componenteNomina.getTipoComponenteNomina() == TipoComponenteNomina.FIJO) {
                gastoService.saveGasto(new Gasto(nomina.getFecha(), componenteNomina.getValor(),
                        "Presupuesto mensual: " + componenteNomina.getPresupuesto().getNombre(), componenteNomina.getPresupuesto(),
                        usuarioActivo));
                totalFijo += componenteNomina.getValor();
            }
        }
        for(ComponenteNomina componenteNomina : nomina.getComponentes()) {
            if(componenteNomina.getValor() != 0 && componenteNomina.getTipoComponenteNomina() == TipoComponenteNomina.PORCENTAJE) {
                gastoService.saveGasto(new Gasto(nomina.getFecha(), (nomina.getTotalNomina() - totalFijo) * (componenteNomina.getValor() / 100),
                        "Presupuesto mensual: " + componenteNomina.getPresupuesto().getNombre(), componenteNomina.getPresupuesto(),
                        usuarioActivo));
            }
        }

        return "redirect:/listado";
    }

    @GetMapping("/listado")
    public String showListado(Model model) {
        model.addAttribute("gastos", gastoService.getGastosByUsuarioId(usuarioActivo.getId()));
        model.addAttribute("total", usuarioActivo.getBalance());
        return "listado";
    }

    @GetMapping("/listadoPresupuesto")
    public String showListadoPresupuesto(Model model) {
        model.addAttribute("presupuestos", presupuestoService.getPresupuestosByUsuarioId(usuarioActivo.getId()));
        model.addAttribute("total", usuarioActivo.getBalance());
        return "listadoPresupuesto";
    }

    @PostMapping("/recalcularBalances")
    public String recalcularBalances(HttpServletRequest request) {
        usuarioActivo.recalcularBalances();
        usuarioService.saveUsuario(usuarioActivo);
        return "redirect:" + request.getHeader("referer");
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
    public String registrarUsuario(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("confirmPassword") String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return "redirect:/crearUsuario?noCoinc";
        } else if(!(usuarioService.getByUsername(username) == null)) {
            return "redirect:/crearUsuario?usuarioExiste";
        } else {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            usuarioService.saveUsuario(new Usuario(username, "{bcrypt}" + passwordEncoder.encode(password)));
            return "redirect:/login";
        }
    }

    @PostMapping("/eliminarGasto")
    public String eliminarGasto(Long id) {
        gastoService.deleteGasto(id);
        return "redirect:/listado";
    }
}
