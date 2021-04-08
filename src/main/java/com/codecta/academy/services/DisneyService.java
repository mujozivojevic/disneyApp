package com.codecta.academy.services;

import com.codecta.academy.repository.entity.DisneyCharacter;
import com.codecta.academy.repository.entity.WelcomeMessage;
import com.codecta.academy.services.model.CharacterDto;
import com.codecta.academy.services.model.GiftDto;
import com.codecta.academy.services.model.ParkDto;
import java.util.List;

public interface DisneyService {
    WelcomeMessage welcome();

    List<CharacterDto> findAllCharacters();
    CharacterDto findCharacterById(Integer id);
    CharacterDto addCharacter(CharacterDto character);
    CharacterDto updateCharacter(Integer id, CharacterDto character);
    List<ParkDto> findAllThemeParks();
    ParkDto findThemeParkById(Integer id);
    ParkDto addThemePark(ParkDto themePark);
    ParkDto updateThemePark(Integer id, ParkDto themePark);
    List<CharacterDto> findCharacterByParkId(Integer id);
    List<ParkDto> findParkByCharacterName(String name);
    GiftDto addGift(GiftDto giftDto);
    List<GiftDto> findAllGifts();
}
