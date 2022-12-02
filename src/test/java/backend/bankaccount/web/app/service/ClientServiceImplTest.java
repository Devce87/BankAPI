package backend.bankaccount.web.app.service;

import backend.bankaccount.web.app.domain.entity.Client;
import backend.bankaccount.web.app.domain.repo.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ClientServiceImplTest {


    @Test
    void findClientById() {
        ClientRepository repository = mock(ClientRepository.class);

        Optional<Client> client = Optional.ofNullable(mock(Client.class));

        when(repository.findById(1L)).thenReturn(client);

        Assertions.assertNotNull(client);
        Assertions.assertEquals(client,repository.findById(1L));

    }
}