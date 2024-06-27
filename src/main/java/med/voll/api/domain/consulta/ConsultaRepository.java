package med.voll.api.domain.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    //Verifica se há alguma consulta agendada para um paciente em um intervalo de datas específico.
    boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primeiroHorario, LocalDateTime ultimoHorario);
    //Verifica se um médico já tem uma consulta agendada para uma data específica.
    boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime data);

}
