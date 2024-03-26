package ru.uvuv643.business_logic.components;

import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import org.reflections.Reflections;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.uvuv643.business_logic.annotations.MustBeSeeded;
import ru.uvuv643.business_logic.models.general.AbstractSeeder;

import jakarta.persistence.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

@Component
public class Dataloader implements ApplicationRunner {

    private final EntityManagerFactory emf;

    public Dataloader(EntityManagerFactory factory) {
        this.emf = factory;
    }

    protected Set<Class<? extends AbstractSeeder>> getSeededClasses() {
        Reflections reflections = new Reflections("ru.uvuv643.business_logic.models");
        Set<Class<? extends AbstractSeeder>> subTypes = reflections.getSubTypesOf(AbstractSeeder.class);
        reflections.getSubTypesOf(AbstractSeeder.class).removeIf(clazz -> clazz.getAnnotation(MustBeSeeded.class) == null);
        return subTypes;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Set<Class<? extends AbstractSeeder>> targetClasses = this.getSeededClasses();
        for (Class<? extends AbstractSeeder> currentClass : targetClasses) {
            try {
                AbstractSeeder object = currentClass.getConstructor().newInstance();
                @SuppressWarnings("unchecked")
                List<AbstractSeeder> currentDefaultObjects = (List<AbstractSeeder>) currentClass.getMethod("getDefaultObjects").invoke(object);
                for (var x : currentDefaultObjects) {
                    if (em.find(currentClass, x.getId()) == null) {
                        em.merge(x);
                    }
                }
            } catch (NoSuchMethodException exception) {
                System.err.println("Cannot find method `getStatusEnums()` in " + currentClass.getName());
            } catch (IllegalAccessException | InvocationTargetException exception) {
                System.err.println("Cannot invoke method `getStatusEnums()` in " + currentClass.getName());
            } catch (InstantiationException exception) {
                System.err.println("Cannot create object in " + currentClass.getName());
            }
        }
        em.getTransaction().commit();
    }

}
