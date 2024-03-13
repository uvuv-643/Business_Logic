package ru.uvuv643.business_logic.models.general;

import jakarta.persistence.Transient;
import ru.uvuv643.business_logic.enums.ModerationStatusEnum;

import java.util.List;
import java.util.Map;

public abstract class AbstractSeeder {

    public List<AbstractSeeder> getDefaultObjects() {
        return List.of();
    }

    public Long getId() {
        return -1L;
    }

}
