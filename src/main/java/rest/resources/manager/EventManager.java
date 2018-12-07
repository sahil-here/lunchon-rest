package rest.resources.manager;

import exception.LOErrorCode;
import exception.LOException;
import lunchon.entity.EventStatusName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rest.dao.*;
import rest.dao.entity.*;
import rest.request.Coordinates;
import rest.request.CreateUpdateEventRequest;
import rest.request.VoteRequest;
import rest.response.CreateUpdateEventResponse;
import rest.response.GetEventDetailsResponse;
import rest.response.GetMinUserDetailsResponse;

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

    @Inject
    protected ICuisinePollDAO cuisinePollDAO;

    @Inject
    protected ITimePollDAO timePollDAO;

    @Inject
    protected IRestaurantPollDAO restaurantPollDAO;

    @Override
    public CreateUpdateEventResponse createOrUpdateEvent(CreateUpdateEventRequest createUpdateEventRequest, Long eventId) throws LOException {
        Event event = null;
        if(eventId==null){
            event = new Event();
            event.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            event.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        }else{
            event = eventDAO.findEventById(eventId);
            event.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        }
        event.setBudget(createUpdateEventRequest.getBudget());
        event.setDescription(createUpdateEventRequest.getDescription());
        event.setName(createUpdateEventRequest.getName());
        event.setOrganiser(userDAO.findUserById(createUpdateEventRequest.getOrganiserId()));

        //Setting Participants
        List<User> participants = new ArrayList<>();
        for(Long id: createUpdateEventRequest.getParticipantIds()){
            participants.add(userDAO.findUserById(id));
        }
        event.setParticipants(participants);

        //Setting Location of the event
        Location existingLocation = locationDAO.idempotencyCheck(createUpdateEventRequest.getLocation());
        if(existingLocation==null){
            Location location = new Location();
            location.setLatitude(createUpdateEventRequest.getLocation().getLatitude());
            location.setLongitude(createUpdateEventRequest.getLocation().getLongitude());
            location.setRadius(createUpdateEventRequest.getLocation().getRadius());
            location.setRadiusUnit(createUpdateEventRequest.getLocation().getRadiusUnit());
            location.setCity(createUpdateEventRequest.getLocation().getCity());
            location.setState(createUpdateEventRequest.getLocation().getState());
            location.setCountry(createUpdateEventRequest.getLocation().getCountry());
            location.setZipcode(createUpdateEventRequest.getLocation().getZipcode());
            event.setLocation(locationDAO.createOrUpdate(location));
        }else{
            event.setLocation(existingLocation);
        }

        //Setting time choices
        List<Time> timeChoices = new ArrayList<>();
        for(rest.request.Time time: createUpdateEventRequest.getTimeChoices()){
            Time existingTime = timeDAO.idempotencyCheck(time);
            if(existingTime==null){
                Time time1 = new Time();
                time1.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                time1.setStartTime(time.getStartTime());
                time1.setEndTime(time.getEndTime());
                timeChoices.add(timeDAO.createOrUpdate(time1));
            }else{
                timeChoices.add(existingTime);
            }
        }
        event.setTimeChoices(timeChoices);

        //Setting restaurant choices
        List<Restaurant> restaurantChoices = new ArrayList<>();
        for(rest.request.Restaurant restaurant: createUpdateEventRequest.getResturantChoices()){
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
        event.setCuisineChoices(createUpdateEventRequest.getCuisineChoices());

        //Setting final chosen options
        event.setFinalCuisine(cuisineDAO.findCuisineById(createUpdateEventRequest.getFinalCuisineId()));
        event.setFinalRestaurant(restaurantDAO.findRestautantById(createUpdateEventRequest.getFinalRestaurantId()));
        event.setFinalTime(timeDAO.findTimeById(createUpdateEventRequest.getFinalTimeId()));

        Event createdOrUpdatedEvent = eventDAO.createOrUpdate(event);

        //Update the status accordingly
        EventStatus eventStatus = new EventStatus();
        eventStatus.setEvent(createdOrUpdatedEvent);
        if(eventId==null){
            eventStatus.setStatus(EventStatusName.CREATED);
        }else if(createdOrUpdatedEvent.getFinalTime()!=null && createdOrUpdatedEvent.getFinalRestaurant()!=null && createdOrUpdatedEvent.getFinalCuisine()!=null){
            eventStatus.setStatus(EventStatusName.FINALIZED);
        }else{
            eventStatus.setStatus(EventStatusName.POLLING);
        }
        eventStatus.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        eventStatus.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        eventStatusDAO.createOrUpdate(eventStatus);

        CreateUpdateEventResponse createUpdateEventResponse = new CreateUpdateEventResponse();
        createUpdateEventResponse.setId(createdOrUpdatedEvent.getId());
        createUpdateEventResponse.setName(createdOrUpdatedEvent.getName());
        return createUpdateEventResponse;
    }

    @Override
    public GetEventDetailsResponse getEventDetails(Long eventId) throws LOException {
        Event event = eventDAO.findEventById(eventId);
        if(event==null){
            throw new LOException(400,LOErrorCode.EVENT_NOT_FOUND.getName());
        }
        return populateEventDetails(event);
    }

    @Override
    public List<Cuisine> getAllCuisines() throws LOException{
        return cuisineDAO.findAllCuisine();
    }

    @Override
    public GetEventDetailsResponse populateEventDetails(Event event){
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
        GetMinUserDetailsResponse organiser = new GetMinUserDetailsResponse();
        organiser.setId(event.getOrganiser().getId());
        organiser.setName(event.getOrganiser().getName());
        organiser.setContact(event.getOrganiser().getContact());
        organiser.setEmail(event.getOrganiser().getEmail());
        response.setOrganiser(organiser);
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
        List<GetMinUserDetailsResponse> participants = new ArrayList<>();
        for(User participant: event.getParticipants()){
            GetMinUserDetailsResponse newParticipant = new GetMinUserDetailsResponse();
            newParticipant.setId(participant.getId());
            newParticipant.setName(participant.getName());
            newParticipant.setEmail(participant.getEmail());
            newParticipant.setContact(participant.getContact());
            participants.add(newParticipant);
        }
        response.setParticipantIds(participants);

        logger.debug("Event with eventId " + event.getId() + " has following cuisine choices.");
        for(Cuisine cuisine: event.getCuisineChoices()){
            logger.debug("Event with eventId " + event.getId() + " has " + " cuisine choice "+cuisine.getId());
        }

        response.setCuisineChoices(event.getCuisineChoices());

        /*logger.info("Event with eventId " + event.getId() + " has following cuisine choices set in the response.");
        for(Cuisine cuisine: response.getCuisineChoices()){
            logger.info("Event with eventId " + event.getId() + " has " + " cuisine choice "+cuisine.getId());
        }*/

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
            restaurant1.setId(restaurant.getId());
            restaurant1.setName(restaurant.getName());
            restaurant1.setAlias(restaurant.getAlias());
            restaurant1.setRating(restaurant.getRating());
            restaurant1.setPrice(restaurant.getPrice());
            restaurant1.setImageUrl(restaurant.getImageUrl());
            restaurant1.setUrl(restaurant.getUrl());
            restaurant1.setPhone(restaurant.getPhone());
            restaurant1.setCoordinates(new Coordinates(restaurant.getLatitude(),restaurant.getLongitude()));
            rest.request.Location location1 = new rest.request.Location();
            location1.setZipcode(restaurant.getZipcode());
            location1.setCountry(restaurant.getCountry());
            location1.setState(restaurant.getState());
            location1.setCity(restaurant.getCity());
            location1.setLongitude(restaurant.getLongitude());
            location1.setLatitude(restaurant.getLatitude());
            location1.setAddress1(restaurant.getAddress1());
            location1.setAddress2(restaurant.getAddress2());
            location1.setAddress3(restaurant.getAddress3());
            restaurant1.setLocation(location1);
            restaurants.add(restaurant1);
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

        List<rest.response.EventStatus> eventStatuses = new ArrayList<>();
        for(EventStatus status: event.getStatuses()){
            rest.response.EventStatus eventStatus = new rest.response.EventStatus();
            eventStatus.setId(status.getId());
            eventStatus.setStatus(status.getStatus().name());
            eventStatus.setCreatedAt(status.getCreatedAt());
            eventStatuses.add(eventStatus);
        }
        response.setStatuses(eventStatuses);

        return response;
    }

    @Override
    public void vote(VoteRequest voteRequest) throws LOException{
        if(voteRequest.getCuisineChoiceId()!=null){
            voteForCuisine(voteRequest);
        }
        if(voteRequest.getRestaurantChoiceId()!=null){
            voteForRestaurant(voteRequest);
        }
        if(voteRequest.getTimeChoiceId()!=null){
            voteForTime(voteRequest);
        }

    }

    @Override
    public void voteForCuisine(VoteRequest voteRequest) throws LOException{
        Event event = eventDAO.findEventById(voteRequest.getEventId());
        if(event==null){
            throw new LOException(400,"Event with id "+voteRequest.getEventId()+" does not exist");
        }
        //Find the cuisine choice being voted
        Cuisine cuisineChoice = null;
        for(Cuisine cuisine: event.getCuisineChoices()){
            if(cuisine.getId().equals(voteRequest.getCuisineChoiceId())){
                cuisineChoice = cuisine;
            }
        }
        if(cuisineChoice==null){
            throw new LOException(400,"Cuisine with id "+voteRequest.getCuisineChoiceId()+" is not available for choice");
        }
        //verify if this voter can vote for this event
        for(User user:event.getParticipants()){
            if(user.getId().equals(voteRequest.getUserId())){
                //check if the voter has already voted
                for(CuisinePoll cuisinePoll: event.getCuisinePolls()){
                    if(cuisinePoll.getVoter().getId().equals(voteRequest.getUserId())){
                        cuisinePoll.setCuisine(cuisineChoice);
                        cuisinePoll.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        cuisinePollDAO.createOrUpdate(cuisinePoll);
                        return;
                    }
                }
                CuisinePoll cuisinePoll = new CuisinePoll();
                cuisinePoll.setCuisine(cuisineChoice);
                cuisinePoll.setEvent(event);
                cuisinePoll.setVoter(user);
                cuisinePoll.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                cuisinePoll.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                cuisinePollDAO.createOrUpdate(cuisinePoll);
                return;
            }
        }
        throw new LOException(400, "Given user cannot vote for this event");
    }

    @Override
    public void voteForRestaurant(VoteRequest voteRequest) throws LOException{
        Event event = eventDAO.findEventById(voteRequest.getEventId());
        if(event==null){
            throw new LOException(400,"Event with id "+voteRequest.getEventId()+" does not exist");
        }
        //Find the restaurant choice being voted
        Restaurant restaurantChoice = null;
        for(Restaurant restaurant: event.getRestaurantChoices()){
            if(restaurant.getId().equals(voteRequest.getRestaurantChoiceId())){
                restaurantChoice = restaurant;
            }
        }
        if(restaurantChoice == null){
            throw new LOException(400,"Restaurant with id "+voteRequest.getRestaurantChoiceId()+" is not available for choice");
        }
        //verify if this voter can vote for this event
        for(User user: event.getParticipants()){
            if(user.getId().equals(voteRequest.getUserId())){
                //check if the voter has already voted
                for(RestaurantPoll restaurantPoll: event.getRestaurantPolls()){
                    if(restaurantPoll.getVoter().getId().equals(voteRequest.getUserId())){
                        restaurantPoll.setRestaurant(restaurantChoice);
                        restaurantPoll.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        restaurantPollDAO.createOrUpdate(restaurantPoll);
                        return;
                    }
                }
                RestaurantPoll restaurantPoll = new RestaurantPoll();
                restaurantPoll.setRestaurant(restaurantChoice);
                restaurantPoll.setEvent(event);
                restaurantPoll.setVoter(user);
                restaurantPoll.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                restaurantPoll.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                restaurantPollDAO.createOrUpdate(restaurantPoll);
                return;
            }
        }
        throw new LOException(400, "Given user cannot vote for this event");
    }

    @Override
    public void voteForTime(VoteRequest voteRequest) throws LOException{
        Event event = eventDAO.findEventById(voteRequest.getEventId());
        if(event==null){
            throw new LOException(400,"Event with id "+voteRequest.getEventId()+" does not exist");
        }
        //Find the time choice being voted
        Time timeChoice = null;
        for(Time time: event.getTimeChoices()){
            if(time.getId().equals(voteRequest.getTimeChoiceId())){
                timeChoice = time;
            }
        }
        if(timeChoice == null){
            throw new LOException(400,"Time with id "+voteRequest.getTimeChoiceId()+" is not available for choice");
        }
        //verify if this voter can vote for this event
        for(User user: event.getParticipants()){
            if(user.getId().equals(voteRequest.getUserId())){
                //check if the voter has already voted
                for(TimePoll timePoll: event.getTimePolls()){
                    if(timePoll.getVoter().getId().equals(voteRequest.getUserId())){
                        timePoll.setTime(timeChoice);
                        timePoll.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                        timePollDAO.createOrUpdate(timePoll);
                        return;
                    }
                }
                TimePoll timePoll = new TimePoll();
                timePoll.setTime(timeChoice);
                timePoll.setEvent(event);
                timePoll.setVoter(user);
                timePoll.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                timePoll.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                timePollDAO.createOrUpdate(timePoll);
                return;
            }
        }
        throw new LOException(400, "Given user cannot vote for this event");
    }

}
