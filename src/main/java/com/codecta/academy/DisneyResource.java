package com.codecta.academy;

import com.codecta.academy.repository.entity.DisneyCharacter;
import com.codecta.academy.services.DisneyService;
import com.codecta.academy.services.WeatherService;
import com.codecta.academy.services.model.CharacterDto;
import com.codecta.academy.services.model.GiftDto;
import com.codecta.academy.services.model.ParkDto;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Set;

@Path("/disney")
public class DisneyResource {

    public class Error {
        public String code;
        public String description;

        public Error(String code, String description) {
            this.code = code;
            this.description = description;
        }
    }

    @Inject
    DisneyService disneyService;

    @Inject
    @RestClient
    WeatherService weatherService;

    @Inject
    Validator validator;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello() {
        return  Response.ok(disneyService.welcome()).build();
    }

    @GET
    @Path("/characters")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCharacters()
    {
        List<CharacterDto> characterList = disneyService.findAllCharacters();
        if(characterList == null || characterList.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(characterList).build();
    }
    @GET
    @Path("/characters/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCharacterById(@PathParam("id") Integer id)
    {
        CharacterDto character = disneyService.findCharacterById(id);
        if(character == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(character).build();
    }
    @PUT
    @Path("/characters/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCharacterById(@PathParam("id") Integer id, CharacterDto disneyCharacter)
    {
        CharacterDto character = disneyService.updateCharacter(id, disneyCharacter);
        if(character == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(character).build();
    }

    @POST
    @Path("/characters")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createCharacter(CharacterDto character, @Context UriInfo uriInfo)
    {
        CharacterDto disneyCharacter = disneyService.addCharacter(character);
        if(disneyCharacter != null) {
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            uriBuilder.path(Integer.toString(disneyCharacter.getId()));
            return Response.created(uriBuilder.build()).entity(disneyCharacter).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new Error("CDT-001", "Invalid request. Unknown theme park in request.")) .build();
    }

    @POST
    @Path("/themeparks")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createThemePark(ParkDto park, @Context UriInfo uriInfo)
    {
        Set<ConstraintViolation<ParkDto>> constraintViolations = validator.validate(park);
        if(!constraintViolations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<ParkDto> violation :
                    constraintViolations) {
                builder.append(violation.getMessage()).append(", ");
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(new Error("VD-0001", "Invalid request in validation. >> " + builder.toString())).build();
        }
        ParkDto themePark = disneyService.addThemePark(park);
        if(themePark != null) {
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            uriBuilder.path(Integer.toString(themePark.getId()));
            return Response.created(uriBuilder.build()).entity(themePark).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new Error("TPD-001", "Invalid request.")).build();
    }
    @GET
    @Path("/themeparks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllThemeParks()
    {
        List<ParkDto> themeParkList = disneyService.findAllThemeParks();
        if(themeParkList == null || themeParkList.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(themeParkList).build();
    }

    @GET
    @Path("/themeparks/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getThemeParkById(@PathParam("id") Integer id)
    {
        ParkDto themePark = disneyService.findThemeParkById(id);
        if(themePark == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(themePark).build();
    }

    @GET
    @Path("/themeparks/{id}/weather")
    public Response getWeatherForThemePark(@PathParam("id") Integer id)
    {
        ParkDto themePark = disneyService.findThemeParkById(id);
        if(themePark == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(weatherService.getWeather(themePark.getLocation(), "e93fcfe3c443ded48547f1ad30e89803", "metric")).build();

    }
    @GET
    @Path("/themeparks/{id}/characters")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCharactersByParkId(@PathParam("id") Integer id) {
        List<CharacterDto> characterDtoList = disneyService.findCharacterByParkId(id);
        if(characterDtoList == null) {
            return Response.noContent().build();
        }
        return Response.ok(characterDtoList).build();
    }

    @GET
    @Path("/themeparks/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCharactersByParkId(@QueryParam("name") String name) {
        List<ParkDto> parkDtoList = disneyService.findParkByCharacterName(name);
        if(parkDtoList == null) {
            return Response.noContent().build();
        }
        return Response.ok(parkDtoList).build();
    }


    @POST
    @Path("/gifts")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createGift(GiftDto gift, @Context UriInfo uriInfo) {
        GiftDto giftDto = disneyService.addGift(gift);
        if(giftDto != null) {
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            uriBuilder.path(Integer.toString(giftDto.getId()));
            return Response.created(uriBuilder.build()).entity(giftDto).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new Error("GF-0001", "Invalid request.")).build();
    }

    @GET
    @Path("/gifts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGifts()
    {
        List<GiftDto> giftDtoList = disneyService.findAllGifts();
        if(giftDtoList == null || giftDtoList.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(giftDtoList).build();
    }

}