package com.examen.chido.service;

import com.examen.chido.models.Ticket;
import com.examen.chido.models.Usuario;
import com.examen.chido.repository.TicketRepository;
import com.examen.chido.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {

    public TicketRepository ticketRepository;
    public UsuarioRepository usuarioRepository;

    public ServicioService(TicketRepository ticketRepository, UsuarioRepository usuarioRepository) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Ticket crearTicket(Long usuarioId, String descripcion, String prioridad){
        Usuario usuario = usuarioRepository.findById(usuarioId).get();

        Ticket ticket = new Ticket();
        ticket.setDescripcion(descripcion);

        if(!usuario.isActivo()){
            throw new RuntimeException("Usuario inactivo papu");
        }

        if(ticket.getDescripcion() == null){
            throw new RuntimeException("La descripción no puede estar vacía");
        }

        ticket.setEstado("ABIERTO");
        ticket.setPrioridad(prioridad);
        ticket.setUsuario(usuario);

        return ticketRepository.save(ticket);
    }

    public Ticket actualizarEstadoTicket(Long ticketId){
        Ticket ticket = ticketRepository.findById(ticketId).get();

        if(ticket.getEstado().equals("CERRADO")){
            throw new RuntimeException("El ticket ya está cerró");
        }

        if(ticket.getEstado().equals("ABIERTO")){
            ticket.setEstado("EN_PROCESO");
        }else if(ticket.getEstado().equals("EN_PROCESO")){
            ticket.setEstado("CERRADO");
        }

        return ticketRepository.save(ticket);
    }

    public Ticket reasignarPrioridad(Long ticketId, String nuevaPrioridad){
        Ticket ticket = ticketRepository.findById(ticketId).get();

        if(ticket.getEstado().equals("CERRADO")){
            throw new RuntimeException("El ticket ya está cerró");
        }

        ticket.setPrioridad(nuevaPrioridad);

        return ticketRepository.save(ticket);
    }

    public Usuario desactivarUsuario(Long usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId).get();

        usuario.setActivo(false);

        return usuarioRepository.save(usuario);
    }

    public List<Ticket>obtenerTicketsUsuario(Long usuarioId){
        return ticketRepository.obtenerByUserId(usuarioId);
    }
}
