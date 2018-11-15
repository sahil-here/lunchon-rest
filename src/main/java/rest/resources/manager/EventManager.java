package rest.resources.manager;

import exception.LOErrorCode;
import exception.LOException;
import lunchon.entity.EventStatusName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.dao.*;
import rest.dao.entity.*;
import rest.request.CreateEventRequest;
import rest.response.CreateUpdateEventResponse;
import rest.response.GetEventDetailsResponse;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EventManager implements IEventManager {

    private static final Logger logger = LoggerFactory.getLogger(EventManager.class);

    @Inject
    protected IEventDAO eventDAO;

    @Inject
    protected ITimeDAO timeDAO;

    @Inject
    protected IRestaurantDAO restaurantDAO;

    @Inject
    protected ICuisineDAO cuisineDAO;

    @Inject
    protected ILocationDAO locationDAO;

    @Inject
    protected IUserDAO userDAO;

    @Inject
    protected IEventStatusDAO eventStatusDAO;



    @Override
    public CreateUpdateEventResponse createEvent(CreateEventRequest createEventRequest) throws LOException {
        Event event = new Event();
        CreateUpdateEventResponse createUpdateEventResponse = new CreateUpdateEventResponse();
        Event existing = eventDAO.idempotencyCheck(createEventRequest);
        if(existing==null){

            event.setBudget(createEventRequest.getBudget());
            event.setDescription(createEventRequest.getDescription());
            event.setName(createEventRequest.getName());
            event.setOrganiser(userDAO.findUserById(createEventRequest.getOrganiserId()));


            //Setting Participants
            List<User> participants = new ArrayList<>();
            for(Long id: createEventRequest.getParticipantIds()){
                participants.add(userDAO.findUserById(id));
            }
            event.setParticipants(participants);

            //Setting Location of the event
            Location existingLocation = locationDAO.idempotencyCheck(createEventRequest.getLocation());
            if(existingLocation==null){
                Location location = new Location();
                location.setLatitude(createEventRequest.getLocation().getLatitude());
                location.setLongitude(createEventRequest.getLocation().getLongitude());
                location.setRadius(createEventRequest.getLocation().getRadius());
                location.setRadiusUnit(createEventRequest.getLocation().getRadiusUnit());
                location.setCity(createEventRequest.getLocation().getCity());
                location.setState(createEventRequest.getLocation().getState());
                location.setCountry(createEventRequest.getLocation().getCountry());
                location.setZipcode(createEventRequest.getLocation().getZipcode());
                event.setLocation(locationDAO.createOrUpdate(location));
            }else{
                event.setLocation(existingLocation);
            }


            //Setting time choices
            List<Time> timeChoices = new ArrayList<>();
            for(rest.request.Time time: createEventRequest.getTimeChoices()){
                Time existingTime = timeDAO.idempotencyCheck(time);
                if(existingTime==null){
                    Time time1 = new Time();
                    time.setCreatedAt(time.getCreatedAt());
                    time.setStartTime(time.getStartTime());
                    time.setEndTime(time.getEndTime());
                    timeChoices.add(timeDAO.createOrUpdate(time1));
                }else{
                    timeChoices.add(existingTime);
                }
            }
            event.setTimeChoices(timeChoices);

            //Setting restaurant choices
            List<Restaurant> restaurantChoices = new ArrayList<>();
            for(rest.request.Restaurant restaurant: createEventRequest.getResturantChoices()){
                Restaurant existingRestaurant = restaurantDAO.idempotencyCheck(restaurant);
                if(existingRestaurant==null){
                    Restaurant restaurant1 = new Restaurant();
                    restaurant1.setId(restaurant.getId());
                    restaurant1.setName(restaurant.getName());
                    restaurant1.setAlias(restaurant.getAlias());
                    restaurant1.setImageUrl(restaurant.getImageUrl());
                    restaurant1.setUrl(restaurant.getUrl());
                    restaurant1.setPhone(restaurant.getPhone());
                    restaurant1.setPrice(restaurant.getPrice());
                    restaurant1.setRating(restaurant.getRating());
                    restaurant1.setAddress1(restaurant.getLocation().getAddress1());
                    restaurant1.setAddress2(restaurant.getLocation().getAddress2());
                    restaurant1.setAddress3(restaurant.getLocation().getAddress3());
                    restaurant1.setCity(restaurant.getLocation().getCity());
                    restaurant1.setState(restaurant.getLocation().getState());
                    restaurant1.setCountry(restaurant.getLocation().getCountry());
                    restaurant1.setLongitude(restaurant.getCoordinates().getLongitude());
                    restaurant1.setLatitude(restaurant.getCoordinates().getLatitude());
                    restaurant1.setZipcode(restaurant.getLocation().getZipcode());
                    restaurantChoices.add(restaurantDAO.createOrUpdate(restaurant1));
                }else{
                    restaurantChoices.add(existingRestaurant);
                }
            }
            event.setRestaurantChoices(restaurantChoices);

            //Setting Cuisine choices
            List<Cuisine> cuisineChoices = new ArrayList<>();
            for(rest.request.Cuisine cuisine: createEventRequest.getCuisineChoices()){
                cuisineChoices.add(cuisineDAO.findCuisineById(cuisine.getId()));
            }
            event.setCuisineChoices(cuisineChoices);

            //Setting final chosen options
            event.setFinalCuisine(cuisineDAO.findCuisineById(createEventRequest.getFinalCuisineId()));
            event.setFinalRestaurant(restaurantDAO.findRestautantById(createEventRequest.getFinalRestaurantId()));
            event.setFinalTime(timeDAO.findTimeById(createEventRequest.getFinalTimeId()));

            Event createdEvent = eventDAO.createOrUpdate(event);
            EventStatus eventStatus = new EventStatus();
            eventStatus.setEvent(createdEvent);
            eventStatus.setStatus(EventStatusName.CREATED);
            eventStatus.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            eventStatus.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            eventStatusDAO.createOrUpdate(eventStatus);
            createUpdateEventResponse.setId(createdEvent.getId());
            createUpdateEventResponse.setName(createdEvent.getName());
            return createUpdateEventResponse;
        }else{
            throw new LOException(400, LOErrorCode.EVENT_ALREADY_PRESENT.getName());
        }
    }

    @Override
    public GetEventDetailsResponse getEventDetails(Long eventId) throws LOException {
        Event event = eventDAO.findEventById(eventId);
        if(event==null){
            throw new LOException(400,LOErrorCode.EVENT_NOT_FOUND.getName());
        }
        GetEventDetailsResponse response = new GetEventDetailsResponse();
        response.setId(event.getId());
        response.setName(event.getName());
        response.setBudget(event.getBudget());
        response.setDescription(event.getDescription());
        if(event.getFinalCuisine()!=null){
            response.setFinalCuisineId(event.getFinalCuisine().getId());
        }
        if(event.getFinalRestaurant()!=null){
            response.setFinalRestaurantId(event.getFinalRestaurant().getId());
        }
        if(event.getFinalTime()!=null){
            response.setFinalTimeId(event.getFinalTime().getId());
        }
        response.setOrganiserId(event.getOrganiser().getId());
        rest.request.Location location = new rest.request.Location();
        location.setId(event.getLocation().getId());
        location.setLatitude(event.getLocation().getLatitude());
        location.setLongitude(event.getLocation().getLongitude());
        location.setRadius(event.getLocation().getRadius());
        location.setRadiusUnit(event.getLocation().getRadiusUnit());
        location.setCity(event.getLocation().getCity());
        location.setState(event.getLocation().getState());
        location.setCountry(event.getLocation().getCountry());
        location.setZipcode(event.getLocation().getZipcode());
        response.setLocation(location);
        List<Long> participants = new ArrayList<>();
        for(User participant: event.getParticipants()){
            participants.add(participant.getId());
        }
        response.setParticipantIds(participants);

        List<rest.request.Cuisine> cuisines = new ArrayList<>();
        for(Cuisine cuisine: event.getCuisineChoices()){
            rest.request.Cuisine cu = new rest.request.Cuisine();
            cu.setId(cuisine.getId());
            cu.setName(cuisine.getName());
            cuisines.add(cu);
        }
        response.setCuisineChoices(cuisines);

        List<rest.request.CuisinePoll> cuisinePolls = new ArrayList<>();
        for(CuisinePoll cuisinePoll: event.getCuisinePolls()){
            rest.request.CuisinePoll cp = new rest.request.CuisinePoll();
            cp.setCuisineId(cuisinePoll.getCuisine().getId());
            cp.setCuisineName(cuisinePoll.getCuisine().getName());
            cp.setVoterId(cuisinePoll.getVoter().getId());
            cp.setCreatedAt(cuisinePoll.getUpdatedAt());
            cuisinePolls.add(cp);
        }
        response.setCuisinePolls(cuisinePolls);

        List<rest.request.Time> times = new ArrayList<>();
        for(Time time: event.getTimeChoices()){
            rest.request.Time time1 = new rest.request.Time();
            time1.setId(time.getId());
            time1.setStartTime(time.getStartTime());
            time1.setEndTime(time.getEndTime());
            time1.setCreatedAt(time.getCreatedAt());
            times.add(time1);
        }
        response.setTimeChoices(times);

        List<rest.request.TimePoll> timePolls = new ArrayList<>();
        for(TimePoll timePoll: event.getTimePolls()){
            rest.request.TimePoll tp = new rest.request.TimePoll();
            tp.setTimeId(timePoll.getTime().getId());
            tp.setStartTime(timePoll.getTime().getStartTime());
            tp.setEndTime(timePoll.getTime().getEndTime());
            tp.setVoterId(timePoll.getVoter().getId());
            tp.setCreatedAt(timePoll.getUpdatedAt());
            timePolls.add(tp);
        }
        response.setTimePolls(timePolls);

        List<rest.request.Restaurant> restaurants = new ArrayList<>();
        for(Restaurant restaurant: event.getRestaurantChoices()){
            rest.request.Restaurant restaurant1 =  new rest.request.Restaurant();
            //TODO
        }
        response.setResturantChoices(restaurants);

        List<rest.request.RestaurantPoll> restaurantPolls = new ArrayList<>();
        for(RestaurantPoll restaurantPoll : event.getRestaurantPolls()){
            rest.request.RestaurantPoll rp = new rest.request.RestaurantPoll();
            rp.setRestaurantId(restaurantPoll.getRestaurant().getId());
            rp.setRestaurantName(restaurantPoll.getRestaurant().getName());
            rp.setVoterId(restaurantPoll.getVoter().getId());
            rp.setCreatedAt(restaurantPoll.getUpdatedAt());
            restaurantPolls.add(rp);
        }
        response.setRestaurantPolls(restaurantPolls);

        return response;
    }

}
