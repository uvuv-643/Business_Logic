package ru.uvuv643.business_logic.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import ru.uvuv643.business_logic.annotations.MustBeSeeded;
import ru.uvuv643.business_logic.enums.MembershipEnum;
import ru.uvuv643.business_logic.models.general.AbstractSeeder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.entry;

@Entity
@Table(name = "memberships")
@MustBeSeeded
public class Membership extends AbstractSeeder {

    public final static Map<MembershipEnum, Long> memberships = Map.ofEntries(
            entry(MembershipEnum.BASIC, 1L),
            entry(MembershipEnum.PRO, 2L),
            entry(MembershipEnum.VIP, 3L)
    );

    @Override
    @Transient
    @JsonIgnore
    public List<AbstractSeeder> getDefaultObjects() {
        return memberships.keySet().stream().map((key) -> new Membership(memberships.get(key), key.getCode(), key.getPriceInUsd(), key.getCoins())).collect(Collectors.toList());
    }

    public Membership(Long id, String title, double priceInUsd, double coins) {
        this.id = id;
        this.title = title;
        this.priceInUsd = priceInUsd;
        this.coins = coins;
    }

    public Membership() {
    }

    @Id
    private Long id;

    @Column
    private String title;

    @Column
    private double priceInUsd;

    @Column
    private double coins;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
