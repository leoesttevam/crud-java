package br.com.springboot.curso_jdev.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev.model.Usuario;
import br.com.springboot.curso_jdev.repository.UsuarioRpository;


@RestController
public class GreetingsController {
	
	@Autowired // IC/CD ou CDI - Injecão de depedencia
	private UsuarioRpository usuarioRepository;
    
	
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET) /* O / intercepta tudo que vem depois da porta 8000 */
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso Spring boot " + name + "!";
    }
    
    @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario); //Vai gravar no banco de dados.
    	
    	return "Olá Mundo " + nome;
    }
    
    //Metodo API - Buscar.
    @GetMapping(value = "listatodos") //listatodos usamos para retorna uma lista de usuarios.
    @ResponseBody //Vai retorna os dados para o corpo da resposta.
    public ResponseEntity<List<Usuario>> listaUsuario(){  //Vai retornar uma lista de usuarios.
    	
    	List<Usuario> usuarios = usuarioRepository.findAll(); //Vai retorna uma lista de usuarios no banco de dados.
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); //Vai retorna um JSON para o corpo da resposta.
    	
    }
    
    //Metodo API - salvar.
    @PostMapping(value = "salvar") //Vai mapear a url.
    @ResponseBody //Vai fazer a descrição da resposta.
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){  //Vai receber os dados para salvar.
    	
    	
    	Usuario user = usuarioRepository.save(usuario); //Ele vai retonar os dados salvos. 
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED); //Vai retornar um JSON com os dados salvos para o corpo da resposta.
    }
    
    //Metodo API - deletar.
    @DeleteMapping(value = "delete") //Vai mapear a url.
    @ResponseBody //Vai fazer a descrição da resposta.
    public ResponseEntity<String> delete(@RequestParam Long iduser){ //Recebe os dados para deletar.
    	
    	usuarioRepository.deleteById(iduser);
    	
    	return new ResponseEntity<String>("Usuario deletado com sucesso", HttpStatus.OK);
    }
    
    //Metodo API - Buscar por ID
    @GetMapping(value = "buscaruserid") //Vai mapear a url.
    @ResponseBody //Vai fazer a descrição da resposta.
    public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "iduser") Long iduser){ //Vai receber os dados para pesquisa.
    	
    	Usuario usuario = usuarioRepository.findById(iduser).get(); //Recebe os dados para consultar.
    		
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK); //Vai fazer o retorno para tela.
    }
    
    //Metodo API - atualizar
    @PutMapping(value = "atualizar") //Vai mapear a url.
    @ResponseBody //Vai fazer a descrição da resposta.
    //Quando tem ? podemos retorna qualquer coisa.
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario){ //Vai receber os dados e atualizar
    	
    	if(usuario.getId() == null) {
    		return new ResponseEntity<String>("Id não foi informado para atualização.", HttpStatus.OK);
    	}
    	
    	Usuario user = usuarioRepository.saveAndFlush(usuario); //Vai salvar e atualizar atraves do banco de dados
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    
    @GetMapping(value = "buscarPorNome") //mapear a url.
    @ResponseBody //descrição da resposta.
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name){ //Receber dados para consultar.
    	
    	
    	List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
    	
    	return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    }
}
