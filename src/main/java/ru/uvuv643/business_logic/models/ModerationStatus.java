package ru.uvuv643.business_logic.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import ru.uvuv643.business_logic.annotations.MustBeSeeded;
import ru.uvuv643.business_logic.enums.ModerationStatusEnum;
import ru.uvuv643.business_logic.models.general.AbstractSeeder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.entry;

@Entity
@Table(name = "moderation_statuses")
@MustBeSeeded
public class ModerationStatus extends AbstractSeeder {

    public final static Map<ModerationStatusEnum, Long> statuses = Map.ofEntries(
            entry(ModerationStatusEnum.ACCEPTED, 1L),
            entry(ModerationStatusEnum.ACTIVE, 2L),
            entry(ModerationStatusEnum.REJECTED, 3L)
    );

    @Override
    @Transient
    @JsonIgnore
    public List<AbstractSeeder> getDefaultObjects() {
        return statuses.keySet().stream().map((key) -> new ModerationStatus(statuses.get(key), key.getCode())).collect(Collectors.toList());
    }

    public ModerationStatus() { }

    public ModerationStatus(Long id, String code) {
        this.id = id;
        this.code = code;
    }

    @Id
    protected Long id;

    @Column
    protected String code;

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
