package com.springboot.jdevtreinamentospringboot.application.controller;

import com.springboot.jdevtreinamentospringboot.application.model.Usuario;
import com.springboot.jdevtreinamentospringboot.application.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    @Autowired //injeção de dependencia
    private UsuarioRepository usuarioRepository;

    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String grettingText(@PathVariable String name) {

        return "Curso Spring Boot API " + name + "!";
    }

    @RequestMapping(value = "/helloworld/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String returnHelloWorld(@PathVariable String name) {

        Usuario usuario = new Usuario();
        usuario.setName(name);
        usuarioRepository.save(usuario); //gravação no banco de dados
        return "Hello World: " + name;
    }

    @GetMapping(value = "listAll") //primeiro método de API
    @ResponseBody //retorna os dados para o corpo da resposta = JSON
    public ResponseEntity<List<Usuario>> userList() {
        List<Usuario> usuarios = usuarioRepository.findAll(); //executa consulta no banco de dados
        return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); //Retorna a lista em JSON
    }

    @PostMapping(value = "save") //mapeia a url
    @ResponseBody //response descrição da resposta
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) { //request recebe os dados para salvar
        Usuario user = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "delete") //mapeia a url
    @ResponseBody //response descrição da resposta
    public ResponseEntity<String> delete(@RequestParam Long idUser) { //request recebe os dados para deletar

        usuarioRepository.deleteById(idUser);

        return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);
    }

    @GetMapping(value = "searchUserById") //mapeia a url
    @ResponseBody //response descrição da resposta
    public ResponseEntity<Usuario> searchUserById(@RequestParam(name = "idUser") Long idUser) { //request recebe os dados para consultar

        Usuario usuario = usuarioRepository.findById(idUser).get();

        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

    @PutMapping(value = "update") //mapeia a url
    @ResponseBody //response descrição da resposta
    public ResponseEntity<?> update(@RequestBody Usuario usuario) { //request recebe os dados para atualizar

        if (usuario.getId() == null) {
            return new ResponseEntity<String>("ID não foi informado para atualização", HttpStatus.OK);
        } else {
            Usuario user = usuarioRepository.saveAndFlush(usuario);
            return new ResponseEntity<Usuario>(user, HttpStatus.OK);
        }
    }

    @GetMapping(value = "searchUserByName") //mapeia a url
    @ResponseBody //response descrição da resposta
    public ResponseEntity<List<Usuario>> searchByName(@RequestParam(name = "name") String name) {
        //request recebe os dados para consultar
        List<Usuario> usuario = usuarioRepository.searchByName(name.trim().toUpperCase());

        return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    }
}
