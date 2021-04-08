package com.codecta.academy.services.impl;

import com.codecta.academy.repository.GiftRepository;
import com.codecta.academy.repository.ThemeParkRepository;
import com.codecta.academy.repository.entity.DisneyCharacter;
import com.codecta.academy.repository.entity.Gift;
import com.codecta.academy.repository.entity.ThemePark;
import com.codecta.academy.repository.entity.WelcomeMessage;
import com.codecta.academy.repository.DisneyCharacterRepository;
import com.codecta.academy.services.DisneyService;
import com.codecta.academy.services.model.CharacterDto;
import com.codecta.academy.services.model.GiftDto;
import com.codecta.academy.services.model.ParkDto;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Transactional
public class DisneyServiceImpl implements DisneyService {

    @Inject
    DisneyCharacterRepository disneyCharacterRepository;

    @Inject
    ThemeParkRepository themeParkRepository;

    @Inject
    GiftRepository giftRepository;

    @Override
    public WelcomeMessage welcome() {
        return new WelcomeMessage("Welcome Lands of Disneyland!", "Here you can find....", "Everyday from 8 am to 10 pm");
    }

    @Override
    public List<CharacterDto> findAllCharacters() {

        List<DisneyCharacter> disneyCharacterList = disneyCharacterRepository.findAll();
        if(disneyCharacterList == null || disneyCharacterList.isEmpty()) {
            return null;
        }
        List<CharacterDto> characterDtoList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for(DisneyCharacter character : disneyCharacterList) {
            characterDtoList.add(modelMapper.map(character, CharacterDto.class));
        }
        return characterDtoList;
    }

    @Override
    public CharacterDto findCharacterById(Integer id) {
        DisneyCharacter disneyCharacter = disneyCharacterRepository.findById(id);
        if(disneyCharacter == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(disneyCharacter, CharacterDto.class);
    }

    @Override
    public CharacterDto addCharacter(CharacterDto character) {
        if(character.getThemeParkId() == null) {
            return null;
        }
        ThemePark themePark = themeParkRepository.findById(character.getThemeParkId());
        if(themePark == null) {
            return null;
        }
        ModelMapper modelMapper = new ModelMapper();
        DisneyCharacter disneyCharacter = modelMapper.map(character, DisneyCharacter.class);
        disneyCharacter.setThemePark(themePark);
        disneyCharacter = disneyCharacterRepository.add(disneyCharacter);
        return modelMapper.map(disneyCharacter, CharacterDto.class);
    }

    @Override
    public CharacterDto updateCharacter(Integer id, CharacterDto character) {
        DisneyCharacter disneyCharacter = disneyCharacterRepository.findById(id);
        if(disneyCharacter != null) {
            disneyCharacter.setGreeting(character.getGreeting());
            disneyCharacter.setName(character.getName());
            disneyCharacter = disneyCharacterRepository.save(disneyCharacter);
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(disneyCharacter, CharacterDto.class);
        }
        return null;
    }

    @Override
    public List<ParkDto> findAllThemeParks() {
        List<ThemePark> themeParks = themeParkRepository.findAll();
        if(themeParks == null || themeParks.isEmpty()) {
            return  null;
        }
        List<ParkDto> parkDtoList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (ThemePark park :
                themeParks) {
            ParkDto parkDto = modelMapper.map(park, ParkDto.class);
            parkDtoList.add(parkDto);
        }
        return parkDtoList;
    }

    @Override
    public ParkDto findThemeParkById(Integer id) {
        ThemePark park = themeParkRepository.findById(id);
        if(park == null) {
            return  null;
        }
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(park, ParkDto.class);
    }

    @Override
    public ParkDto addThemePark(ParkDto themePark) {
        ModelMapper modelMapper = new ModelMapper();
        ThemePark  dbThemePark = modelMapper.map(themePark, ThemePark.class);
        dbThemePark = themeParkRepository.add(dbThemePark);
        return modelMapper.map(dbThemePark, ParkDto.class);
    }

    @Override
    public ParkDto updateThemePark(Integer id, ParkDto themePark) {
        ThemePark dbThemePark = themeParkRepository.findById(id);
        if (dbThemePark != null) {
            dbThemePark.setAttractions(themePark.getAttractions());
            dbThemePark.setLocation(themePark.getLocation());
            dbThemePark = themeParkRepository.save(dbThemePark);
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(dbThemePark, ParkDto.class);
        }
        return null;
    }

    @Override
    public List<CharacterDto> findCharacterByParkId(Integer id) {
        List<DisneyCharacter> disneyCharacterList = disneyCharacterRepository.findAllByParkId(id);
        if(disneyCharacterList == null || disneyCharacterList.isEmpty()) {
            return null;
        }
        List<CharacterDto> characterDtoList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (DisneyCharacter character :
                disneyCharacterList) {
            characterDtoList.add(modelMapper.map(character, CharacterDto.class));
        }
        return characterDtoList;
    }

    @Override
    public List<ParkDto> findParkByCharacterName(String name) {
        List<ThemePark> themeParkList = themeParkRepository.findByCharacterName(name);
        if(themeParkList == null || themeParkList.isEmpty()) {
            return null;
        }
        List<ParkDto> parkDtoList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (ThemePark park :
                themeParkList) {
            parkDtoList.add(modelMapper.map(park, ParkDto.class));
        }
        return parkDtoList;
    }

    @Override
    public GiftDto addGift(GiftDto giftDto) {
        if(giftDto.getCharacterIdList() == null || giftDto.getCharacterIdList().isEmpty()) {
            return null;
        }
        List<DisneyCharacter> characterList = disneyCharacterRepository.findAllByIdList(giftDto.getCharacterIdList());
        if(characterList == null || characterList.isEmpty()) {
            return null;
        }

        ModelMapper modelMapper = new ModelMapper();
        Gift gift = modelMapper.map(giftDto, Gift.class);
        gift = giftRepository.add(gift);
        for (DisneyCharacter character :
                characterList) {
            character.getGifts().add(gift);
            gift.getCharacters().add(character);
            disneyCharacterRepository.save(character);
            giftRepository.save(gift);
        }
        giftDto = modelMapper.map(gift, GiftDto.class);
        for(DisneyCharacter ch: gift.getCharacters()) {
            giftDto.getCharacterIdList().add(ch.getId());
        }
        return  giftDto;
    }

    @Override
    public List<GiftDto> findAllGifts() {
        List<Gift> allGifts = giftRepository.findAll();
        if(allGifts == null || allGifts.isEmpty()) {
            return null;
        }
        List<GiftDto> result = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (Gift gift :
                allGifts) {
            GiftDto giftDto = modelMapper.map(gift, GiftDto.class);
            result.add(giftDto);
        }
        return result;
    }
}
