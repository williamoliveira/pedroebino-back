package com.pin2.pedrobino.entities.request;

import com.pin2.pedrobino.entities.city.City;
import com.pin2.pedrobino.entities.client.Client;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
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
    @Column(name = "prefered_date")
    private Date preferedDate;

    @Column(name = "estimated_travel_duration")
    private double estimatedTravelDuration;

    @Min(0)
    private double volume;

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

    @OneToMany(mappedBy="request", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    private List<Proposal> proposals;

    @ManyToOne(optional=true, fetch=FetchType.EAGER)
    @JoinColumn(name="choosen_proposal_id")
    private Proposal choosenProposal;

    public Request() {
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

    public Date getPreferedDate() {
        return preferedDate;
    }

    public void setPreferedDate(Date preferedDate) {
        this.preferedDate = preferedDate;
    }

    public double getEstimatedTravelDuration() {
        return estimatedTravelDuration;
    }

    public void setEstimatedTravelDuration(double estimatedTravelDuration) {
        this.estimatedTravelDuration = estimatedTravelDuration;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
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

    public Proposal getChoosenProposal() {
        return choosenProposal;
    }

    public void setChoosenProposal(Proposal choosenProposal) {
        this.choosenProposal = choosenProposal;
    }
}
