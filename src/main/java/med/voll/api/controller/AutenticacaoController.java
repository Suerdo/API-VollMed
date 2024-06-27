package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.DadosAutenticacaoCadastro;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autenticacao")
@CrossOrigin(origins = "http://localhost:4200")
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private  TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @PostMapping("/login")
    public ResponseEntity efutuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authenticaon = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((Usuario)authenticaon.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @PostMapping("/registro")
    @Transactional
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid DadosAutenticacaoCadastro dadosCadastro) {
        var novoUsuario = new Usuario(dadosCadastro, passwordEncoder);
        usuarioRepository.save(novoUsuario);
        var tokenJWT = tokenService.gerarToken(novoUsuario);
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
