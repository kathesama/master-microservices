package com.kathesama.app.master.microservices.service.account.infrastructure.adapter.input.rest.mapper;

import com.kathesama.app.master.microservices.service.common.domain.model.Card;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.request.CardRequestModel;
import com.kathesama.app.master.microservices.service.common.infrastructure.adapter.input.rest.dto.model.response.CardResponseModel;
import org.mapstruct.Mapper;
import org.springframework.http.ResponseEntity;

@Mapper(componentModel = "spring")
public interface CardRestMapper {
    Card toCard(CardResponseModel cardResponseModel);
}
