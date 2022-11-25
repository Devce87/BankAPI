package backend.bankaccount.web.app.mapper;

import org.mapstruct.Mapper;

@Mapper
public interface DefaultMapper<Entity, DTO>{

    DTO entityToDTO(Entity entity);
    Entity DTOtoEntity (DTO dto);

}
