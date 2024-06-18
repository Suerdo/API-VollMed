package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoDeConsulta {
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        var medicoPossuiConsultaNoMesmoHorario = repository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if(medicoPossuiConsultaNoMesmoHorario) {
            throw new ValidacaoException("Médico já possui uma consulta nesse horário.");
        }
    }
}
