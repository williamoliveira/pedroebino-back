package com.pin2.pedrobino.entities.agenda;

import com.pin2.pedrobino.entities.request.Request;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "agenda")
public class AgendaEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Column(name = "starts_at")
    private Date startsAt;

    @NotEmpty
    @Column(name = "ends_at")
    private Date endsAt;

    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    @JoinColumn(name="request_id", nullable=false)
    private Request request;

    public AgendaEntry() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(Date startsAt) {
        this.startsAt = startsAt;
    }

    public Date getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(Date endsAt) {
        this.endsAt = endsAt;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
