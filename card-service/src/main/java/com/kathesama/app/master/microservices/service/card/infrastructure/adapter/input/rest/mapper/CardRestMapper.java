package com.kathesama.app.master.microservices.service.card.infrastructure.adapter.input.rest.mapper;

import com.kathesama.app.master.microservices.service.card.domain.model.Card;
import com.kathesama.app.master.microservices.service.card.infrastructure.adapter.input.rest.dto.model.request.CardRequestModel;
import com.kathesama.app.master.microservices.service.card.infrastructure.adapter.input.rest.dto.model.response.CardResponseModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardRestMapper {
    CardResponseModel toCardResponse(Card account);
    Card toCard(CardRequestModel cardRequestModel);
}
