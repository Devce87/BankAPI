package backend.bankaccount.web.app.mapper;

import backend.bankaccount.web.app.domain.dto.ClientDTO;
import backend.bankaccount.web.app.domain.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper implements DefaultMapper<Client, ClientDTO>{


    @Override
    public ClientDTO entityToDTO(Client client) {
        return ClientDTO.builder()
                .name(client.getName())
                .age(client.getAge())
                .identification(client.getIdentification())
                .address(client.getAddress())
                .phoneNumber(client.getPhoneNumber())
                .username(client.getUsername())
                .password(client.getPassword())
                .build();
    }

    @Override
    public Client DTOtoEntity(ClientDTO clientDTO) {
        return Client.builder()
                .name(clientDTO.getName())
                .age(clientDTO.getAge())
                .identification(clientDTO.getIdentification())
                .address(clientDTO.getAddress())
                .phoneNumber(clientDTO.getPhoneNumber())
                .username(clientDTO.getUsername())
                .password(clientDTO.getPassword())
                .build();
    }
}
