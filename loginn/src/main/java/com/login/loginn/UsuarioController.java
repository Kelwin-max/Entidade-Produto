package com.login.loginn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping("/cadastro")
    public ResponseEntity<Usuario> saveUser(@RequestBody Usuario user) {
        
        Usuario novoUsuario = new Usuario(user.getName(), user.getPassword(), user.getEmail());
        Usuario salvo = usuarioRepository.save(novoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario user) {
        Usuario findUser = usuarioRepository.findByEmail(user.getEmail());
        
        if (findUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        } 
        
        if (findUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok("Logado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha incorreta.");
        }
    }
    
    @GetMapping
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public Optional<Usuario> usuarioPorId(@PathVariable int id) {
        return usuarioRepository.findById(id);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuarioPorId(@PathVariable int id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
            
            return ResponseEntity.noContent().build();
        } else {
            
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(
            @PathVariable int id,
            @RequestBody Usuario novoUsuario) {
                    
            Optional<Usuario> usuarioExistente = usuarioRepository.findById(id); 
            
            if (usuarioExistente.isPresent()) {
                Usuario usuario = usuarioExistente.get();
                
                
                usuario.setName(novoUsuario.getName());
                usuario.setPassword(novoUsuario.getPassword());
                usuario.setEmail(novoUsuario.getEmail());
                
                Usuario usuarioAtualizado = usuarioRepository.save(usuario);
                
                
                return ResponseEntity.ok(usuarioAtualizado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        
}