package com.pin2.pedrobino.entities.request;

import com.pin2.pedrobino.entities.city.City;
import com.pin2.pedrobino.entities.client.Client;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name = "can_share")
    private boolean canShare;

    @NotNull
    @Column(name = "preferred_date")
    private LocalDateTime preferredDate;

    // In seconds
    @Column(name = "estimated_travel_duration")
    private long estimatedTravelDuration;

    // In meters
    private long distance;

    // In Liters
    @Min(0)
    private int volume;

    @NotEmpty
    private String status;

    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(name="from_city_id")
    private City from;

    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(name="to_city_id")
    private City to;

    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @Size(min=3, max=3)
    @OneToMany(mappedBy="request", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    private List<Proposal> proposals;

    @ManyToOne(optional=true, fetch=FetchType.LAZY)
    @JoinColumn(name="chosen_proposal_id")
    private Proposal chosenProposal;

    public Request() {
    }

    public Request(boolean canShare,
                   LocalDateTime preferredDate,
                   long estimatedTravelDuration,
                   long distance,
                   int volume,
                   String status,
                   City from,
                   City to,
                   Client client,
                   List<Proposal> proposals,
                   Proposal chosenProposal) {
        this.canShare = canShare;
        this.preferredDate = preferredDate;
        this.estimatedTravelDuration = estimatedTravelDuration;
        this.distance = distance;
        this.volume = volume;
        this.status = status;
        this.from = from;
        this.to = to;
        this.client = client;
        this.proposals = proposals;
        this.chosenProposal = chosenProposal;
    }

    public Request(boolean canShare,
                   LocalDateTime preferredDate,
                   long estimatedTravelDuration,
                   long distance,
                   int volume,
                   String status,
                   City from,
                   City to,
                   Client client) {
        this.canShare = canShare;
        this.preferredDate = preferredDate;
        this.estimatedTravelDuration = estimatedTravelDuration;
        this.distance = distance;
        this.volume = volume;
        this.status = status;
        this.from = from;
        this.to = to;
        this.client = client;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCanShare() {
        return canShare;
    }

    public void setCanShare(boolean canShare) {
        this.canShare = canShare;
    }

    public LocalDateTime getPreferredDate() {
        return preferredDate;
    }

    public void setPreferredDate(LocalDateTime preferredDate) {
        this.preferredDate = preferredDate;
    }

    public long getEstimatedTravelDuration() {
        return estimatedTravelDuration;
    }

    public void setEstimatedTravelDuration(long estimatedTravelDuration) {
        this.estimatedTravelDuration = estimatedTravelDuration;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public City getFrom() {
        return from;
    }

    public void setFrom(City from) {
        this.from = from;
    }

    public City getTo() {
        return to;
    }

    public void setTo(City to) {
        this.to = to;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Proposal> getProposals() {
        return proposals;
    }

    public void setProposals(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public Proposal getChosenProposal() {
        return chosenProposal;
    }

    public void setChosenProposal(Proposal chosenProposal) {
        this.chosenProposal = chosenProposal;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public float getEstimatedTravelDurationInHours(){
        return estimatedTravelDuration / 3600;
    }
}
