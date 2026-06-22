package com.example.service_auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service_auth.dto.AuthRequest;
import com.example.service_auth.model.Usuario;
import com.example.service_auth.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AutenticacionController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@RequestBody Usuario usuario){
        return ResponseEntity.ok(authService.registrar(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request){
        try{
            String token = authService.login(request.getNombreUsuario(), request.getPassword());
            return ResponseEntity.ok(token);
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
