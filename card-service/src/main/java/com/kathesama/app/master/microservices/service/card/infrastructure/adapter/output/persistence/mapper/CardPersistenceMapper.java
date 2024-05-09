package com.kathesama.app.master.microservices.service.card.infrastructure.adapter.output.persistence.mapper;

import com.kathesama.app.master.microservices.service.common.domain.model.Card;
import com.kathesama.app.master.microservices.service.card.infrastructure.adapter.output.persistence.entity.CardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardPersistenceMapper {
    CardEntity toCardEntity(Card card);
    Card toCard(CardEntity cardEntity);
    List<Card> toCards(List<CardEntity> cardEntities);

    @Mapping(target = "cardId", ignore = true) // Ignorar el ID para evitar sobrescribirlo
    void updateCardFromDto(Card source, @MappingTarget CardEntity target);
}
