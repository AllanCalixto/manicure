package br.com.calixto.manicure.controller;

import br.com.calixto.manicure.autenticacao.DadosLogin;
import br.com.calixto.manicure.autenticacao.TokenService;
import br.com.calixto.manicure.entity.Usuario;
import br.com.calixto.manicure.exception.RegraDeNegocioException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }



    @PostMapping("/api/v1/login")
    public ResponseEntity<String> efetuarLogin(@RequestBody DadosLogin dados ) throws RegraDeNegocioException {
        // verificar os dados da requisição e compara com o que existem no banco
       var authenticationToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
       var authentication = authenticationManager.authenticate(authenticationToken);

       String tokenAcesso = tokenService.gerarToken((Usuario) authentication.getPrincipal());
       return ResponseEntity.ok(tokenAcesso);
    }
}
