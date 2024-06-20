package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.time.DayOfWeek;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria devolver null quando o unico médico cadastrado não está disponível na data.")
    void escolherMedicoAleatorioLivreDataCenario1() {
        //Given ou arrange
        // Obtém o LocalDateTime atual
        LocalDate date = LocalDate.now();

        // Ajusta para a próxima segunda-feira
        LocalDateTime dateTime = date.atTime(10, 0);

        // Ajusta a hora para as 10 da manhã, minuto, segundo e nanosegundo para zero
        LocalDateTime proximaSegundaAsDez = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour(10)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        var medico = cadastrarMedico("MedicoTest", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);
        var paciente = cadastrarPaciente("Paciente", "paciente@gmail.com");
        cadastrarConsulta(medico, paciente, proximaSegundaAsDez);

        // When ou Act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreData(Especialidade.CARDIOLOGIA, proximaSegundaAsDez);

        // Then ou Assert
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver médico quando ele estiver disponível na data.")
    void escolherMedicoAleatorioLivreDataCenario2() {

        LocalDate date = LocalDate.now();

        LocalDateTime dateTime = date.atTime(10, 0);

        LocalDateTime proximaSegundaAsDez = LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .withHour(10)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        var medico = cadastrarMedico("MedicoTest", "medico@voll.med", "123456", Especialidade.CARDIOLOGIA);

        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreData(Especialidade.CARDIOLOGIA, proximaSegundaAsDez);
        assertThat(medicoLivre).isEqualTo(medico);
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico,paciente, data));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade){
       var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
       em.persist(medico);
       return medico;
    }

    private Paciente cadastrarPaciente(String nome, String email) {
        var paciente = new Paciente((dadosPaciente(nome, email)));
        em.persist(paciente);
        return paciente;
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "61999999999",
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "Rua Test",
                "Bairro Test",
                "00000000",
                "Cidade Test",
                "TS",
                null,
                null
        );
    }
}