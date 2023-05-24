package com.example.lab6_sol.controller;

import com.example.lab6_sol.entity.Usuario;
import com.example.lab6_sol.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/lista")
    public String listaUsuarios(Model model){
        List<Usuario> estudiantes = usuarioRepository.findByRolid(5);
        model.addAttribute("estudiantes", estudiantes);
        return "lista_usuarios";
    }

    @GetMapping("/crear")
    public String crearUsuario(@ModelAttribute("usuario") Usuario usuario){
        return "formulario";
    }

    @GetMapping("/editar")
    public String editarUsuario(@ModelAttribute("usuario") Usuario usuario,
                                @RequestParam(value = "u") Integer idUsuario,
                                Model model){
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
        if (usuarioOptional.isPresent()){
            usuario = usuarioOptional.get();
            model.addAttribute("usuario", usuario);
        }
        return "formulario";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "formulario";
        }
        if (usuario.getId() == 0){
            usuario.setActivo(Boolean.TRUE);
            usuario.setRolid(5);
        }
        else{
            usuario.setPassword(usuarioRepository.findById(usuario.getId()).get().getPassword());
        }
        usuarioRepository.save(usuario);
        return "redirect:/estudiante/lista";
    }

    @GetMapping("/cursos")
    public String misCursos(){
        return "mis_cursos";
    }
}
