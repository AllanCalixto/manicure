package br.com.calixto.manicure.autenticacao;

import br.com.calixto.manicure.entity.Usuario;
import br.com.calixto.manicure.exception.RegraDeNegocioException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    public String gerarToken(Usuario usuario) throws RegraDeNegocioException {

        try {
            Algorithm algorithm = Algorithm.HMAC256("12345678");
            return JWT.create()
                    .withIssuer("auth0")
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(expiracao(30))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RegraDeNegocioException("Erro ao gerar token JWT de acesso!");
        }

    }

    public String verificarToken(String token) {

        DecodedJWT decodedJWT;
        try {
            Algorithm algorithm = Algorithm.HMAC256("12345678");
            JWTVerifier verifier = JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer("auth0")
                    // reusable verifier instance
                    .build();

            decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("erro");
        }
    }

    private Instant expiracao(Integer minutos) {
        return LocalDateTime.now().plusMinutes(minutos).toInstant(ZoneOffset.of("-03:00"));
    }
}
