package com.examen.chido.controller;

import com.examen.chido.controller.dto.TicketActualizarDTO;
import com.examen.chido.controller.dto.TicketDTO;
import com.examen.chido.controller.dto.TicketPrioridadDTO;
import com.examen.chido.controller.dto.UsuarioDTO;
import com.examen.chido.models.Ticket;
import com.examen.chido.models.Usuario;
import com.examen.chido.service.ServicioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@CrossOrigin({"*"})
public class ControladorController {

    public ServicioService servicioService;

    public ControladorController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @PostMapping("ticket/")
    public Ticket crearTicket(@RequestBody TicketDTO payload){
        return servicioService.crearTicket(payload.usuarioId, payload.descripcion, payload.prioridad);
    }

    @PostMapping("ticket/estado/{id}")
    public Ticket actualizarEstadoTicket(@RequestParam TicketActualizarDTO payload){
        return servicioService.actualizarEstadoTicket(payload.id);
    }

    @PostMapping("ticket/prioridad/{id}")
    public Ticket reasignarPrioridad(@RequestParam TicketPrioridadDTO payload){
        return servicioService.reasignarPrioridad(payload.id, payload.nuevaPrioridad);
    }

    @PostMapping("usuario/{id}")
    public Usuario desactivarUsuario(@RequestParam UsuarioDTO payload){
        return servicioService.desactivarUsuario(payload.id);
    }

    @GetMapping("usuario/lista/{id}")
    public List<Ticket> obtenerTicketsUsuario(@RequestParam UsuarioDTO payload){
        return servicioService.obtenerTicketsUsuario(payload.id);
    }
}
