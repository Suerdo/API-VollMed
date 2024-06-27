package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDateTime;

public record DadosListagemConsulta(
        @NotBlank String nomeMedico,
        Long idMedico,
        @NotBlank String nomePaciente,
        @NotNull Long idPaciente,
        @NotNull @Future LocalDateTime data,
        @NotNull Long idConsulta) {

        public DadosListagemConsulta(Consulta consulta) {
                this(
                        consulta.getMedico() != null ? consulta.getMedico().getNome() : null,
                        consulta.getMedico() != null ? consulta.getMedico().getId() : null,
                        consulta.getPaciente() != null ? consulta.getPaciente().getNome() : null,
                        consulta.getPaciente() != null ? consulta.getPaciente().getId() : null,
                        consulta.getData(),
                        consulta.getId()
                );
        }
}
